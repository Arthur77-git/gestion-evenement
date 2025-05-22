package com.evenements.model;

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
 * Tests unitaires pour la classe Conference.
 */
public class ConferenceTest {

    private Conference conference;
    private Participant participant;
    private TestNotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new TestNotificationService();
        conference = new Conference("CF1", "Conf Tech", LocalDateTime.now(), "Lyon", 50, "Dr. Smith", "IA");
        participant = new Participant("P1", "Alice", "alice@email.com", notificationService);
    }

    @Test
    public void testAjouterEtSupprimerParticipant() throws Exception {
        conference.ajouterParticipant(participant);
        assertTrue(conference.getParticipants().contains(participant), "Le participant doit être inscrit");

        conference.supprimerParticipant(participant);
        assertFalse(conference.getParticipants().contains(participant), "Le participant doit être supprimé");

        conference.annuler();
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
            conference.supprimerParticipant(p2);
        });
        assertEquals("Le participant Bob n'est pas inscrit à l'événement Conf Tech", exception.getMessage());
    }

    @Test
    public void testAfficherDetails() {
        String details = conference.afficherDetails();
        assertTrue(details.contains("Orateur: Dr. Smith"), "Doit inclure l'orateur");
        assertTrue(details.contains("Thème: IA"), "Doit inclure le thème");
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