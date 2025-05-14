package com.evenements;

import com.evenements.model.Concert;
import com.evenements.model.Participant;
import com.evenements.service.ConsoleNotificationService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ConsoleNotificationService service = new ConsoleNotificationService();
        Participant p1 = new Participant("P1", "Alice", "alice@email.com", service);
        Concert concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 100, "The Band", "Rock");
        concert.ajouterParticipant(p1);
        concert.annuler(); // Devrait afficher une notification
    }
}