package com.evenements.model;

import java.time.LocalDateTime;

/**
 * Représente un concert, un type spécifique d'événement.
 */
public class Concert extends Evenement {
    private final String artiste;
    private final String genreMusical;

    /**
     * Constructeur pour initialiser un concert.
     *
     * @param id           Identifiant unique
     * @param nom          Nom du concert
     * @param date         Date et heure
     * @param lieu         Lieu
     * @param capaciteMax  Capacité maximale
     * @param artiste      Nom de l'artiste ou groupe
     * @param genreMusical Genre musical (ex. rock, jazz)
     */
    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    @Override
    public String afficherDetails() {
        return super.afficherDetails() + ", Artiste: " + artiste + ", Genre: " + genreMusical;
    }

    // Getters
    public String getArtiste() {
        return artiste;
    }

    public String getGenreMusical() {
        return genreMusical;
    }
}