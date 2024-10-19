package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Op_LoginController extends Op_Controller implements Initializable {

    @FXML
    public TextField id_op;
    @FXML
    public PasswordField password_op;
    @FXML
    public Text error;
    @FXML
    public Button login_op;
    @FXML
    public ChoiceBox<String> sede_op;
    @FXML
    private Button goBackButton;
    @FXML
    boolean found = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sede_op.getItems().addAll("Verona", "Padova", "Vicenza", "Venezia");
    }

    @FXML
    void Login(MouseEvent event) throws IOException, SQLException {

        //controllo che l'utente che cerca di registrarsi non sia già registrato.
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/questura", "root", "");
        Statement statement = connection.createStatement();

        //controllo che i campi non siano vuoti
        if(id_op.getText().isEmpty() || password_op.getText().isEmpty() || sede_op.getValue() == null)
            error.setText("Inserisci tutti i campi.");

        else {
            ResultSet res = statement.executeQuery("SELECT n_tesserino, password, sede FROM operatori");

            while (res.next()) {
                //se ID e password dello stesso record esistono e corrispondono con quelli inseriti, allora accedo
                if (res.getString("n_tesserino").equals(id_op.getText())
                        && res.getString("password").equals(password_op.getText())
                        && res.getString("sede").equals(sede_op.getValue())) {
                    found = true;
                    break;
                }
            }

            setSede(sede_op.getValue());

            if(found){
                Stage stage = (Stage) login_op.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("operatore-main-page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Calendario");
                stage.setScene(scene);
            }
            else
                error.setText("L'operatore non esiste.");
        }
    }


    @FXML
    void goBack(MouseEvent event) throws IOException {

        Stage stage = (Stage) goBackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("starting-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    @Override
    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public String getSede() {
        return sede;
    }
}

