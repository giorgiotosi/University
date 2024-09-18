package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;


public class Op_MainController extends Op_Controller implements Initializable{

    @FXML
    private ChoiceBox<String> servizio;
    @FXML
    private GridPane gridButtons;
    @FXML
    private Text error;
    @FXML
    private Label day1;
    @FXML
    private Label day2;
    @FXML
    private Label day3;
    @FXML
    private Label day4;
    @FXML
    private Label day5;
    @FXML
    private Label day6;
    @FXML
    private Label day7;
    @FXML
    private Button goHomeButton;
    @FXML
    private Button goBackButton;
    @FXML
    private Text month;
    @FXML
    private Text year;
    @FXML
    private Label setDay;
    @FXML
    private Label setHBegin;
    @FXML
    private Label setHEnd;

    Integer rowIndex = null;
    Integer colIndex = null;
    Node source = null;
    String sede_corrente = getSede();
    int settimana_corrente;
    Week week = Week.getInstance(true);
    int[] days= week.getWeek();
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/questura", "root", "");

    //creo una connessione al server
    public Op_MainController() throws SQLException {};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        servizio.getItems().addAll("scadenza", "primo rilascio", "furto/smarrimento", "deterioramento");
        listener();

        LocalDate date = LocalDate.of(week.year, week.month, week.day);
        settimana_corrente = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 53;

        try {
            colori_settimana();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        day1.setText(String.valueOf(days[0]));
        day2.setText(String.valueOf(days[1]));
        day3.setText(String.valueOf(days[2]));
        day4.setText(String.valueOf(days[3]));
        day5.setText(String.valueOf(days[4]));
        day6.setText(String.valueOf(days[5]));
        day7.setText(String.valueOf(days[6]));

        month.setText(Mesi.getByValore(week.month -1));
        year.setText(String.valueOf(week.year));
    }

    public void goNextWeek(MouseEvent mouseEvent) throws SQLException {

        settimana_corrente = (settimana_corrente + 1) % 53;
        colori_settimana();
        week.nextWeek();

        day1.setText(String.valueOf(days[0]));
        day2.setText(String.valueOf(days[1]));
        day3.setText(String.valueOf(days[2]));
        day4.setText(String.valueOf(days[3]));
        day5.setText(String.valueOf(days[4]));
        day6.setText(String.valueOf(days[5]));
        day7.setText(String.valueOf(days[6]));

        month.setText(Mesi.getByValore(week.month-1));
        year.setText(String.valueOf(week.year));

    }

    public void goBackWeek(MouseEvent mouseEvent) throws SQLException {

        if(settimana_corrente == 0)
            settimana_corrente = 53;

        settimana_corrente = (settimana_corrente - 1) % 53;
        colori_settimana();
        week.previousWeek();

        day1.setText(String.valueOf(days[0]));
        day2.setText(String.valueOf(days[1]));
        day3.setText(String.valueOf(days[2]));
        day4.setText(String.valueOf(days[3]));
        day5.setText(String.valueOf(days[4]));
        day6.setText(String.valueOf(days[5]));
        day7.setText(String.valueOf(days[6]));

        month.setText(Mesi.getByValore(week.month-1));
        year.setText(String.valueOf(week.year));

    }

