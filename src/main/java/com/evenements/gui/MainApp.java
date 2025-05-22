package com.evenements.gui;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.model.Concert;
import com.evenements.model.Conference;
import com.evenements.model.Evenement;
import com.evenements.model.Participant;
import com.evenements.service.GestionEvenements;
import com.evenements.service.TestNotificationService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;

public class MainApp extends Application {

    private GestionEvenements gestionEvenements;
    private ObservableList<Evenement> evenementList;
    private TextField idField;
    private TextField nomField;
    private TextField lieuField;
    private TextField capaciteField;
    private TextField artisteOrateurField;
    private TextField genreThemeField;
    private ComboBox<String> typeComboBox;
    private TextField participantNomField;
    private TextField participantEmailField;
    private Label statusLabel;
    private ListView<Evenement> evenementListView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        gestionEvenements = GestionEvenements.getInstance();
        evenementList = FXCollections.observableArrayList();

        // Conteneur principal
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        // Titre
        Label titleLabel = new Label("Gestion d'Événements");
        titleLabel.getStyleClass().add("title-label");

        // Section : Liste des événements
        VBox eventListSection = new VBox(10);
        eventListSection.getStyleClass().add("form-section");
        Label eventsLabel = new Label("Événements");
        evenementListView = new ListView<>(evenementList);
        evenementListView.setPrefHeight(150);
        evenementListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Evenement item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNom() + " (" + item.getLieu() + ")");
            }
        });
        Button supprimerEvenementButton = new Button("Supprimer Événement");
        supprimerEvenementButton.getStyleClass().add("button-danger");
        supprimerEvenementButton.setOnAction(e -> supprimerEvenement());
        eventListSection.getChildren().addAll(eventsLabel, evenementListView, supprimerEvenementButton);

        // Section : Ajouter un événement
        VBox addEventSection = new VBox(10);
        addEventSection.getStyleClass().add("form-section");
        Label addEventLabel = new Label("Ajouter un Événement");
        idField = new TextField();
        idField.setPromptText("ID de l'événement");
        nomField = new TextField();
        nomField.setPromptText("Nom de l'événement");
        lieuField = new TextField();
        lieuField.setPromptText("Lieu");
        capaciteField = new TextField();
        capaciteField.setPromptText("Capacité");
        artisteOrateurField = new TextField();
        artisteOrateurField.setPromptText("Artiste/Orateur");
        genreThemeField = new TextField();
        genreThemeField.setPromptText("Genre/Thème");
        typeComboBox = new ComboBox<>(FXCollections.observableArrayList("Concert", "Conférence"));
        typeComboBox.setValue("Concert");
        Button ajouterEvenementButton = new Button("Ajouter Événement");
        ajouterEvenementButton.getStyleClass().add("button-primary");
        ajouterEvenementButton.setOnAction(e -> ajouterEvenement());
        addEventSection.getChildren().addAll(
                addEventLabel, idField, nomField, lieuField, capaciteField,
                artisteOrateurField, genreThemeField, typeComboBox, ajouterEvenementButton
        );

        // Section : Ajouter un participant
        VBox addParticipantSection = new VBox(10);
        addParticipantSection.getStyleClass().add("form-section");
        Label participantLabel = new Label("Inscrire un Participant");
        participantNomField = new TextField();
        participantNomField.setPromptText("Nom du participant");
        participantEmailField = new TextField();
        participantEmailField.setPromptText("Email");
        Button ajouterParticipantButton = new Button("Inscrire Participant");
        ajouterParticipantButton.getStyleClass().add("button-primary");
        ajouterParticipantButton.setOnAction(e -> ajouterParticipant());
        addParticipantSection.getChildren().addAll(
                participantLabel, participantNomField, participantEmailField, ajouterParticipantButton
        );

        // Statut
        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        // Ajouter toutes les sections au conteneur principal
        root.getChildren().addAll(
                titleLabel, eventListSection, addEventSection, addParticipantSection, statusLabel
        );

        // Configurer la scène
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/evenements/gui/styles.css").toExternalForm());
        primaryStage.setTitle("Gestion d'Événements");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Initialiser la liste
        refreshEvenementList();
    }

    private void ajouterEvenement() {
        try {
            String id = idField.getText().trim();
            String nom = nomField.getText().trim();
            String lieu = lieuField.getText().trim();
            String capaciteText = capaciteField.getText().trim();
            String extra1 = artisteOrateurField.getText().trim();
            String extra2 = genreThemeField.getText().trim();
            String type = typeComboBox.getValue();

            if (id.isEmpty() || nom.isEmpty() || lieu.isEmpty() || capaciteText.isEmpty()) {
                statusLabel.setText("Erreur : Tous les champs requis doivent être remplis.");
                return;
            }

            int capacite = Integer.parseInt(capaciteText);
            if (capacite <= 0) {
                statusLabel.setText("Erreur : La capacité doit être positive.");
                return;
            }

            Evenement evenement = type.equals("Concert") ?
                    new Concert(id, nom, LocalDateTime.now(), lieu, capacite,
                            extra1.isEmpty() ? "Artiste" : extra1, extra2.isEmpty() ? "Rock" : extra2) :
                    new Conference(id, nom, LocalDateTime.now(), lieu, capacite,
                            extra1.isEmpty() ? "Orateur" : extra1, extra2.isEmpty() ? "Thème" : extra2);

            gestionEvenements.ajouterEvenement(evenement);
            refreshEvenementList();
            statusLabel.setText("Événement ajouté avec succès !");
            clearFields();
        } catch (EvenementDejaExistantException e) {
            statusLabel.setText("Erreur : Cet événement existe déjà.");
        } catch (NumberFormatException e) {
            statusLabel.setText("Erreur : La capacité doit être un nombre.");
        }
    }

    private void supprimerEvenement() {
        Evenement selected = evenementListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            gestionEvenements.supprimerEvenement(selected.getId());
            refreshEvenementList();
            statusLabel.setText("Événement supprimé avec succès !");
        } else {
            statusLabel.setText("Erreur : Veuillez sélectionner un événement.");
        }
    }

    private void ajouterParticipant() {
        Evenement selected = evenementListView.getSelectionModel().getSelectedItem();
        String nom = participantNomField.getText().trim();
        String email = participantEmailField.getText().trim();
        if (selected == null) {
            statusLabel.setText("Erreur : Veuillez sélectionner un événement.");
            return;
        }
        if (nom.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Erreur : Nom et email requis.");
            return;
        }
        try {
            Participant participant = new Participant("P" + System.currentTimeMillis(), nom, email, new TestNotificationService());
            selected.ajouterParticipant(participant);
            statusLabel.setText("Participant ajouté à " + selected.getNom() + " !");
            participantNomField.clear();
            participantEmailField.clear();
        } catch (CapaciteMaxAtteinteException e) {
            statusLabel.setText("Erreur : Capacité maximale atteinte.");
        }
    }

    private void refreshEvenementList() {
        evenementList.clear();
        evenementList.addAll(gestionEvenements.getEvenements().values());
    }

    private void clearFields() {
        idField.clear();
        nomField.clear();
        lieuField.clear();
        capaciteField.clear();
        artisteOrateurField.clear();
        genreThemeField.clear();
    }
}