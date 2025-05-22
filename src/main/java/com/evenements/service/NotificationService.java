package com.evenements.service;

import java.util.concurrent.CompletableFuture;

/**
 * Interface pour l'envoi de notifications aux participants.
 */
public interface NotificationService {

    /**
     * Envoie une notification de manière asynchrone.
     *
     * @param message Le message à envoyer
     * @return Un CompletableFuture représentant la complétion de l'envoi
     */
    CompletableFuture<Void> envoyerNotification(String message);
}