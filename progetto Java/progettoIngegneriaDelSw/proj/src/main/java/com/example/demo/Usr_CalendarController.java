package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;



public class Usr_CalendarController extends Usr_Controller implements Initializable {

    @FXML
    public Label servizio_scelto;
    public GridPane gridButtons;
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

    LocalDate now = LocalDate.now();
    LocalDate firstNextDate = now.plusMonths(1);
    Integer rowIndex = null;
    Integer colIndex = null;
    Node source = null;
    Week week = Week.getInstance(false); //false per usare utente
    int[] days= week.getWeek();
    int settimana_corrente;
    String sede_corrente = getSede();
    String servizio_corrente = getServizio();
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/questura", "root", "");

    public Usr_CalendarController() throws SQLException {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDate date = LocalDate.of(week.year, week.month, week.day);
        settimana_corrente = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    //(date.get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 53) - 1;
        System.out.println("Settimana corrente: " + settimana_corrente);

        servizio_scelto.setText(servizio_corrente.toUpperCase());
        error.setText("Selezionare una data a partire da: " + firstNextDate.getDayOfMonth() + "/" +
                firstNextDate.getMonthValue() + "/" + firstNextDate.getYear() + "\n(minimo 30 giorni a partire dalla data odierna)");

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
        System.out.println("Settimana corrente: " + settimana_corrente);
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
        System.out.println("Settimana corrente: " + settimana_corrente);
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
        //System.out.println("RIGA: " + rowIndex);
        //System.out.println("COLONNA: " + colIndex);


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
                    //System.out.println("MMMMMMMMHHHHHHHHHH.......");
            }
            for(int i = 0; i<6; i++) {
                index[i]=0;
            }
            meseInPiu=false;

        }

        if (setHBegin != null) {
            setHBegin.setText(( rowIndex+8) + ".00");
        }

        if (setHEnd != null) {
            setHEnd.setText(( rowIndex+9) + ".00");
        }
    }

    public void prenota(MouseEvent mouseEvent) throws SQLException, IOException {

        //gestione concorrenza in caso prenotazione risulti disponibile (= 2) da lato user ma sia stata nel frattempo
        //aggiornata a non disponibile (= 1) dal lato db
        String query;
        PreparedStatement statement;
        ResultSet resultSet;
        int query_disp = 0;

        try {
            //statement della query al db
            query = "SELECT disponibilita FROM calendario WHERE giorno_colonna = ? AND ora_riga = ? AND sede= ? AND servizio = ? AND settimana = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, colIndex);                 //giorno
            statement.setInt(2, rowIndex);                 //ora
            statement.setString(3, sede_corrente);         //sede
            statement.setString(4, servizio_corrente);     //servizio
            statement.setInt(5, settimana_corrente);       //settimana

            System.out.println(query);
            System.out.println(colIndex);
            System.out.println(rowIndex);
            System.out.println(sede_corrente);
            System.out.println(servizio_corrente);
            System.out.println(settimana_corrente);

            //esecuzione query
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                query_disp = resultSet.getInt("disponibilita");
                System.out.println("disponibilità = " + query_disp);
            }

            //chiusura risorse
            resultSet.close();
            statement.close();

            //se la prenotazione risulta disponibile anche da lato db allora procedo con la prenotazione
            if(query_disp == 2) {
                //prelevo la data di oggi dalla label setDay
                String[] parti = setDay.getText().split("/");
                int day = Integer.parseInt(parti[0]);
                int month = Integer.parseInt(parti[1]);
                int year = Integer.parseInt(parti[2]);

                LocalDate selected = LocalDate.of(year, month, day);
                //orario selezionato è in data non valida, meno di 30 giorni dalla data odierna
                if (selected.isBefore(firstNextDate)) {
                    error.setText("Prenotazione non disponibile.\nSelezionare un orario verde a partire dalla data: " + firstNextDate.getDayOfMonth() + "/" +
                            firstNextDate.getMonthValue() + "/" + firstNextDate.getYear() + "\n(minimo 30 giorni dalla data odierna)");
                }
                //orario valido, aggiorno il db
                else {
                    error.setText("");
                    //modifico disponibilità nel db in base a colonna(giorno), riga(orario), la sede, il servizio, la settimana
                    query = "UPDATE calendario SET disponibilita = 1, utente_prenotato = ? WHERE giorno_colonna = ? AND ora_riga = ? AND sede= ? AND servizio = ? AND settimana = ?";
                    statement = connection.prepareStatement(query);

                    statement.setString(1, getCod_fiscale());         //utente prenotato
                    statement.setInt(2, colIndex);                 //giorno
                    statement.setInt(3, rowIndex);                 //ora
                    statement.setString(4, sede_corrente);         //sede
                    statement.setString(5, servizio_corrente);     //servizio
                    statement.setInt(6, settimana_corrente);       //settimana

                    statement.executeUpdate();

                    //aggiorno i colori degli slot e mostru un alertBox per avvisare dell'avvenuta prenotazione
                    colori_settimana();
                    succesfulReservationAlert();
                    System.out.println("prenotazione fatta da " + getCod_fiscale());
                }
            }
            //se trovo un valore diverso da disponibile (2) allora i dati dello user non sono aggiornati
            else{
                //se lo slot è verde vuol dire che le disponibilità non sono state aggiornate e la pagina va ricaricata
                if (source.getStyle().contains("#00FF0080")) {

                    error.setText("Pagina scaduta.\nLa prenotazione selezionata non è più disponibile.\nSelezionare un'altro orario verde.");
                    colori_settimana();
                }
                //se lo slot è rosso l'orario è già prenotato
                else if (source.getStyle().contains("#FF000080")) {
                    error.setText("Prenotazione non disponibile.\nSelezionare un orario verde.");
                }
                //se lo slot non contiene un colore l'orario non è disponibile
                else if (source.getStyle().contains("transparent")) {
                    error.setText("Orario non disponibile.\nSelezionare un orario verde.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void succesfulReservationAlert() throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Prenotazione");
        alert.setHeaderText("\t\t\tPrenotazione effettuata!\n");
        alert.setContentText("Giorno: " + setDay.getText() + " alle ore: " + setHBegin.getText() + "\nDocumenti necessari per il ritiro:\n" +
                "\t    -Modulo di richiesta compilato\n" +
                "\t    -Marca da bollo\n" +
                "\t    -Ricevuta del versamento\n" +
                "\t    -Due fototessere su sfondo bianco\n" +
                "\t    -Passaporto precedente (se in possesso)");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
        Stage stage = (Stage) goHomeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    private void colori_settimana() throws SQLException {

        String query = "SELECT giorno_colonna, ora_riga, disponibilita, settimana FROM calendario WHERE sede = ? AND servizio = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, sede_corrente);
        stmt.setString(2, servizio_corrente);

        /**
         * ALGORITMO: scorro tutta la gridPane popolata di bottoni, SE il bottone con coordinate (row,col)
         * è salvato in db, allora lo coloro in base al numero (2 = verde, 1 = rosso, 0 = grigio")
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
                    if (columnIndex == res.getInt("giorno_colonna") && rowIndex == res.getInt("ora_riga")
                            && settimana_corrente == res.getInt("settimana")) {
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
    }

    @Override
    public void setCod_fiscale(String cod_fiscale) {
        this.cod_fiscale = cod_fiscale;
    }

    @Override
    public String getCod_fiscale() {
        return cod_fiscale;
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
