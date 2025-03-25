package org.example.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.example.AppController;
import org.example.domain.AgentieTurism;
import org.example.domain.Angajat;
import org.example.domain.Excursie;
import org.example.domain.Rezervare;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainWindow extends Application {
    private Stage primaryStage;

    private static AppController appController;

    private TextField usernameField;
    private TextField passwordField;
    private Button loginButton;
    private Button logoutButton;

    private TableView<Excursie> excursieTableView;
    private TableView<Rezervare> rezervariTableView;
    private TableView<AgentieTurism> agentieTurismTableView;
    private TableView<Angajat> angajatTableView;

    private ObservableList<Excursie> excursieList;
    private ObservableList<Rezervare> rezervareList;
    private ObservableList<AgentieTurism> agentieTurismList;
    private ObservableList<Angajat> angajatList;

    private Angajat loggedUser;


    private TextField numePrenumeClientField;
    private TextField telefonClientField;
    private TextField nrLocuriRezervateField;
    private Button rezervareButton;

    private Excursie selectedExcursie;


    public static void setAppController(AppController appController) {
        MainWindow.appController = appController;
    }

    private void update() {
        excursieList.setAll(appController.excursieRepository.findAll().stream().toList());

    }

    private void updateRezervari() {
        rezervareList.setAll(appController.rezervareRepository.findAll().stream()
                .filter(e-> e.getAgentieTurism().getId().equals(loggedUser.getAgentieTurism().getId()))
                .toList());
    }

    private void showLogin() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add( new Label("Usernamr:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2, 2, 1);

        Scene loginScene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void showDashboard() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TableColumn obiectivExcursie = new TableColumn("Obiectiv");
        obiectivExcursie.setCellValueFactory(new PropertyValueFactory<Excursie, String>("obiectiv"));

        TableColumn firmaTransportExcursie = new TableColumn("FirmaTransport");
        firmaTransportExcursie.setCellValueFactory(new PropertyValueFactory<Excursie, String>("firmaTransport"));

        TableColumn nrLocuriExcursie = new TableColumn("NrLocuri");
        nrLocuriExcursie.setCellValueFactory(new PropertyValueFactory<Excursie, Integer>("nrLocuri"));

        TableColumn dataOraPlecateExcursie = new TableColumn("DataOraPlcare");
        dataOraPlecateExcursie.setCellValueFactory(new PropertyValueFactory<Excursie, LocalDateTime>("dataOraPlcare"));

        TableColumn pretExcursie = new TableColumn("PretExcursie");
        pretExcursie.setCellValueFactory(new PropertyValueFactory<Excursie, Integer>("pret"));

        updateRezervari();

        TableColumn numeClientRezervare = new TableColumn("numeClient");
        numeClientRezervare.setCellValueFactory(new PropertyValueFactory<Rezervare, String>("numeClient"));

        TableColumn numarTelefonRezervare = new TableColumn("numarTelefon");
        numarTelefonRezervare.setCellValueFactory(new PropertyValueFactory<Rezervare, String>("numarTelefon"));

        TableColumn nrLocuriRezervateRezervare = new TableColumn("nrLocuriRezervate");
        nrLocuriRezervateRezervare.setCellValueFactory(new PropertyValueFactory<Rezervare, Integer>("nrLocuriRezervate"));

        TableColumn excursieRezervare = new TableColumn("excursie");
        excursieRezervare.setCellValueFactory(new PropertyValueFactory<Rezervare, String>("excursie"));

        excursieTableView.getColumns().addAll(obiectivExcursie, firmaTransportExcursie, nrLocuriExcursie, dataOraPlecateExcursie, pretExcursie);
        excursieTableView.setItems(excursieList);

        rezervariTableView.getColumns().addAll(numeClientRezervare, numarTelefonRezervare, nrLocuriRezervateRezervare, excursieRezervare);
        rezervariTableView.setItems(rezervareList);


        Scene loggedInScene = new Scene(gridPane, 1000, 700);
        gridPane.add(logoutButton, 0, 3, 2, 1);

        gridPane.add(excursieTableView, 0, 0, 2, 1);
        gridPane.add(rezervariTableView, 0, 2, 2, 1);

        gridPane.add(new Label("Nume prenume client:"), 3, 0, 1, 1);
        gridPane.add(new Label("Telefon client:"), 3, 1, 2, 1);
        gridPane.add(new Label("Nr locuri dorite:"), 3, 2, 3, 1);

        gridPane.add(numePrenumeClientField, 4, 0, 1, 1);
        gridPane.add(telefonClientField, 4, 1, 1, 1);
        gridPane.add(nrLocuriRezervateField, 4, 2, 1, 1);

        gridPane.add(rezervareButton, 2, 3, 2, 1);


        primaryStage.setScene(loggedInScene);
        primaryStage.setTitle(loggedUser.getUsername() + "'s Dashboard");
    }

    private void initialize() {
        excursieTableView = new TableView<>();
        rezervariTableView = new TableView<>();

        excursieTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedExcursie = newSelection;
            }
        });

        agentieTurismList = FXCollections.observableArrayList();
        angajatList = FXCollections.observableArrayList();
        excursieList = FXCollections.observableArrayList();
        rezervareList = FXCollections.observableArrayList();

        usernameField = new TextField();
        passwordField = new TextField();
        loginButton = new Button("Login");
        logoutButton = new Button("Logout");

        logoutButton.setOnAction(e -> {
            loggedUser = null;
            showLogin();
        });

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Optional<Angajat> angajat = appController.angajatRepository.findByUsername(username);

            if (angajat.isPresent()) {
                if (angajat.get().getPassword().equals(password)) {
                    loggedUser = angajat.get();
                    showDashboard();
                } else {
                    showAlert("Invalid username or password.");
                }
            }
        });

        primaryStage = new Stage();

        numePrenumeClientField = new TextField();
        telefonClientField = new TextField();
        nrLocuriRezervateField = new TextField();
        rezervareButton = new Button("Rezervare");
        rezervareButton.setOnAction(e -> {
            try {
                handleRezervare();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        update();
    }

    private void handleRezervare() throws SQLException {
        if (selectedExcursie == null) {
            showAlert("Please select an excursion before making a reservation.");
            return;
        }

        String numeClient = numePrenumeClientField.getText();
        String telefonClient = telefonClientField.getText();
        String locuriStr = nrLocuriRezervateField.getText();

        if (numeClient.isEmpty() || telefonClient.isEmpty() || locuriStr.isEmpty()) {
            showAlert("All fields must be filled!");
            return;
        }

        int nrLocuriRezervate;
        try {
            nrLocuriRezervate = Integer.parseInt(locuriStr);
            if (nrLocuriRezervate <= 0) {
                showAlert("Number of seats must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number for seats.");
            return;
        }

        System.out.println(appController.excursieRepository.remaingSeats(selectedExcursie));
        if (nrLocuriRezervate > appController.excursieRepository.remaingSeats(selectedExcursie)) {
            showAlert("Not enough seats available.");
            return;
        }

        Rezervare newRezervare = new Rezervare(numeClient, nrLocuriRezervate, telefonClient, selectedExcursie, loggedUser.getAgentieTurism());
        appController.rezervareRepository.save(newRezervare);

        selectedExcursie.setNrLocuri(selectedExcursie.getNrLocuri() - nrLocuriRezervate);
        appController.excursieRepository.update(selectedExcursie);

        updateRezervari();

        showAlert("Reservation successfully created!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) throws Exception {
        initialize();
        showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
