/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.IOException;
import java.net.URL;
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
 * @author nahomi
 */
public class FXMLJuegoBuscaminasController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTablero;
    @FXML
    private Button buttonEmpezar;

    private Label timeLabel;
    private GridPane gridPaneTablero;
    private HBox hBox;
    private Reloj timer;

    private Stage stageTablero;
    private int[][] tablero;
    private ArrayList<Button> celdas = new ArrayList<>();
    private ArrayList<Button> celdasPresionadas = new ArrayList<>();
    private ArrayList<Button> banderasColocadas = new ArrayList<>();
    private int totalCeldas;
    private int totalMinas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void empezar(ActionEvent e) {
        gridPaneTablero = new GridPane();
        gridPaneTablero.setVgap(1);
        gridPaneTablero.setHgap(1);
        gridPaneTablero.setGridLinesVisible(true);
        buttonEmpezar.setVisible(false);
        buttonEmpezar.setDisable(true);
        stageTablero = ((Stage) anchorPaneTablero.getScene().getWindow());
        stageTablero.centerOnScreen();
        Usuario jugador = (Usuario) (stageTablero.getUserData());
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
        if (jugador.getDificultad().equals("Principiante")) {
            System.out.println("Se ha iniciado el modo principiante");
            totalCeldas = 71;
            totalMinas = 10;
            tablero = crearTablero(9, 9);
            stageTablero.setWidth((31 * 9) + 25);
            stageTablero.setHeight((31 * 9) + 70);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Button boton = new Button();
                    boton.setAlignment(Pos.CENTER);
                    boton.setPrefSize(30, 30);
                    boton.setPadding(new Insets(0));
                    GridPane.setHalignment(boton, HPos.CENTER);
                    GridPane.setMargin(boton, new Insets(0));
                    Image icono = new Image("images/celda.png");
                    if (tablero[i][j] == -1) {
                        //Image icono = new Image("images/mina.jpg");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    String time = timer.getTime();
                                    timer.stopTimer();
                                    Image icono2 = new Image("images/minaExplota.jpg");
                                    boton.setBackground(new Background(new BackgroundImage(icono2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    for (Button buton : celdas) {
                                        if (!boton.equals(buton)) {
                                            if (((String) buton.getUserData()).equals("-1")) {
                                                Image icono = new Image("images/mina.jpg");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("0")) {
                                                Image icono = new Image("images/vacio.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("1")) {
                                                Image icono = new Image("images/uno.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("2")) {
                                                Image icono = new Image("images/dos.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("3")) {
                                                Image icono = new Image("images/tres.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("4")) {
                                                Image icono = new Image("images/cuatro.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("5")) {
                                                Image icono = new Image("images/cinco.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("6")) {
                                                Image icono = new Image("images/seis.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("7")) {
                                                Image icono = new Image("images/siete.png");
                                                buton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                            } else if (((String) buton.getUserData()).equals("8")) {
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
                                    alert.setContentText("Eres una basura :v\nTu tiempo fue de: "+time);
                                    alert.showAndWait();
                                    finalizarJuego();
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if (!banderasColocadas.contains(boton)) {
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        banderasColocadas.add(boton);
                                        if (banderasColocadas.size() == totalMinas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                }
                            }
                        });
                        Mina mina = new Mina(i,j,"-1");
                        boton.setUserData(mina);
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 0) {
                        //Image icono = new Image("images/vacio.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"0");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/vacio.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 1) {
                        //Image icono = new Image("images/uno.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"1");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/uno.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 2) {
                        //Image icono = new Image("images/dos.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"2");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/dos.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 3) {
                        //Image icono = new Image("images/tres.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"3");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/tres.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 4) {
                        //Image icono = new Image("images/cuatro.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"4");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/cuatro.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 5) {
                        //Image icono = new Image("images/cinco.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"5");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/cinco.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 6) {
                        //Image icono = new Image("images/seis.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"6");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/seis.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 7) {
                        //Image icono = new Image("images/siete.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"7");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/siete.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 8) {
                        //Image icono = new Image("images/ocho.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        Mina mina = new Mina(i,j,"8");
                        boton.setUserData(mina);
                        boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    if (!celdasPresionadas.contains(boton)) {
                                        Image icono = new Image("images/ocho.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                        celdasPresionadas.add(boton);
                                        if (celdasPresionadas.size() == totalCeldas) {
                                            System.out.println("GANASTEEEE");
                                            stageTablero.close();
                                        }
                                    }
                                } else if (event.getButton() == MouseButton.SECONDARY) {
                                    if(!celdasPresionadas.contains(boton)){
                                        Image icono = new Image("images/bandera.png");
                                        boton.setBackground(new Background(new BackgroundImage(icono,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                                    }
                                }
                            }
                        });
                        celdas.add(boton);
                        gridPaneTablero.add(boton, i, j);
                    } else {
                        System.err.println("Error en el tablero");
                    }
                }
            }
        } else if (jugador.getDificultad().equals("Intermedio")) {
            System.out.println("Se ha iniciado el modo intermedio");
            tablero = crearTablero(16, 16);
            totalCeldas = 216;
            stageTablero.setWidth((31 * 16) + 25);
            stageTablero.setHeight((31 * 16) + 70);
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    Button boton = new Button();
                    boton.setAlignment(Pos.CENTER);
                    boton.setPrefSize(30, 30);
                    boton.setPadding(new Insets(0));
                    GridPane.setHalignment(boton, HPos.CENTER);
                    GridPane.setMargin(boton, new Insets(0));
                    //Image icono = new Image("images/celda.png");
                    if (tablero[i][j] == -1) {
                        Image icono = new Image("images/mina.jpg");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 0) {
                        Image icono = new Image("images/vacio.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 1) {
                        Image icono = new Image("images/uno.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 2) {
                        Image icono = new Image("images/dos.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 3) {
                        Image icono = new Image("images/tres.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 4) {
                        Image icono = new Image("images/cuatro.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 5) {
                        Image icono = new Image("images/cinco.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 6) {
                        Image icono = new Image("images/seis.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 7) {
                        Image icono = new Image("images/siete.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 8) {
                        Image icono = new Image("images/ocho.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else {
                        System.err.println("Error en el tablero");
                    }
                }
            }
        } else if (jugador.getDificultad().equals("Experto")) {
            System.out.println("Se ha iniciado el modo experto");
            tablero = crearTablero(30, 16);
            totalCeldas = 381;
            stageTablero.setWidth((31 * 30) + 25);
            stageTablero.setHeight((31 * 16) + 70);
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 16; j++) {
                    Button boton = new Button();
                    boton.setAlignment(Pos.CENTER);
                    boton.setPrefSize(30, 30);
                    boton.setPadding(new Insets(0));
                    GridPane.setHalignment(boton, HPos.CENTER);
                    GridPane.setMargin(boton, new Insets(0));
                    //Image icono = new Image("images/celda.png");
                    if (tablero[i][j] == -1) {
                        Image icono = new Image("images/mina.jpg");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 0) {
                        Image icono = new Image("images/vacio.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 1) {
                        Image icono = new Image("images/uno.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 2) {
                        Image icono = new Image("images/dos.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 3) {
                        Image icono = new Image("images/tres.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 4) {
                        Image icono = new Image("images/cuatro.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 5) {
                        Image icono = new Image("images/cinco.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 6) {
                        Image icono = new Image("images/seis.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 7) {
                        Image icono = new Image("images/siete.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else if (tablero[i][j] == 8) {
                        Image icono = new Image("images/ocho.png");
                        boton.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        gridPaneTablero.add(boton, i, j);
                    } else {
                        System.err.println("Error en el tablero");
                    }
                }
            }

        }
        //gridPaneTablero.setAlignment(Pos.CENTER);
        gridPaneTablero.setLayoutX(10);
        gridPaneTablero.setLayoutY(10);
        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        Label tiempo = new Label("Tiempo:   ");
        timeLabel = new Label("00:00:00");
        tiempo.setTextFill(Color.WHITE);
        timeLabel.setTextFill(Color.WHITE);
        hBox.getChildren().addAll(tiempo, timeLabel);

        anchorPaneTablero.getChildren().addAll(gridPaneTablero, hBox);
        AnchorPane.setBottomAnchor(hBox, 8.0);
        AnchorPane.setRightAnchor(hBox, 20.0);

        Date inicioJuego = new Date();
        System.out.println("Empezamos : " + inicioJuego.toString());
        timer = new Reloj(timeLabel);
        timer.startTimer();
    }
    
    public void vacioRecursivo(int n, int m,int totalX, int totalY){
        int i=n,j=m;
        if (tablero[i][j] != -1) {
            if ((i > 0) && (j > 0)) {
                if (tablero[i - 1][j - 1] == 0) {
                    Button btn = celdas.get((i)*totalY+(j+1));
                    if(!celdasPresionadas.contains(btn)){
                        Image icono = new Image("images/vacio.jpg");
                        btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(celdas.get((i)*totalY+(j+1)));
                        vacioRecursivo(i-1,j-1,totalX,totalY);
                    }
                }else if((tablero[i - 1][j - 1] != 0)&&(tablero[i - 1][j - 1] != -1)){
                    Button btn = celdas.get((i)*totalY+(j+1));
                    if(!celdasPresionadas.contains(btn)){
                        //Image icono = new Image("images/"+((btn.))+".jpg");
                        //btn.setBackground(new Background(new BackgroundImage(icono, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                        celdasPresionadas.add(celdas.get((i)*totalY+(j+1)));
                        vacioRecursivo(i-1,j-1,totalX,totalY);
                    }
                }
            }
            if (i > 0) {
                if (tablero[i - 1][j] == -1) {
                }
            }

            if ((i > 0) && (j < (totalY - 1))) {
                if (tablero[i - 1][j + 1] == -1) {
                }
            }

            if ((j < (totalY - 1))) {
                if (tablero[i][j + 1] == -1) {
                }
            }
            if ((j < (totalY - 1)) && (i < (totalX - 1))) {
                if (tablero[i + 1][j + 1] == -1) {
                }
            }

            if ((i < (totalX - 1))) {
                if (tablero[i + 1][j] == -1) {
                }
            }

            if ((j > 0) && (i < (totalX - 1))) {
                if (tablero[i + 1][j - 1] == -1) {
                }
            }

            if ((j > 0)) {
                if (tablero[i][j - 1] == -1) {
                }
            }
        }
        return;
    }
    
    public int[][] crearTablero(int i, int j) {
        int[][] tablero = new int[i][j];
        int contadorMina = 0;
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                tablero[k][l] = -5;
            }
        }
        for (int k = 0; k < ((j == 9) ? 10 : ((j == 16) ? 40 : ((j == 30)) ? 99 : 10)); k++) {
            int randomX = (int) (Math.random() * i);
            int randomY = (int) (Math.random() * j);
            if (tablero[randomX][randomY] == -5) {
                System.out.println("Mina en la posicion,[" + randomX + "][" + randomY + "]");
                tablero[randomX][randomY] = -1;
            } else {
                k--;
            }
        }

        for (int k = 0; k < i; k++) {//fila
            for (int l = 0; l < j; l++) {//col
                if (tablero[k][l] != -1) {
                    if ((k > 0) && (l > 0)) {
                        if (tablero[k - 1][l - 1] == -1) {
                            contadorMina++;
                        }
                    }
                    if (k > 0) {
                        if (tablero[k - 1][l] == -1) {
                            contadorMina++;
                        }
                    }

                    if ((k > 0) && (l < (j - 1))) {
                        if (tablero[k - 1][l + 1] == -1) {
                            contadorMina++;
                        }
                    }

                    if ((l < (j - 1))) {
                        if (tablero[k][l + 1] == -1) {
                            contadorMina++;
                        }
                    }
                    if ((l < (j - 1)) && (k < (i - 1))) {
                        if (tablero[k + 1][l + 1] == -1) {
                            contadorMina++;
                        }
                    }

                    if ((k < (i - 1))) {
                        if (tablero[k + 1][l] == -1) {
                            contadorMina++;
                        }
                    }

                    if ((l > 0) && (k < (i - 1))) {
                        if (tablero[k + 1][l - 1] == -1) {
                            contadorMina++;
                        }
                    }

                    if ((l > 0)) {
                        if (tablero[k][l - 1] == -1) {
                            contadorMina++;
                        }
                    }

                    tablero[k][l] = contadorMina;
                    contadorMina = 0;
                }
            }
        }
        return tablero;
    }

    public void finalizarJuego() {
        Label labelGameOver = new Label("Game Over");
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
        ((Stage) anchorPaneTablero.getScene().getWindow()).close();
    }
}
