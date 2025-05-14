package com.evenements.service;

/**
 * Implémentation de NotificationService qui affiche les notifications dans la console.
 */
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        System.out.println("Console: " + message);
    }
}