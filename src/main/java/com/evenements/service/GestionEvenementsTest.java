package com.evenements.service;

import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.model.Concert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe GestionEvenements.
 */
public class GestionEvenementsTest {

    private GestionEvenements gestion;
    private Concert concert;

    /**
     * Configure les objets avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 100, "The Band", "Rock");
        // Vider la map pour un test propre
        gestion.getEvenements().clear();
    }

    /**
     * Teste que l'ajout d'un événement avec un ID existant échoue.
     */
    @Test
    public void testAjouterEvenementDejaExistant() {
        // Ajouter un événement
        gestion.ajouterEvenement(concert);
        assertEquals(concert, gestion.rechercherEvenement("C1"), "L'événement doit être ajouté");

        // Ajouter un autre événement avec le même ID doit échouer
        Concert concertDuplique = new Concert("C1", "Concert Jazz", LocalDateTime.now(), "Lyon", 50, "Jazz Band", "Jazz");
        Exception exception = assertThrows(EvenementDejaExistantException.class, () -> {
            gestion.ajouterEvenement(concertDuplique);
        });

        assertEquals("Un événement avec l'ID C1 existe déjà",
                exception.getMessage(), "Le message de l'exception doit être correct");
    }
}