package com.evenements.model;

import com.evenements.observer.ParticipantObserver;
import com.evenements.service.NotificationService;

/**
 * Représente un participant à un événement, capable de recevoir des notifications.
 */
public class Participant implements ParticipantObserver {
    private final String id;
    private final String nom;
    private final String email;
    private final NotificationService notificationService;

    /**
     * Constructeur pour initialiser un participant.
     *
     * @param id                  Identifiant unique
     * @param nom                 Nom du participant
     * @param email               Adresse email
     * @param notificationService Service pour envoyer les notifications
     */
    public Participant(String id, String nom, String email, NotificationService notificationService) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.notificationService = notificationService;
    }

    /**
     * Reçoit une notification et l'envoie via le service de notification de manière asynchrone.
     *
     * @param message Le message de la notification
     */
    @Override
    public void recevoirNotification(String message) {
        String formattedMessage = String.format("Notification pour %s (%s): %s", nom, email, message);
        notificationService.envoyerNotification(formattedMessage);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}