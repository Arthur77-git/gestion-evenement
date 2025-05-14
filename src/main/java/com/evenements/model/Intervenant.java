package com.evenements.model;

/**
 * Représente un intervenant dans une conférence.
 */
public class Intervenant {
    private String nom;
    private String specialite;

    /**
     * Constructeur pour initialiser un intervenant.
     *
     * @param nom        Nom de l'intervenant
     * @param specialite Spécialité ou domaine d'expertise
     */
    public Intervenant(String nom, String specialite) {
        this.nom = nom;
        this.specialite = specialite;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getSpecialite() {
        return specialite;
    }
}