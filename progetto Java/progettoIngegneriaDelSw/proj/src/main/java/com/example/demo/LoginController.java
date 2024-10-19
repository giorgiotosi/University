package com.example.demo;

import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController extends Usr_Controller{

    @FXML
    private TextField cod_fisc_field;

    @FXML
    private TextField password_field;

    @FXML
    private Text error;

    @FXML
    private Button loginButton;

    @FXML
    private Button registrationButton;

    @FXML
    void login(MouseEvent event) throws IOException, SQLException {
        try{
            //connessione al db
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/questura", "root", "");
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT cod_fiscale, password FROM login");
            boolean found = false;

            //prelevo codice fiscale e password dal db
            while(res.next()){
                //se coincidono entrambi i campi, allora consento l'accesso alla piattaforma
                if (res.getString("cod_fiscale").equals(cod_fisc_field.getText()) && res.getString("password").equals(password_field.getText()))
                    found = true;
            }
            //se non ho trovato niente allora l'utente non esiste in anagrafica
            if(!found) {
                error.setText("Dati non validi");
            }
            else {
                setCod_fiscale(cod_fisc_field.getText());
                Stage stage = (Stage) loginButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Menu");
                stage.setScene(scene);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void registration(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registrati");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void goBack(MouseEvent event) throws IOException {
        Stage stage = (Stage) registrationButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("starting-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome");
        stage.setScene(scene);
    }


    @Override
    public void setCod_fiscale(String cod_fiscale) {
        Usr_Controller.cod_fiscale = cod_fiscale;
    }

    @Override
    public String getCod_fiscale() {
        return null;
    }

    @Override
    public void setSede(String sede) {
    }

    @Override
    public String getSede() {
        return null;
    }

    @Override
    public void setServizio(String servizio) {
    }

    @Override
    public String getServizio() {
        return null;
    }
}