    public void write(ActionEvent mouseEvent) {

        source = (Node) mouseEvent.getSource();
        rowIndex = GridPane.getRowIndex(source);
        if(rowIndex == null){
            rowIndex = 0;
        }
        colIndex = GridPane.getColumnIndex(source);
        if(colIndex == null){
            colIndex = 0;
        }

        // Ottieni la riga e la colonna del nodo corrente

        if (setDay != null) {
            boolean meseInPiu=false;
            int[] index = new int[7];
            for(int i = 0; i<6; i++){
                if(Integer.parseInt(day1.getText()) > Integer.parseInt(day2.getText())){
                    index[1]= 1;
                    index[2]= 1;
                    index[3]= 1;
                    index[4]= 1;
                    index[5]= 1;
                    index[6]= 1;
                    meseInPiu = true;
                    break;
                }
                if(Integer.parseInt(day2.getText()) > Integer.parseInt(day3.getText())){
                    index[2]= 1;
                    index[3]= 1;
                    index[4]= 1;
                    index[5]= 1;
                    index[6]= 1;
                    meseInPiu = true;
                    break;
                }
                if(Integer.parseInt(day3.getText()) > Integer.parseInt(day4.getText())){
                    index[3]= 1;
                    index[4]= 1;
                    index[5]= 1;
                    index[6]= 1;
                    meseInPiu = true;
                    break;
                }
                if(Integer.parseInt(day4.getText()) > Integer.parseInt(day5.getText())){
                    index[4]= 1;
                    index[5]= 1;
                    index[6]= 1;
                    meseInPiu = true;
                    break;
                }
                if(Integer.parseInt(day5.getText()) > Integer.parseInt(day6.getText())){
                    index[5]= 1;
                    index[6]= 1;
                    meseInPiu = true;
                    break;
                }
                if(Integer.parseInt(day6.getText()) > Integer.parseInt(day7.getText())){
                    index[6]= 1;
                    meseInPiu = true;
                    break;
                }
            }
            switch (colIndex) {
                case 0:
                    setDay.setText(day1.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);
                    break;
                case 1:
                    setDay.setText(day2.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    if (meseInPiu && index[1]==1 )
                        setDay.setText(day2.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    break;
                case 2:
                    setDay.setText(day3.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    if (meseInPiu && index[2]==1 )
                        setDay.setText(day3.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    break;
                case 3:
                    setDay.setText(day4.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    if (meseInPiu && index[3]==1 )
                        setDay.setText(day4.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    break;
                case 4:
                    setDay.setText(day5.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    if (meseInPiu && index[4]==1 )
                        setDay.setText(day5.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    break;
                case 5:
                    setDay.setText(day6.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    if (meseInPiu && index[5]==1 )
                        setDay.setText(day6.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    break;
                case 6:
                    setDay.setText(day7.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    if (meseInPiu && index[6]==1 )
                        setDay.setText(day7.getText() + "/"+ (week.monthArray[colIndex]) +"/"+ week.year);

                    break;
                default:
            }
            for(int i = 0; i<6; i++) {
                index[i]=0;
            }
        }

        if (setHBegin != null) {
            setHBegin.setText(( rowIndex+8) + ".00");
        }

        if (setHEnd != null) {
            setHEnd.setText(( rowIndex+9) + ".00");
        }
    }

    public void disponibile(MouseEvent mouseEvent) throws SQLException {

        error.setText("");
        String[] parti = setDay.getText().split("/");
        int day = Integer.parseInt(parti[0]);
        int month = Integer.parseInt(parti[1]);
        int year = Integer.parseInt(parti[2]);
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(year,month,day);

        if(source == null){
            error.setText("Seleziona una data ed un orario");
        }
        else if(servizio.getValue() == null){
            error.setText("Inserisci un servizio");
        }
        //gestione date antecedenti ad oggi (impossibile renderle disponibili)
        //prelevo la data di oggi dalla label setDay
        else if(date.isBefore(now) || date.isEqual(now)){
            error.setText("Seleziona una data futura");
        }
        //se è rosso l'orario è già prenotato
        else if (source.getStyle().contains("#FF000080")) {
            error.setText("Slot già prenotato");
        }
        //se è verde l'orario è già disponibile
        else if (source.getStyle().contains("#00FF0080")) {
            error.setText("Slot già disponibile");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Disponibilità");
            alert.setHeaderText("\t\t\t Slot adesso disponibile!\n");
            alert.setContentText("Giorno: " + setDay.getText() + " alle ore: " + setHBegin.getText());
            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            alert.showAndWait();

            source.setStyle("-fx-background-radius: 10; -fx-background-color: #00FF0080; ");

            //salvo lo slot nel db corretto

            //passo al db colonna(giorno), riga(orario), il colore (disponibilita), la sede, il servizio, la settimana
            String query = "INSERT INTO calendario (giorno_colonna, ora_riga, disponibilita, sede, servizio, settimana) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, colIndex);
            statement.setInt(2, rowIndex);
            statement.setInt(3, 2);
            statement.setString(4, sede_corrente);
            statement.setString(5, servizio.getValue());
            statement.setInt(6, settimana_corrente);

            statement.executeUpdate();
        }
    }

    public void nonDisponibile(MouseEvent mouseEvent) throws SQLException {

        error.setText("");
        String[] parti = setDay.getText().split("/");
        int day = Integer.parseInt(parti[0]);
        int month = Integer.parseInt(parti[1]);
        int year = Integer.parseInt(parti[2]);
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(year,month,day);

        if(source == null){
            error.setText("Seleziona una data ed un orario");
        }
        else if(servizio.getValue() == null){
            error.setText("Inserisci un servizio");
        }
        //gestione date antecedenti ad oggi (impossibile renderle disponibili)
        //prelevo la data di oggi dalla label setDay
        else if(date.isBefore(now) || date.isEqual(now)){
            error.setText("Seleziona una data futura");
        }
        //se è trasparente l'orario è già non gestito
        else if (source.getStyle().contains("transparent")) {
            error.setText("Slot già non gestito");
        }
        //se è rosso l'orario è già disponibile
        else if (source.getStyle().contains("#FF000080")) {
            error.setText("Slot già prenotato");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Disponibilità rimossa");
            alert.setHeaderText("\t\t\t Slot adesso non gestito!\n");
            alert.setContentText("Giorno: " + setDay.getText() + " alle ore: " + setHBegin.getText());
            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            alert.showAndWait();

            source.setStyle("-fx-background-radius: 10; -fx-background-color: transparent; ");

            //salvo lo slot nel db corretto

            //passo al db colonna(giorno), riga(orario), il colore (disponibilita), la sede, il servizio, la settimana
            String query = "DELETE FROM calendario WHERE giorno_colonna = ? AND ora_riga = ? AND disponibilita = ? AND " +
                    "sede = ? AND servizio = ? AND settimana = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, colIndex);
            statement.setInt(2, rowIndex);
            statement.setInt(3, 2);
            statement.setString(4, sede_corrente);
            statement.setString(5, servizio.getValue());
            statement.setInt(6, settimana_corrente);

            statement.executeUpdate();
        }
    }

    private void colori_settimana() throws SQLException {

        String query = "SELECT giorno_colonna, ora_riga, disponibilita, settimana FROM calendario WHERE sede = ? AND servizio = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, sede_corrente);
        stmt.setString(2, servizio.getValue());

        /**
         * ALGORITMO: scorro tutta la gridPane popolata di bottoni, SE il bottone con coordinate (row,col)
         * è salvato in db, allora lo coloro in base al numero (2 = verde, 1 = rosso, 0 = grigio"
         */
        for(Node node: gridButtons.getChildren()){

            if(node instanceof Button) {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);

                if(columnIndex == null)
                    columnIndex = 0;
                if(rowIndex == null)
                    rowIndex = 0;

                ResultSet res = stmt.executeQuery();
                boolean flag = false;

                while (res.next() && !flag) {
                    if (columnIndex == res.getInt("giorno_colonna") && rowIndex == res.getInt("ora_riga") && settimana_corrente == res.getInt("settimana")) {
                        flag = true;
                        //se in db è 2, allora coloro di verde
                        if (res.getInt("disponibilita") == 2)
                            node.setStyle("-fx-background-radius: 10; -fx-background-color: #00FF0080; ");
                        //se in db è 1, allora coloro di rosso
                        if (res.getInt("disponibilita") == 1)
                            node.setStyle("-fx-background-radius: 10; -fx-background-color: #FF000080; ");
                    }
                }
                //se il bottone NON è salvato in memoria, lo coloro di grigio
                if (!flag)
                    node.setStyle("-fx-background-radius: 10; -fx-background-color: transparent; ");
            }
        }
    }

    private void listener(){
        servizio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("Servizio selezionato: " + newValue);
            try {
                colori_settimana();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
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

    //ritorno al main se clicco il tasto back
    @FXML
    void goBack(MouseEvent event) throws IOException {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("operatore-login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
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
