package com.evenements;

import com.evenements.model.Concert;
import com.evenements.model.Participant;
import com.evenements.service.ConsoleNotificationService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ConsoleNotificationService service = new ConsoleNotificationService();
        Participant p1 = new Participant("P1", "Alice", "alice@email.com", service);
        Participant p2 = new Participant("P2", "Bob", "bob@email.com", service);
        Concert concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 1, "Sia", "Pop");
        concert.ajouterParticipant(p1);
        concert.ajouterParticipant(p2);
        concert.annuler(); // Devrait afficher une notification
    }
}