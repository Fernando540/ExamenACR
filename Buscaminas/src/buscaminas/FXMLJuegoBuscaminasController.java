package buscaminas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author nahomi, ferna
 */
public class FXMLJuegoBuscaminasController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTablero;
    @FXML
    private Button buttonEmpezar;

    private Label timeLabel;
    private GridPane gridPaneTablero;
    private Label noBanderas;
    private HBox hBox;
    private Reloj timer;

    private Stage stageTablero;
    private int[][] tablero;
    private ArrayList<Button> celdas = new ArrayList<>();
    private ArrayList<Button> celdasPresionadas = new ArrayList<>();
    private ArrayList<Button> banderasColocadas = new ArrayList<>();
    private ArrayList<Button> mbanderasColocadas = new ArrayList<>();
    private int totalCeldas;
    private int totalMinas;
    private int contarBanderas = 0;
    private int filas = 0, columnas = 0;

    Usuario jugador;
    private Socket cl;
    private BufferedReader br;
    private PrintWriter writer;
    private String dif;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void empezar(ActionEvent e) throws IOException {
        gridPaneTablero = new GridPane();
        gridPaneTablero.setVgap(1);
        gridPaneTablero.setHgap(1);
        gridPaneTablero.setGridLinesVisible(true);
        buttonEmpezar.setVisible(false);
        buttonEmpezar.setDisable(true);
        stageTablero = ((Stage) anchorPaneTablero.getScene().getWindow());
        stageTablero.centerOnScreen();
        jugador = (Usuario) (stageTablero.getUserData());

        cl = jugador.getSocket();
        br = jugador.getBr();
        writer = jugador.getWriter();
        dif = jugador.getDificultad();

        stageTablero.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.stopTimer();
                Platform.exit();
                System.exit(0);
            }
        });

        System.out.println("Ha ingresado el jugador");
        System.out.println(jugador.getNombre());
        System.out.println(jugador.getDificultad());
        System.out.println(jugador.getInicioT().toString());

        switch (jugador.getDificultad()) {
            case "Principiante":
                System.out.println("Se ha iniciado el modo principiante");
                filas = 9;
                columnas = 9;
                totalMinas = 10;
                writer.write("1");
                break;
            case "Intermedio":
                System.out.println("Se ha iniciado el modo intermedio");
                filas = 16;
                columnas = 16;
                totalMinas = 40;
                writer.write("2");
                break;
            case "Experto":
                System.out.println("Se ha iniciado el modo experto");
                filas = 16;
                columnas = 30;
                totalMinas = 99;
                writer.write("3");
                break;
            default:
                break;
        }
        writer.flush();
        tablero = new int[filas][columnas];

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                int a = Integer.parseInt(br.readLine());//sc.readLine();
                tablero[i][j] = a;
                if (a != -1) {
                    System.out.print(a + " ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println("");
        }

        totalCeldas = (filas * columnas) - totalMinas;
        stageTablero.setWidth((31 * columnas) + 25);
        stageTablero.setHeight((31 * filas) + 70);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button boton = new Button();
                boton.setAlignment(Pos.CENTER);
                boton.setPrefSize(30, 30);
                boton.setPadding(new Insets(0));
                GridPane.setHalignment(boton, HPos.CENTER);
                GridPane.setMargin(boton, new Insets(0));
                Image icono = new Image("images/celda.png");

                switch (tablero[i][j]) {
                    case -1:
                        //Image icono = new Image("images/mina.jpg");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if(mbanderasColocadas.contains(boton)){
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                    }
                                    String time = timer.getTime();
                                    String tiempoParo = timer.stopTimer();
                                    Image icono2 = new Image("images/minaExplota.jpg");
                                    boton.setBackground(new Background(new BackgroundImage(icono2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    for (Button buton : celdas) {
                                        if (!boton.equals(buton)) {
                                            if (((Mina) buton.getUserData()).getTipo().equals("images/mina.png")) {
                                                Image icono = new Image("images/mina.jpg");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/vacio.png")) {
                                                Image icono = new Image("images/vacio.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/uno.png")) {
                                                Image icono = new Image("images/uno.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/dos.png")) {
                                                Image icono = new Image("images/dos.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/tres.png")) {
                                                Image icono = new Image("images/tres.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/cuatro.png")) {
                                                Image icono = new Image("images/cuatro.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/cinco.png")) {
                                                Image icono = new Image("images/cinco.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/seis.png")) {
                                                Image icono = new Image("images/seis.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/siete.png")) {
                                                Image icono = new Image("images/siete.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((Mina) buton.getUserData()).getTipo().equals("images/ocho.png")) {
                                                Image icono = new Image("images/ocho.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else {
                                                System.err.println("Error en el tablero");
                                            }
                                        }
                                    }
                                    System.out.println("El usuario " + jugador.getNombre() + " ha perdido... Reiniciando...");

                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Resultado");
                                    alert.setHeaderText("Suerte para la próxima. A continuación se muestra tu resultado");
                                    alert.setContentText("Eres una basura :v\nTu tiempo fue de: " + time);
                                    alert.showAndWait();
                                    finalizarJuego(false, tiempoParo);
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!banderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            banderasColocadas.add(boton);
                                            if ((banderasColocadas.size() == totalMinas) && mbanderasColocadas.isEmpty()) {
                                                System.out.println("GANASTEEEE");
                                                finalizarJuego(true, timer.stopTimer());
                                                stageTablero.close();
                                            }
                                            contarBanderas++;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (banderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        banderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        boton.setUserData(new Mina(i, j, "images/mina.png", -1));
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 0:
                        //Image icono = new Image("images/vacio.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/vacio.png", 0));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/vacio.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        } else {
                                            vacioRecursivo(((Mina) boton.getUserData()).getPosicionX(), ((Mina) boton.getUserData()).getPosicionY(), filas, columnas);
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 1:
                        //Image icono = new Image("images/uno.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/uno.png", 1));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/uno.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 2:
                        //Image icono = new Image("images/dos.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/dos.png", 2));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/dos.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 3:
                        //Image icono = new Image("images/tres.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/tres.png", 3));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/tres.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 4:
                        //Image icono = new Image("images/cuatro.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/cuatro.png", 4));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/cuatro.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 5:
                        //Image icono = new Image("images/cinco.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/cinco.png", 5));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/cinco.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 6:
                        //Image icono = new Image("images/seis.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/seis.png", 6));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/seis.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 7:
                        //Image icono = new Image("images/siete.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/siete.png", 7));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/siete.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!mbanderasColocadas.contains(boton)) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    case 8:
                        //Image icono = new Image("images/ocho.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setUserData(new Mina(i, j, "images/ocho.png", 8));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        if(mbanderasColocadas.contains(boton)){
                                            contarBanderas--;
                                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                        Image icono = new Image("images/ocho.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            finalizarJuego(true, timer.stopTimer());
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if ((!mbanderasColocadas.contains(boton))) {
                                        if (contarBanderas < totalMinas) {
                                            mbanderasColocadas.add(boton);
                                            Image icono = new Image("images/bandera.png");
                                            boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            contarBanderas++;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        }
                                    } else if (mbanderasColocadas.contains(boton) && (!celdasPresionadas.contains(boton))) {
                                        contarBanderas--;
                                        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                                        mbanderasColocadas.remove(boton);
                                        Image icono = new Image("images/celda.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, j, i);
                        break;
                    default:
                        System.err.println("Error en el tablero");
                        break;
                }
            }
        }

        gridPaneTablero.setLayoutX(10);
        gridPaneTablero.setLayoutY(10);
        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        Label tiempo = new Label("Tiempo:   ");
        timeLabel = new Label("00:00:00");
        tiempo.setTextFill(Color.WHITE);
        timeLabel.setTextFill(Color.WHITE);
        Label banderas = new Label("Tienes: ");
        banderas.setTextFill(Color.WHITE);
        Label banderasTotal = new Label(" de "+String.valueOf(totalMinas)+" banderas\t\t");
        banderasTotal.setTextFill(Color.WHITE);
        noBanderas = new Label();
        noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
        noBanderas.setTextFill(Color.RED);
        hBox.getChildren().addAll(banderas,noBanderas,banderasTotal,tiempo, timeLabel);

        anchorPaneTablero.getChildren().addAll(gridPaneTablero, hBox);
        AnchorPane.setBottomAnchor(hBox, 8.0);
        AnchorPane.setRightAnchor(hBox, 20.0);
 
        Date inicioJuego = new Date();
        System.out.println("Empezamos : " + inicioJuego.toString());
        timer = new Reloj(timeLabel);
        timer.startTimer();
    }

    public void vacioRecursivo(int n, int m, int totalX, int totalY) {
        int i = n, j = m;
        if (tablero[i][j] != -1) {
            try {
                if (tablero[i - 1][j - 1] == 0) {
                    Button btn = celdas.get((i - 1) * totalY + ((j - 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                        vacioRecursivo(i - 1, j - 1, totalX, totalY);
                    }
                } else if ((tablero[i - 1][j - 1] != 0) && (tablero[i - 1][j - 1] != -1)) {
                    Button btn = celdas.get((i - 1) * totalY + ((j - 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            try {
                if (tablero[i - 1][j] == 0) {
                    Button btn = celdas.get((i - 1) * totalY + ((j)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                        vacioRecursivo(i - 1, j, totalX, totalY);
                    }
                } else if ((tablero[i - 1][j] != 0) && (tablero[i - 1][j] != -1)) {
                    Button btn = celdas.get((i - 1) * totalY + ((j)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            try {
                if (tablero[i - 1][j + 1] == 0) {
                    Button btn = celdas.get((i - 1) * totalY + ((j + 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                        vacioRecursivo(i - 1, j + 1, totalX, totalY);
                    }
                } else if ((tablero[i - 1][j + 1] != 0) && (tablero[i - 1][j + 1] != -1)) {
                    Button btn = celdas.get(((i - 1) * totalY + ((j + 1))));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            try {
                if (tablero[i][j + 1] == 0) {
                    Button btn = celdas.get((i) * totalY + ((j + 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                        vacioRecursivo(i, j + 1, totalX, totalY);
                    }
                } else if ((tablero[i][j + 1] != 0) && (tablero[i][j + 1] != -1)) {
                    Button btn = celdas.get((i) * totalY + ((j + 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            try {
                if (tablero[i + 1][j + 1] == 0) {
                    Button btn = celdas.get((i + 1) * totalY + ((j + 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                        vacioRecursivo(i + 1, j + 1, totalX, totalY);
                    }
                } else if ((tablero[i + 1][j + 1] != 0) && (tablero[i + 1][j + 1] != -1)) {
                    Button btn = celdas.get((i + 1) * totalY + ((j + 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                            stageTablero.close();
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            try {
                if (tablero[i + 1][j] == 0) {
                    Button btn = celdas.get((i + 1) * totalY + ((j)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                        }
                        vacioRecursivo(i + 1, j, totalX, totalY);
                    }
                } else if ((tablero[i + 1][j] != 0) && (tablero[i + 1][j] != -1)) {
                    Button btn = celdas.get((i + 1) * totalY + ((j)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            try {
                if (tablero[i + 1][j - 1] == 0) {
                    Button btn = celdas.get((i + 1) * totalY + ((j - 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                        }
                        vacioRecursivo(i + 1, j - 1, totalX, totalY);
                    }
                } else if ((tablero[i + 1][j - 1] != 0) && (tablero[i + 1][j - 1] != -1)) {
                    Button btn = celdas.get((i + 1) * totalY + ((j - 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            try {
                if (tablero[i][j - 1] == 0) {
                    Button btn = celdas.get((i) * totalY + ((j - 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image("images/vacio.png");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                        }
                        vacioRecursivo(i, j - 1, totalX, totalY);
                    }
                } else if ((tablero[i][j - 1] != 0) && (tablero[i][j - 1] != -1)) {
                    Button btn = celdas.get((i) * totalY + ((j - 1)));
                    if (!celdasPresionadas.contains(btn)) {
                        if (mbanderasColocadas.contains(btn)) {
                            contarBanderas--;
                            noBanderas.setText(String.valueOf(totalMinas-contarBanderas));
                        }
                        Image icono = new Image(((Mina) btn.getUserData()).getTipo());
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(btn);
                        if (celdasPresionadas.size() == totalCeldas) {
                            finalizarJuego(true, timer.stopTimer());
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }

    private void writeWinner() {
        writer.write("2");
        writer.flush();
        String diff = "";
        switch (jugador.getDificultad()) {
            case "Principiante":
                diff = "1";
                break;
            case "Intermedio":
                diff = "2";
                break;
            case "Experto":
                diff = "3";
                break;
            default:
                break;
        }
        writer.write(diff + jugador.getTiempo() + "/" + jugador.getNombre() + "/" + jugador.getFinT().toString());
        writer.flush();
    }

    public void finalizarJuego(boolean isWinner, String tiempo) {
        jugador.setFinT(LocalDate.now());
        jugador.setTiempo(tiempo);
        if (isWinner) {
            Label labelGameOver = new Label("¡Winner! en " + tiempo);
            writeWinner();
            labelGameOver.setFont(new Font("Bauhaus 93", 32));
            labelGameOver.setTextFill(Paint.valueOf("BLACK"));
            labelGameOver.setBackground(new Background(new BackgroundImage(new Image("images/gamOver.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
            Scene sceneGameOver = new Scene(labelGameOver);
            Stage stageGameOver = new Stage();
            stageGameOver.setScene(sceneGameOver);
            stageGameOver.setTitle("Buscaminas");
            stageGameOver.centerOnScreen();
            Image icono3 = new Image("images/titleBuscIcon.png");
            stageGameOver.getIcons().add(icono3);
            stageGameOver.initStyle(StageStyle.UNIFIED);
            stageGameOver.setResizable(false);
            stageGameOver.show();
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException ex) {
            }
            stageGameOver.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Buscaminas");
                stage.centerOnScreen();
                stage.getIcons().add(icono3);
                stage.initStyle(StageStyle.UNIFIED);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLJuegoBuscaminasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            writeWinner();
            Label labelGameOver = new Label("Game Over en " + tiempo);
            labelGameOver.setFont(new Font("Bauhaus 93", 32));
            labelGameOver.setTextFill(Paint.valueOf("BLACK"));
            labelGameOver.setBackground(new Background(new BackgroundImage(new Image("images/gamOver.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
            Scene sceneGameOver = new Scene(labelGameOver);
            Stage stageGameOver = new Stage();
            stageGameOver.setScene(sceneGameOver);
            stageGameOver.setTitle("Buscaminas");
            stageGameOver.centerOnScreen();
            Image icono3 = new Image("images/titleBuscIcon.png");
            stageGameOver.getIcons().add(icono3);
            stageGameOver.initStyle(StageStyle.UNIFIED);
            stageGameOver.setResizable(false);
            stageGameOver.show();
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException ex) {
            }
            stageGameOver.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Buscaminas");
                stage.centerOnScreen();
                stage.getIcons().add(icono3);
                stage.initStyle(StageStyle.UNIFIED);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLJuegoBuscaminasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ((Stage) anchorPaneTablero.getScene().getWindow()).close();
        writer.write("-1");
        writer.flush();
        try {
            br.close();
            writer.close();
            cl.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLJuegoBuscaminasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
