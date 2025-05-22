package com.evenements.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implémentation de NotificationService pour les tests.
 */
public class TestNotificationService implements NotificationService {
    private List<String> notifications = new ArrayList<>();
    private CompletableFuture<Void> lastFuture;

    @Override
    public CompletableFuture<Void> envoyerNotification(String message) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            notifications.add(message);
        });
        this.lastFuture = future;
        return future;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public CompletableFuture<Void> getLastFuture() {
        return lastFuture;
    }
}