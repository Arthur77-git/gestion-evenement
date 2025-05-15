package com.evenements.exception;

/**
 * Exception levée lorsqu'un événement avec le même ID existe déjà.
 */
public class EvenementDejaExistantException extends RuntimeException {

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message décrivant l'erreur
     */
    public EvenementDejaExistantException(String message) {
        super(message);
    }
}