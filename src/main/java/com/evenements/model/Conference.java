package com.evenements.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une conférence, un type spécifique d'événement.
 */
public class Conference extends Evenement {
    private String theme;
    private List<Intervenant> intervenants;

    /**
     * Constructeur pour initialiser une conférence.
     *
     * @param id           Identifiant unique
     * @param nom          Nom de la conférence
     * @param date         Date et heure
     * @param lieu         Lieu
     * @param capaciteMax  Capacité maximale
     * @param theme        Thème de la conférence
     */
    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = new ArrayList<>();
    }

    /**
     * Ajoute un intervenant à la conférence.
     *
     * @param intervenant L'intervenant à ajouter
     */
    public void ajouterIntervenant(Intervenant intervenant) {
        intervenants.add(intervenant);
    }

    @Override
    public String afficherDetails() {
        StringBuilder details = new StringBuilder(super.afficherDetails());
        details.append(", Thème: ").append(theme);
        details.append(", Intervenants: ");
        for (Intervenant i : intervenants) {
            details.append(i.getNom()).append(" ");
        }
        return details.toString();
    }

    // Getters
    public String getTheme() {
        return theme;
    }

    public List<Intervenant> getIntervenants() {
        return new ArrayList<>(intervenants);
    }
}