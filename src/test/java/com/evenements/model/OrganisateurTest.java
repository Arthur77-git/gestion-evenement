package com.evenements.model;

import com.evenements.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Organisateur.
 */
public class OrganisateurTest {

    private Organisateur organisateur;
    private Concert concert;
    private TestNotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new TestNotificationService();
        organisateur = new Organisateur("O1", "Eve", "eve@email.com", notificationService);
        concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 100, "The Band", "Rock");
    }

    @Test
    public void testAjouterEvenementOrganise() {
        organisateur.ajouterEvenementOrganise(concert);
        assertTrue(organisateur.getEvenementsOrganises().contains(concert));
    }

    @Test
    public void testRecevoirNotification() throws Exception {
        organisateur.recevoirNotification("Événement annulé");
        CompletableFuture<Void> future = notificationService.getLastFuture();
        future.get(1, TimeUnit.SECONDS);
        assertEquals(1, notificationService.getNotifications().size());
        assertEquals("Notification pour Eve (eve@email.com): Événement annulé",
                notificationService.getNotifications().get(0));
    }

    private static class TestNotificationService implements NotificationService {
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
}