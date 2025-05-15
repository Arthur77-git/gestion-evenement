package com.evenements.model;

import com.evenements.exception.CapaciteMaxAtteinteException;
import com.evenements.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Evenement, avec un focus sur le pattern Observer et les exceptions.
 */
public class EvenementTest {

    private Concert concert;
    private Participant participant;
    private TestNotificationService notificationService;

    /**
     * Configure les objets avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        notificationService = new TestNotificationService();
        concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 2, "The Band", "Rock");
        participant = new Participant("P1", "Alice", "alice@email.com", notificationService);
    }

    /**
     * Teste l'inscription d'un participant et la réception d'une notification lors de l'annulation.
     */
    @Test
    public void testInscriptionEtNotificationAnnulation() {
        concert.ajouterParticipant(participant);
        assertTrue(concert.getParticipants().contains(participant), "Le participant doit être dans la liste");

        concert.annuler();
        List<String> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size(), "Une notification doit être envoyée");
        assertEquals("Notification pour Alice (alice@email.com): L'événement Concert Rock a été annulé.",
                notifications.get(0), "Le message de notification doit être correct");
    }

    /**
     * Teste que l'ajout d'un participant échoue lorsque la capacité est atteinte.
     */
    @Test
    public void testAjouterParticipantCapaciteMaxAtteinte() {
        // Ajouter deux participants (capacité = 2)
        Participant p2 = new Participant("P2", "Bob", "bob@email.com", notificationService);
        concert.ajouterParticipant(participant);
        concert.ajouterParticipant(p2);

        // Ajouter un troisième participant doit échouer
        Participant p3 = new Participant("P3", "Charlie", "charlie@email.com", notificationService);
        Exception exception = assertThrows(CapaciteMaxAtteinteException.class, () -> {
            concert.ajouterParticipant(p3);
        });

        assertEquals("Capacité maximale de 2 atteinte pour l'événement Concert Rock",
                exception.getMessage(), "Le message de l'exception doit être correct");
    }

    /**
     * Implémentation de NotificationService pour capturer les notifications dans les tests.
     */
    private static class TestNotificationService implements NotificationService {
        private List<String> notifications = new ArrayList<>();

        @Override
        public void envoyerNotification(String message) {
            notifications.add(message);
        }

        public List<String> getNotifications() {
            return notifications;
        }
    }
}