package com.evenements.model;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.ParticipantNonInscritException;
import com.evenements.observer.EvenementObservable;
import com.evenements.observer.ParticipantObserver;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un événement (conférence, concert, etc.).
 * Fournit les attributs et méthodes de base pour tous les types d'événements.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Conference.class, name = "Conference"),
        @JsonSubTypes.Type(value = Concert.class, name = "Concert")
})
public abstract class Evenement implements EvenementObservable {
    private String id;
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int capaciteMax;
    private List<Participant> participants;
    @JsonIgnore
    private List<ParticipantObserver> observateurs;

    /**
     * Constructeur pour initialiser un événement.
     *
     * @param id           Identifiant unique de l'événement
     * @param nom          Nom de l'événement
     * @param date         Date et heure de l'événement
     * @param lieu         Lieu de l'événement
     * @param capaciteMax  Capacité maximale de participants
     */
    public Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.participants = new ArrayList<>();
        this.observateurs = new ArrayList<>();
    }

    /**
     * Ajoute un participant à l'événement et comme observateur.
     * Lance une exception si la capacité maximale est atteinte.
     *
     * @param participant Le participant à ajouter
     * @throws CapaciteMaxAtteinteException si la capacité est dépassée
     */
    public void ajouterParticipant(Participant participant) {
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale de " + capaciteMax + " atteinte pour l'événement " + nom);
        }
        participants.add(participant);
        ajouterObservateur(participant);
    }

    /**
     * Supprime un participant de l'événement et de la liste des observateurs.
     *
     * @param participant Le participant à supprimer
     * @throws ParticipantNonInscritException si le participant n'est pas inscrit
     */
    public void supprimerParticipant(Participant participant) {
        if (!participants.remove(participant)) {
            throw new ParticipantNonInscritException("Le participant " + participant.getNom() + " n'est pas inscrit à l'événement " + nom);
        }
        supprimerObservateur(participant);
    }

    /**
     * Annule l'événement et notifie les observateurs.
     */
    public void annuler() {
        notifierObservateurs("L'événement " + nom + " a été annulé.");
    }

    /**
     * Affiche les détails de l'événement.
     *
     * @return Une chaîne contenant les détails
     */
    public String afficherDetails() {
        return "ID: " + id + ", Nom: " + nom + ", Date: " + date + ", Lieu: " + lieu + ", Capacité: " + capaciteMax;
    }

    @Override
    public void ajouterObservateur(ParticipantObserver observateur) {
        observateurs.add(observateur);
    }

    @Override
    public void supprimerObservateur(ParticipantObserver observateur) {
        observateurs.remove(observateur);
    }

    @Override
    public void notifierObservateurs(String message) {
        for (ParticipantObserver observateur : observateurs) {
            observateur.recevoirNotification(message);
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }
}