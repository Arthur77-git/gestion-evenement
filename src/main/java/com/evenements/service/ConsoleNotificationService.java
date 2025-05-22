package com.evenements.service;

import java.util.concurrent.CompletableFuture;

/**
 * Implémentation de NotificationService qui affiche les notifications dans la console.
 */
public class ConsoleNotificationService implements NotificationService {

    /**
     * Envoie une notification de manière asynchrone en l'affichant dans la console.
     *
     * @param message Le message à envoyer
     * @return Un CompletableFuture représentant la complétion de l'envoi
     */
    @Override
    public CompletableFuture<Void> envoyerNotification(String message) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Notification: " + message);
        });
    }
}