package buscaminas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ferna
 */
public class RecordsController{
    
    @FXML
    private TableView<Usuario> tablaScores;
    
    @FXML
    private Label lblPrin;
    
    @FXML
    private Label lblInt;
    
    @FXML
    private Label lblExp;
    
    @FXML
    private TableColumn colTime;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colDate;
    
    ObservableList<Usuario> winners;
    
    public void iniciaTabla() {
        colTime.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        winners = FXCollections.observableArrayList();
        tablaScores.setItems(winners);
    }
    
    public void setDifi(int diff){
        switch(diff){
            case 1:
                lblPrin.setDisable(false);
                break;
            case 2:
                lblInt.setDisable(false);
                break;
            case 3:
                lblExp.setDisable(false);
                break;
        }
    }
    
    public void addWinner(Usuario usr){
        winners.add(usr);
    }
}
