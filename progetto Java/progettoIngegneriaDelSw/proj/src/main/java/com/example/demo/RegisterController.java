package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class RegisterController {


    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private DatePicker data;

    @FXML
    private TextField luogo;

    @FXML
    private TextField cod_fisc;

    @FXML
    private PasswordField password;

    @FXML
    private Text error;

    @FXML
    private Button registrationButton;

    @FXML
    private Text goBack;



    @FXML
    void registrati(MouseEvent event) throws IOException, SQLException {

        //controllo che l'utente che cerca di registrarsi non sia già registrato.
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/questura", "root", "");
        Statement statement = connection.createStatement();
        boolean found = false;
        boolean found_ = false;

        //controllo che i campi non siano vuoti
        if(nome.getText().isEmpty() || cognome.getText().isEmpty() || luogo.getText().isEmpty() ||
                cod_fisc.getText().isEmpty() || data.getValue() == null ||password.getText().isEmpty())
            error.setText("Inserisci tutti i campi.");

        else {

            ResultSet res = statement.executeQuery("SELECT cod_fiscale FROM login");

            while (res.next()) {
                if (res.getString("cod_fiscale").equals(cod_fisc.getText())) {
                    found = true;
                    error.setText("Codice fiscale già registrato.");
                    break;
                }
            }


            //controllo che l'utente appartenga all'anagrafe a disposizione
            if (!found) {

                res = statement.executeQuery("SELECT nome, cognome, luogo, data, cod_fiscale, tess_sanitaria FROM registrazione");


                while (res.next()) {
                    if (res.getString("nome").equals(nome.getText()) &&
                            res.getString("cognome").equals(cognome.getText()) &&
                            res.getString("luogo").equals(luogo.getText()) &&
                            res.getString("cod_fiscale").equals(cod_fisc.getText()) &&
                            res.getString("data").equals(data.getValue().toString())) {
                        found_ = true;
                        break;
                    }
                }


                if (!found_) {
                    System.out.println("Utente non registrato in anagrafe.");
                    error.setText("Dati non validi o non riconosciuti dal sistema.\nRicontrollare i dati inseriti.\n" +
                            "Se il problema persiste contattare serviziopassaporti@prefetturaveneto.it");
                }

                //controllo che la password abbia i requisiti
                else if(password.getText().length() < 6 || password.getText().length() > 14 || !password.getText().matches(".*[0-9].*"))
                    error.setText("La passowrd deve essere tra 6 e 14 caratteri e deve contenere almeno un numero.");

                //se è tutto corretto, salvo i dati della persona che può effettuare il login
                else {
                    goBack(event);
                    //salvo tutti i dati del DB in rispettive variabili
                    String nome_ = res.getString("nome");
                    String cognome_ = res.getString("cognome");
                    String password_ = password.getText();
                    String luogo_ = res.getString("luogo");
                    String data_ = res.getString("data");
                    String cod_fisc_ = res.getString("cod_fiscale");
                    String tess_sanitaria_ = res.getString("tess_sanitaria");

                    String upgrade = "INSERT INTO login (nome, cognome, password, luogo, data, cod_fiscale, tess_sanitaria) " +
                            "VALUES('"+nome_+"', '"+cognome_+"', '"+password_+"', '"+luogo_+"', '"+data_+"', '"+cod_fisc_+"', '"+tess_sanitaria_+"')";

                    statement.executeUpdate(upgrade);
                }

                System.out.println("Arrivo qui?");
            }
        }
    }


    @FXML
    void goBack(MouseEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}

