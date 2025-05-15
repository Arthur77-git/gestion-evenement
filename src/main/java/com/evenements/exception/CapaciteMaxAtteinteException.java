package com.evenements.exception;

/**
 * Exception levée lorsque la capacité maximale d'un événement est atteinte.
 */
public class CapaciteMaxAtteinteException extends RuntimeException {

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message décrivant l'erreur
     */
    public CapaciteMaxAtteinteException(String message) {
        super(message);
    }
}