package com.evenements.observer;

/**
 * Interface pour les objets pouvant recevoir des notifications (observateurs).
 */
public interface ParticipantObserver {
    /**
     * Reçoit une notification avec un message.
     *
     * @param message Le message envoyé par le sujet
     */
    void recevoirNotification(String message);
}