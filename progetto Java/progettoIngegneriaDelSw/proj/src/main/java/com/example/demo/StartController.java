package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StartController implements Initializable{


    @FXML
    private Button cittadinoLogin;

    @FXML
    private Button operatoreLogin;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            File file = new File("proj\\src\\cittadino.jpg");
            Image image = new Image(file.toURI().toString());
            image1.setImage(image);

            File file1 = new File("proj\\src\\questura.jpg");
            Image image1 = new Image(file1.toURI().toString());
            image2.setImage(image1);
    }

    @FXML
    void loginCittadino(MouseEvent event) throws IOException {
        Stage stage = (Stage) cittadinoLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-register-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    @FXML
    void loginOperatore(MouseEvent event) throws IOException {
        Stage stage = (Stage) operatoreLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("operatore-login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}




