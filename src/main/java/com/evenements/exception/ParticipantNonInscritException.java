package com.evenements.exception;

/**
 * Exception levée lorsqu'on tente de supprimer un participant non inscrit à un événement.
 */
public class ParticipantNonInscritException extends RuntimeException {

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message décrivant l'erreur
     */
    public ParticipantNonInscritException(String message) {
        super(message);
    }
}