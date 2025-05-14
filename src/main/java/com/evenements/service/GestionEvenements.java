package com.evenements.service;

import com.evenements.model.Evenement;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe Singleton pour gérer la collection d'événements.
 */
public class GestionEvenements {
    private static GestionEvenements instance;
    private Map<String, Evenement> evenements;

    /**
     * Constructeur privé pour empêcher l'instanciation directe.
     */
    private GestionEvenements() {
        evenements = new HashMap<>();
    }

    /**
     * Retourne l'instance unique de GestionEvenements.
     *
     * @return L'instance singleton
     */
    public static GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }

    /**
     * Ajoute un événement à la collection.
     *
     * @param evenement L'événement à ajouter
     */
    public void ajouterEvenement(Evenement evenement) {
        evenements.put(evenement.getId(), evenement);
    }

    /**
     * Supprime un événement par son ID.
     *
     * @param id L'identifiant de l'événement
     */
    public void supprimerEvenement(String id) {
        evenements.remove(id);
    }

    /**
     * Recherche un événement par son ID.
     *
     * @param id L'identifiant de l'événement
     * @return L'événement correspondant, ou null si non trouvé
     */
    public Evenement rechercherEvenement(String id) {
        return evenements.get(id);
    }

    // Getter
    public Map<String, Evenement> getEvenements() {
        return new HashMap<>(evenements);
    }
}