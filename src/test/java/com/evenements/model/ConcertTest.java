package com.evenements.model;

import com.evenements.exception.ParticipantNonInscritException;
import com.evenements.service.NotificationService;
import com.evenements.service.TestNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Concert.
 */
public class ConcertTest {

    private Concert concert;
    private Participant participant;
    private TestNotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new TestNotificationService();
        concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 50, "The Band", "Rock");
        participant = new Participant("P1", "Alice", "alice@email.com", notificationService);
    }

    @Test
    public void testAjouterEtSupprimerParticipant() throws Exception {
        concert.ajouterParticipant(participant);
        assertTrue(concert.getParticipants().contains(participant), "Le participant doit être inscrit");

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
    public void testAfficherDetails() {
        String details = concert.afficherDetails();
        assertTrue(details.contains("Artiste: The Band"), "Doit inclure l'artiste");
        assertTrue(details.contains("Genre: Rock"), "Doit inclure le genre");
    }
}