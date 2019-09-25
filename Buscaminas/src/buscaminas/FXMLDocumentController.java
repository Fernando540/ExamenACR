package buscaminas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Brandon
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox comboBoxNivel;
    @FXML
    private TextField textFieldNombreUsuario;
    @FXML
    private AnchorPane anchorPanePrincipal;

    private Socket cl;
    private BufferedReader br;
    private PrintWriter writer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Principiante");
        list.add("Intermedio");
        list.add("Experto");
        comboBoxNivel.setItems(list);
        while (cl == null) {
            try {
                conectar();
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void jugar(ActionEvent e) throws IOException {
        if ((!textFieldNombreUsuario.getText().trim().isEmpty()) && (comboBoxNivel.getValue() != null) && !textFieldNombreUsuario.getText().contains("/")) {
            writer.write("0");
            writer.flush();
            System.out.println("Accedio");
            Usuario usuario = new Usuario(textFieldNombreUsuario.getText(), (String) comboBoxNivel.getValue(), LocalDate.now(), cl, br, writer);
            Parent root = FXMLLoader.load(getClass().getResource("FXMLJuegoBuscaminas.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setScene(scene);
            stage.setTitle("Buscaminas");
            stage.centerOnScreen();
            Image icono = new Image("images/titleBuscIcon.png");
            stage.getIcons().add(icono);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setResizable(false);
            stage.setUserData(usuario);
            stage.show();
            ((Stage) (anchorPanePrincipal.getScene().getWindow())).close();
        } else {
            Label labelAlerta = new Label("ERROR: Ingresa un nombre de usuario.");
            Scene esceneAlerta = new Scene(labelAlerta);
            Stage stageAlerta = new Stage();
            stageAlerta.setScene(esceneAlerta);
            stageAlerta.setTitle("ERROR");
            stageAlerta.setResizable(false);
            stageAlerta.setHeight(50);
            stageAlerta.setWidth(250);
            stageAlerta.show();
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException ex) {
                stageAlerta.close();
            }
            stageAlerta.close();
        }
    }

    @FXML
    public void showScores(ActionEvent e) throws IOException {
        if (comboBoxNivel.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atención");
            alert.setHeaderText("Selecciona una dificultad, por favor");
            alert.showAndWait();
        } else {
            /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tablero de Resultados");
            alert.setHeaderText("Modalidad: " + dif);
            alert.setContentText(content);
            alert.showAndWait();*/

            try {
                //Se carga la segunda escena
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Records.fxml"));
                Parent root = loader.load();

                //Creamos objeto Controller el cual usaremos para invocar los metdos necesarios y así pasar información
                RecordsController records = (RecordsController) loader.getController();
                records.iniciaTabla();
                
                System.out.print("Solicitando...");
                writer.write("1");
                writer.flush();
                String dif = comboBoxNivel.getValue().toString();
                int difi = 0;
                switch (dif) {
                    case "Principiante":
                        System.out.println("Scores principiante");
                        writer.write("1");
                        difi = 1;
                        break;
                    case "Intermedio":
                        System.out.println("Scores intermedio");
                        writer.write("2");
                        difi = 2;
                        break;
                    case "Experto":
                        System.out.println("Scores experto");
                        writer.write("3");
                        difi = 3;
                        break;
                    default:
                        break;
                }
                writer.flush();
                records.setDifi(difi);
                
                boolean seguir = true;
                String in;
                while (seguir) {
                    in = br.readLine();
                    if (in.equals("-1")) {
                        seguir = false;
                    } else {
                        String[] splitted = in.split("/");
                        System.out.println(splitted[0]);
                        System.out.println(splitted[1]);
                        System.out.println(splitted[2]);
                        records.addWinner(new Usuario(splitted[0],splitted[1],splitted[2]));
                        /*for (String celda : splitted) {
                        System.out.println(celda);
                    }*/
                    }
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Tabla de Resultados");
                Image icono = new Image("images/titleBuscIcon.png");
                stage.getIcons().add(icono);
                stage.show();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    public void conectar() throws InterruptedException {
        int pto = 1234;
        String host = "127.0.0.1";
        try {
            cl = new Socket(host, pto);
            br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            writer = new PrintWriter(cl.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Servidor no encontrado... Reintendando en 3 segundos");
            Thread.sleep(3000);
        }
    }

}
