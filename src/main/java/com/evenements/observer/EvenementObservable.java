package com.evenements.observer;

/**
 * Interface pour les objets pouvant être observés (sujets).
 * Permet de gérer des observateurs et de les notifier.
 */
public interface EvenementObservable {
    /**
     * Ajoute un observateur à la liste.
     *
     * @param observateur L'observateur à ajouter
     */
    void ajouterObservateur(ParticipantObserver observateur);

    /**
     * Supprime un observateur de la liste.
     *
     * @param observateur L'observateur à supprimer
     */
    void supprimerObservateur(ParticipantObserver observateur);

    /**
     * Notifie tous les observateurs avec un message.
     *
     * @param message Le message à envoyer
     */
    void notifierObservateurs(String message);
}