package com.evenements.model;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.exception.ParticipantNonInscritException;
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
 * Tests unitaires pour la classe Evenement.
 */
public class EvenementTest {

    private Concert concert;
    private Participant participant;
    private TestNotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new TestNotificationService();
        concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 2, "The Band", "Rock");
        participant = new Participant("P1", "Alice", "alice@email.com", notificationService);
    }

    @Test
    public void testAjouterParticipant() {
        concert.ajouterParticipant(participant);
        assertTrue(concert.getParticipants().contains(participant), "Le participant doit être inscrit");
    }

    @Test
    public void testAjouterParticipantCapaciteMaxAtteinte() {
        Participant p2 = new Participant("P2", "Bob", "bob@email.com", notificationService);
        concert.ajouterParticipant(participant);
        concert.ajouterParticipant(p2);

        Participant p3 = new Participant("P3", "Charlie", "charlie@email.com", notificationService);
        Exception exception = assertThrows(CapaciteMaxAtteinteException.class, () -> {
            concert.ajouterParticipant(p3);
        });
        assertEquals("Capacité maximale de 2 atteinte pour l'événement Concert Rock", exception.getMessage());
    }

    @Test
    public void testSupprimerParticipant() throws Exception {
        concert.ajouterParticipant(participant);
        concert.supprimerParticipant(participant);
        assertFalse(concert.getParticipants().contains(participant), "Le participant doit être supprimé");

        concert.annuler();
        CompletableFuture<Void> future = notificationService.getLastFuture();
        if (future != null) {
            future.get(1, TimeUnit.SECONDS);
        }
        assertEquals(0, notificationService.getNotifications().size(), "Aucune notification après désinscription");
    }

    @Test
    public void testSupprimerParticipantNonInscrit() {
        Participant p2 = new Participant("P2", "Bob", "bob@email.com", notificationService);
        Exception exception = assertThrows(ParticipantNonInscritException.class, () -> {
            concert.supprimerParticipant(p2);
        });
        assertEquals("Le participant Bob n'est pas inscrit à l'événement Concert Rock", exception.getMessage());
    }

    @Test
    public void testAnnulerEvenement() throws Exception {
        concert.ajouterParticipant(participant);
        concert.annuler();
        CompletableFuture<Void> future = notificationService.getLastFuture();
        future.get(1, TimeUnit.SECONDS);
        assertEquals(1, notificationService.getNotifications().size());
        assertEquals("Notification pour Alice (alice@email.com): L'événement Concert Rock a été annulé.",
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