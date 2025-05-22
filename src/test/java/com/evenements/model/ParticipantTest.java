package com.evenements.model;

import com.evenements.service.TestNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Participant.
 */
public class ParticipantTest {

    private Participant participant;
    private TestNotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new TestNotificationService();
        participant = new Participant("P1", "Alice", "alice@email.com", notificationService);
    }

    @Test
    public void testRecevoirNotificationAnnulation() throws Exception {
        participant.recevoirNotification("Événement annulé");
        CompletableFuture<Void> future = notificationService.getLastFuture();
        future.get(1, TimeUnit.SECONDS);
        List<String> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size(), "Une notification doit être reçue");
        assertEquals("Notification pour Alice (alice@email.com): Événement annulé", notifications.get(0));
    }

    @Test
    public void testRecevoirNotificationReport() throws Exception {
        participant.recevoirNotification("Événement reporté");
        CompletableFuture<Void> future = notificationService.getLastFuture();
        future.get(1, TimeUnit.SECONDS);
        List<String> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size(), "Une notification doit être reçue");
        assertEquals("Notification pour Alice (alice@email.com): Événement reporté", notifications.get(0));
    }

    @Test
    public void testGetters() {
        assertEquals("P1", participant.getId(), "L'ID doit être correct");
        assertEquals("Alice", participant.getNom(), "Le nom doit être correct");
        assertEquals("alice@email.com", participant.getEmail(), "L'email doit être correct");
    }
}