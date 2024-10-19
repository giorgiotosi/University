package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Usr_MainController extends Usr_Controller implements Initializable {

    @FXML
    private Text error;

    @FXML
    private ChoiceBox<String> servizio_usr;

    @FXML
    private ChoiceBox<String> sede_usr;

    @FXML
    private Button prenotazioneButton;

    @FXML
    private Button goHomeButton;

    @FXML
    private Button goBackButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servizio_usr.getItems().addAll("scadenza", "primo rilascio", "furto/smarrimento", "deterioramento");
        sede_usr.getItems().addAll("Verona", "Padova", "Vicenza", "Venezia");

    }


    @FXML
    void prenota(MouseEvent event) throws IOException {


        if(servizio_usr.getValue() == null || sede_usr.getValue() == null){
            error.setText("Inserisci tutti i campi.");
        }

        else {

            /*Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Prenotazione");
            alert.setHeaderText("Documenti necessari per il ritiro:\n");
            alert.setContentText(
                    "-Modulo di richiesta compilato\n" +
                            "-Marca da bollo\n" +
                            "-Ricevuta del versamento\n" +
                            "-Due fototessere su sfondo bianco\n" +
                            "-Passaporto precedente (se in possesso)");

            Optional<ButtonType> res = alert.showAndWait();*/

            //salvo nella classe padre i valori di sede e servizio per
            //visualizzare il corretto calendario
            setSede(sede_usr.getValue());
            setServizio(servizio_usr.getValue());



            //mostra la pagina con il calendario

            Stage stage = (Stage) prenotazioneButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("calendar-user-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Prenota");
            stage.setScene(scene);
        }
    }

    //ritorno al menu di login se clicco il tasto home
    @FXML
    void goHome(MouseEvent event) throws IOException {
        Stage stage = (Stage) goHomeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("starting-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    //ritorno al menu di login se clicco il tasto back
    @FXML
    void goBack(MouseEvent event) throws IOException {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-register-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
    }

    @Override
    public void setCod_fiscale(String cod_fiscale) {

    }

    @Override
    public String getCod_fiscale() {
        return null;
    }

    @Override
    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public String getSede() {
        return sede;
    }

    @Override
    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    @Override
    public String getServizio() {
        return servizio;
    }


}
