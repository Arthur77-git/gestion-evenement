package com.evenements.model;

import java.time.LocalDateTime;

/**
 * Représente une conférence, un type spécifique d'événement.
 */
public class Conference extends Evenement {
    private String orateur;
    private String theme;

    /**
     * Constructeur pour initialiser une conférence.
     *
     * @param id           Identifiant unique
     * @param nom          Nom de la conférence
     * @param date         Date et heure
     * @param lieu         Lieu
     * @param capaciteMax  Capacité maximale
     * @param orateur      Orateur principal
     * @param theme        Thème de la conférence
     */
    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String orateur, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.orateur = orateur;
        this.theme = theme;
    }

    @Override
    public String afficherDetails() {
        return super.afficherDetails() + ", Orateur: " + orateur + ", Thème: " + theme;
    }

    // Getters
    public String getOrateur() {
        return orateur;
    }

    public String getTheme() {
        return theme;
    }
}