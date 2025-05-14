package com.evenements.service;

/**
 * Interface pour l'envoi de notifications.
 */
public interface NotificationService {
    /**
     * Envoie une notification avec un message.
     *
     * @param message Le message à envoyer
     */
    void envoyerNotification(String message);
}