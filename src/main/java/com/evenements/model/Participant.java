package com.evenements.model;

import com.evenements.observer.ParticipantObserver;
import com.evenements.service.NotificationService;

/**
 * Représente un participant à un événement.
 */
public class Participant implements ParticipantObserver {
    private String id;
    private String nom;
    private String email;
    private NotificationService notificationService;

    /**
     * Constructeur pour initialiser un participant.
     *
     * @param id                Identifiant unique
     * @param nom               Nom du participant
     * @param email             Adresse email
     * @param notificationService Service pour envoyer les notifications
     */
    public Participant(String id, String nom, String email, NotificationService notificationService) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.notificationService = notificationService;
    }

    @Override
    public void recevoirNotification(String message) {
        notificationService.envoyerNotification("Notification pour " + nom + " (" + email + "): " + message);
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