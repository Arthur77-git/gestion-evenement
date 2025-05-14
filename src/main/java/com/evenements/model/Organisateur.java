package com.evenements.model;

import com.evenements.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un organisateur, qui est un type de participant pouvant gérer des événements.
 */
public class Organisateur extends Participant {
    private List<Evenement> evenementsOrganises;

    /**
     * Constructeur pour initialiser un organisateur.
     *
     * @param id                  Identifiant unique
     * @param nom                 Nom de l'organisateur
     * @param email               Adresse email
     * @param notificationService Service pour envoyer les notifications
     */
    public Organisateur(String id, String nom, String email, NotificationService notificationService) {
        super(id, nom, email, notificationService);
        this.evenementsOrganises = new ArrayList<>();
    }

    /**
     * Ajoute un événement à la liste des événements organisés.
     *
     * @param evenement L'événement à ajouter
     */
    public void ajouterEvenementOrganise(Evenement evenement) {
        evenementsOrganises.add(evenement);
    }

    // Getters
    public List<Evenement> getEvenementsOrganises() {
        return new ArrayList<>(evenementsOrganises);
    }
}