package be.condorcet.api3haninisprojet2_1.services.location;

import org.junit.jupiter.api.AfterEach;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.adresse.AdresseServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.client.ClientServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.taxi.TaxiServiceImpl;
import be.condorcet.api3haninisprojet2_1.entities.Client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LocationServiceImplTest {

    @Autowired
    private LocationServiceImpl LocationService;

    @Autowired
    private TaxiServiceImpl TaxiService;

    @Autowired
    private AdresseServiceImpl AdresseService;

    
    @Autowired
    private ClientServiceImpl ClientService;


    private Location location;
    private Client client;
    private Taxi taxi;
    private Adresse adDebut;
    private Adresse adFin;
    private Double total;

    @BeforeEach
    void setUp() {
        try {

            adDebut = new Adresse(null, 7000,"Mons", "Rue des arbres", "1A");
            AdresseService.create(adDebut);

            adFin = new Adresse(null, 7300,"Saint-Ghislain", "Rue des rochers", "34");
            AdresseService.create(adFin);

            taxi = new Taxi(null, "T-000-EST", "ESSENCE", 10.0, new ArrayList<>());
            TaxiService.create(taxi);

            client = new Client(null, "clienttest@gmail.com", "TestNom", "TestPrenom", "048476378");
            ClientService.create(client);

            total = taxi.getPrixKm()*location.getKmtotal();

            location = new Location(null, 300, LocalDate.now(), 25.0, total, adDebut, adFin, taxi, client);
            LocationService.create(location);

            System.out.println("création de la location : " + location);
        } catch (Exception e) {
            System.out.println("erreur de création de la location : " + location + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            AdresseService.delete(adDebut);
            AdresseService.delete(adFin);
            TaxiService.delete(taxi);
            ClientService.delete(client);
            LocationService.delete(location);

            System.out.println("effacement de la location : " + location);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de la location :" + location + " erreur : " + e);
        }
    }

    @Test
    void create() {
        assertNotEquals(0, location.getId(), "id Location non incrémenté");
        assertEquals(LocalDate.now(), location.getDateLoc(), "date Location non enregistré : " + location.getDateLoc() + " au lieu de " + LocalDate.now());
    }

     @Test
    void read() {
        try {
            int idLoc = location.getId();
            Location location2 = LocationService.read(idLoc);
            assertEquals(LocalDate.now(), location2.getDateLoc(), "Date de la location différente " + location2.getDateLoc() + " au lieu de " + LocalDate.now());

        } catch (Exception e) {
            fail("recherche infructueuse " + e);
        }
    }

    @Test
    void update() {
        try {
            location.setDateLoc(LocalDate.now().plusDays(1));

            location = LocationService.update(location);

            LocalDate nowPlusOne = LocalDate.now().plusDays(1);

            assertEquals(nowPlusOne, location.getDateLoc(), "date Location différent : " + location.getDateLoc() + " au lieu de " + nowPlusOne);
        } catch (Exception e) {
            fail("erreur de mise à jour " + e);
        }
    }

    @Test
    void delete() {
        try {
            LocationService.delete(location);

            Assertions.assertThrows(Exception.class, () -> {
                LocationService.read(location.getId());
            }, "record non effacé");
        } catch (Exception e) {
            fail("erreur d'effacement " + e);
        }
    }


    @Test
    void all() {
        try {
            List<Location> locations = LocationService.all();

            assertNotEquals(0, locations.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de toutes les locations " + e);
        }
    }

    @Test
    void readByDates() {
        try {
            LocalDate start = LocalDate.now();
            LocalDate end = start.plusDays(1);

            List<Location> locations = LocationService.getLocationsByDates(start, end);

            assertNotEquals(0, locations.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de Locations par date " + e);
        }
    }

    @Test
    void getLocationsByTaxis() {
        try {
            List<Location> locations = LocationService.getLocationsByTaxi(taxi);
            assertNotEquals(0, locations.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche des Locations du patient " + e);
        }
    }





}
