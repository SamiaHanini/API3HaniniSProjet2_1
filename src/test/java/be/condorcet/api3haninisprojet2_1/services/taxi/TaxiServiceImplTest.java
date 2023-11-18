package be.condorcet.api3haninisprojet2_1.services.taxi;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.taxi.TaxiServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
class TaxiServiceImplTest {

    @Autowired
    private InterfTaxiService taxiServiceImpl;

    private Taxi taxi;

    @BeforeEach
    void setUp() {
        try {
            taxi = new Taxi("T-000-EST", "Essence", 1.5);
            taxiServiceImpl.create(taxi);
            taxi = taxiServiceImpl.read(taxi.getId());
            System.out.println("Création du taxi réussie : " + taxi);

        } catch (Exception e) {
            System.out.println("Échec de la création du taxi : " + taxi + ". Erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            taxiServiceImpl.delete(taxi);
            System.out.println("Suppression du taxi réussie : " + taxi);

        } catch (Exception e) {
            System.out.println("Erreur d'effacement du taxi : " + taxi + ". Erreur : " + e);
        }
    }


    @Test
    void create() {
        assertNotEquals(0, taxi.getId(), "Identifiant du taxi non incrémenté");
        assertEquals("T-000-EST", taxi.getImmatriculation(), "Immatriculation non enregistrée : " + taxi.getImmatriculation() + " au lieu de T-000-EST");
        assertEquals("Essence", taxi.getCarburant(), "Carburant non enregistré : " + taxi.getCarburant() + " au lieu de Essence");
        assertEquals(1.5, taxi.getPrixkm(), "Prix au km non enregistré : " + taxi.getPrixkm() + " au lieu de 1.5");
    }

    @Test
    void read() {
        try {
            int idTaxi = taxi.getId();
            Taxi taxi2 = taxiServiceImpl.read(idTaxi);

            assertEquals(taxi.getImmatriculation(), taxi2.getImmatriculation(), "Immatriculation différente " + taxi.getImmatriculation() + " au lieu de " + taxi2.getImmatriculation());
            assertEquals(taxi.getCarburant(), taxi2.getCarburant(), "Carburant différent " + taxi.getCarburant() + " au lieu de " + taxi2.getCarburant());
            assertEquals(taxi.getPrixkm(), taxi2.getPrixkm(), "Prix au km différent " + taxi.getPrixkm() + " au lieu de " + taxi2.getPrixkm());
        } catch (Exception e) {
            System.out.println("Erreur de création du taxi : " + taxi + " erreur : " + e);
        }
    }

    @Test
    void update() {
        try {
            taxi.setImmatriculation("T-002-EST");
            taxi.setCarburant("Diesel");
            taxi.setPrixkm(2.0);
            taxi = taxiServiceImpl.update(taxi);
            assertEquals("T-002-EST", taxi.getImmatriculation(), "Immatriculation différente de \"T-002-EST\"");
            assertEquals("Diesel", taxi.getCarburant(), "Carburant différent de Diesel");
            assertEquals(2.0, taxi.getPrixkm(), "Prix au km différent de 2.0");
        } catch (Exception e) {
            System.out.println("Erreur de mise à jour du taxi : " + taxi + " erreur : " + e);
        }
    }

    @Test
    void delete() {
        try {
            taxiServiceImpl.delete(taxi);
            Assertions.assertThrows(Exception.class, () -> {
                taxiServiceImpl.read(taxi.getId());
            }, "Record non effacé");
        } catch (Exception e) {
            System.out.println("Erreur d'effacement du taxi : " + taxi + " erreur : " + e);
        }
    }

    @Test
    void all() {
        try {
            List<Taxi> taxis = taxiServiceImpl.all();
            assertNotEquals(0, taxis.size(), "Nombre de taxis non conforme");
        } catch (Exception e) {
            System.out.println("Erreur de recherche de tous les taxis : " + e);
        }
    }

    @Test
    void rechCarburant() {
        try {
            List<Taxi> taxis = taxiServiceImpl.read("Essence");
            boolean trouve = false;
            for (Taxi taxi : taxis) {
                if (taxi.getCarburant().startsWith("Essence")) {
                    trouve = true;
                } else {
                    fail("Un record ne correspond pas, carburant = " + taxi.getCarburant());
                }
            }
            assertNotEquals(0, taxis.size(), "Enregistrement non retrouvé dans la liste");
        } catch (Exception e) {
            fail("Recherche infructueuse : " + e);
        }
    }


    @Test
    void clientsForTaxi() {
        try {
            List<Client> clients = taxiServiceImpl.clientsForTaxi(1);
            clients.forEach(System.out::println);

            assertNotEquals(0, clients.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de taxis par client " + e);
        }
    }

}
