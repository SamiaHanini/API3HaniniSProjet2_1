package be.condorcet.api3haninisprojet2_1.services.location;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.adresse.AdresseServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.client.ClientServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.taxi.TaxiServiceImpl;
import be.condorcet.api3haninisprojet2_1.entities.Client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
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
    //private Double total;

    @BeforeEach
    void setUp() {
        try {

            adDebut = new Adresse(7000,"Mons", "Rue des arbres", "1A");
            AdresseService.create(adDebut);

            adFin = new Adresse(7300,"Saint-Ghislain", "Rue des rochers", "34");
            AdresseService.create(adFin);

            taxi = new Taxi("T-000-EST", "ESSENCE", 10.0);
            TaxiService.create(taxi);

            client = new Client("clienttest@gmail.com", "TestNom", "TestPrenom", "048476378");
            ClientService.create(client);

            location = new Location(LocalDate.now(),30,  25.0, null, adDebut, adFin, taxi, client);
            LocationService.create(location);

            System.out.println("création de la location : " + location);
        } catch (Exception e) {
            System.out.println("erreur de création de la location : " + location + " erreur : " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            try {
                LocationService.delete(location);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression de la location : " + e);
            }

            try {
                ClientService.delete(client);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression du client : " + e);
            }
            try {
                TaxiService.delete(taxi);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression du taxi : " + e);
            }

            try {
                AdresseService.delete(adDebut);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression de l'adresse de départ : " + e);
            }
            try {
                AdresseService.delete(adFin);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression de l'adresse de départ : " + e);
            }
            System.out.println("effacement de la location : " + location);
        } catch (Exception e) {
            System.out.println("erreur d'effacement de la location :" + location + " erreur : " + e);
        }
    }

    //@Test
    // void create() {
    //     assertNotEquals(0, location.getId(), "id Location non incrémenté");
    //     assertEquals(LocalDate.now(), location.getDateLoc(), "date Location non enregistré : " + location.getDateLoc() + " au lieu de " + LocalDate.now());
    //     assertNotEquals(300,location.getKmtotal(), "km total non enregistré");
    //     assertNotEquals(25,location.getAcompte(), "acompte non enregistré");
    //     assertNotEquals(0,location.getTotal(), "total non enregistré");
    //     assertNotEquals(adDebut.getId(),location.getAdresseDebut().getId(), "location adresse départ non enregistrée");
    //     assertNotEquals(adFin.getId(),location.getAdresseFin().getId(), "location adresse arrivé non enregistrée");
    //     assertNotEquals(taxi.getId(),location.getTaxi().getId(), "location taxi non enregistrée");
    //     assertNotEquals(client.getId(),location.getClient().getId(), "location client non enregistrée");

    // }

    // @Test
    // void create() {
    //     assertNotEquals(0, location.getId(), "id location non incrémenté");
    //     assertEquals(LocalDate.now(), location.getDateLoc(), "date prescription non enregistré : " + location.getDateLoc() + " au lieu de " + LocalDate.now());
    // }

    @Test
    void create() {
        assertNotEquals(0, location.getId(), "location id not incremented");
        assertNotEquals(null,location.getDateLoc(), "location date not set");
        assertNotEquals(0,location.getKmtotal(), "location km not set");
        assertNotEquals(0,location.getTotal(), "location total not set");
        assertNotEquals(null,location.getTaxi(), "location taxi not set");
        assertNotEquals(null,location.getClient(), "location client not set");
        assertNotEquals(null,location.getAdresseDebut(), "location adressedep not set");
        assertNotEquals(null,location.getAdresseFin(), "location adressearr not set");
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
        try{
            location.setKmtotal(20);
            location = LocationService.update(location);
            location = LocationService.read(location.getId());
            assertEquals(20,location.getKmtotal(), "pas de différence dans le km total");
        }catch (Exception e){
            fail("erreur de mise à jour : " + e);
        }
    }

    @Test
    void delete() {
        try{
            LocationService.delete(location);
            Assertions.assertThrows(Exception.class, () -> {
                LocationService.read(location.getId());
            },"error : location not deleted");
        }catch (Exception e){
            fail("delete failed : " + e);
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

            LocalDate start = LocalDate.of(2023, 1, 12);
            LocalDate end = LocalDate.of(2023, 2, 19);
            Taxi taxiLocs = TaxiService.read(2);
            List<Location> locations = LocationService.getLocationsByDatesAndTaxi(taxiLocs,start, end);

            assertNotEquals(0, locations.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche de Locations par date " + e);
        }
    }

    @Test
    void getLocationsByTaxis() {
        try {
            Taxi taxiLocs = TaxiService.read(2);
            List<Location> locations = LocationService.getLocationsByTaxi(taxiLocs);
            assertNotEquals(0, locations.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("erreur de recherche des Locations du patient " + e);
        }
    }


}
