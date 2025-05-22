package com.evenements.service;

import com.evenements.exception.EvenementDejaExistantException;
import com.evenements.model.Concert;
import com.evenements.model.Evenement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe GestionEvenements.
 */
public class GestionEvenementsTest {

    private GestionEvenements gestion;
    private Concert concert;

    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        concert = new Concert("C1", "Concert Rock", LocalDateTime.now(), "Paris", 100, "The Band", "Rock");
        gestion.getEvenements().clear();
    }

    @Test
    public void testAjouterEvenement() {
        gestion.ajouterEvenement(concert);
        assertEquals(concert, gestion.rechercherEvenement("C1"));
    }

    @Test
    public void testAjouterEvenementDejaExistant() {
        gestion.ajouterEvenement(concert);
        Concert concertDuplique = new Concert("C1", "Concert Jazz", LocalDateTime.now(), "Lyon", 50, "Jazz Band", "Jazz");
        Exception exception = assertThrows(EvenementDejaExistantException.class, () -> {
            gestion.ajouterEvenement(concertDuplique);
        });
        assertEquals("Un événement avec l'ID C1 existe déjà", exception.getMessage());
    }

    @Test
    public void testSupprimerEvenement() {
        gestion.ajouterEvenement(concert);
        gestion.supprimerEvenement("C1");
        assertNull(gestion.rechercherEvenement("C1"));
    }

    @Test
    public void testSerialisationDeserialisation() throws IOException {
        gestion.ajouterEvenement(concert);
        String fichier = "evenements_test.json";
        gestion.sauvegarderEvenements(fichier);

        GestionEvenements nouvelleGestion = GestionEvenements.getInstance();
        nouvelleGestion.getEvenements().clear();
        nouvelleGestion.chargerEvenements(fichier);

        Evenement loaded = nouvelleGestion.rechercherEvenement("C1");
        assertNotNull(loaded);
        assertEquals("Concert Rock", loaded.getNom());
        assertEquals("Paris", loaded.getLieu());
        assertTrue(loaded instanceof Concert);
        assertEquals("The Band", ((Concert) loaded).getArtiste());

        new File(fichier).delete();
    }
}