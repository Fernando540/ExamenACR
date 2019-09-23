package buscaminas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
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
        while(cl==null){
            try {
                conectar();
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void conectar() throws InterruptedException{
        int pto = 1234;
        String host = "127.0.0.1";
        try {
            cl = new Socket(host, pto);
            br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            writer = new PrintWriter(cl.getOutputStream(), true);   
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("servidor no encontrado... reintentando en 3 segundos...");
            Thread.sleep(3000);
        }
    }
    @FXML
    public void jugar(ActionEvent e) throws IOException{
        if((!textFieldNombreUsuario.getText().trim().isEmpty())&&(comboBoxNivel.getValue()!=null)){
            System.out.println("Accedio");
            Usuario usuario = new Usuario(textFieldNombreUsuario.getText(),(String)comboBoxNivel.getValue(),new Date(),cl,br,writer);
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
            ((Stage)(anchorPanePrincipal.getScene().getWindow())).close();
            //writer.write("0");
            //writer.flush();
        }else{
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
                Thread.sleep(1*1000);
            } catch (InterruptedException ex) {
                stageAlerta.close();
            }
            stageAlerta.close();
        }
    }
    
    @FXML
    public void verMarcadores(ActionEvent e) throws IOException{
        
    }
    
}
