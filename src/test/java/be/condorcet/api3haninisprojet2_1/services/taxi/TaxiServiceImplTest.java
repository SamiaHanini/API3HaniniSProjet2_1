package be.condorcet.api3haninisprojet2_1.services.taxi;

import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.adresse.AdresseServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.client.ClientServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.location.LocationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaxiServiceImplTest {

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private TaxiServiceImpl taxiServiceImpl;

    @Autowired
    private AdresseServiceImpl adresseService;

    @Autowired
    private ClientServiceImpl clientService;

    private Location location;
    private Client client;
    private Taxi taxi;
    private Adresse adDebut;
    private Adresse adFin;

    @BeforeEach
    void setUp() throws Exception {
        taxi = new Taxi("TEST-A12", "Essence", 10.0);
        taxiServiceImpl.create(taxi);

        adDebut = new Adresse(7000, "Mons", "Rue des arbres", "1A");
        adresseService.create(adDebut);

        adFin = new Adresse(7300, "Saint-Ghislain", "Rue des rochers", "34");
        adresseService.create(adFin);

        client = new Client("clienttest@gmail.com", "TestNom", "TestPrenom", "048476378");
        clientService.create(client);

        Date date=Date.valueOf(LocalDate.now().toString());
        location = new Location(date, 30, 25.0, taxi, client, adDebut, adFin);
        locationService.create(location);
    }

    @AfterEach
    void tearDown() throws Exception {
        locationService.delete(location);
        clientService.delete(client);
        taxiServiceImpl.delete(taxi);
        adresseService.delete(adDebut);
        adresseService.delete(adFin);
    }

    @Test
    void create() throws Exception {
        Taxi newTaxi = new Taxi("CREATE-TEST", "Diesel", 15.0);
        taxiServiceImpl.create(newTaxi);

        Taxi retrievedTaxi = taxiServiceImpl.getTaxiByImmatriculation("CREATE-TEST");
        assertNotNull(retrievedTaxi, "Taxi should be created and retrieved.");
        assertEquals("CREATE-TEST", retrievedTaxi.getImmatriculation(), "Immatriculation should match.");

        taxiServiceImpl.delete(newTaxi);
    }

    @Test
    void delete() throws Exception {
        Taxi newTaxi = new Taxi("DELETE-TEST", "Electric", 20.0);
        taxiServiceImpl.create(newTaxi);

        int taxiId = newTaxi.getIdtaxi();
        taxiServiceImpl.delete(newTaxi);

        assertThrows(Exception.class, () -> taxiServiceImpl.read(taxiId), "Taxi should be deleted.");
    }

    @Test
    void read() throws Exception {
        Taxi retrievedTaxi = taxiServiceImpl.read(taxi.getIdtaxi());
        assertNotNull(retrievedTaxi, "Taxi should be retrievable by ID.");
        assertEquals(taxi.getIdtaxi(), retrievedTaxi.getIdtaxi(), "Taxi ID should match.");
    }

    @Test
    void update() throws Exception {
        taxi.setImmatriculation("T-002-EST");
        taxi.setCarburant("Diesel");
        taxi.setPrixkm(2.0);
        Taxi updatedTaxi = taxiServiceImpl.update(taxi);

        assertEquals("T-002-EST", updatedTaxi.getImmatriculation(), "Immatriculation should be updated.");
        assertEquals("Diesel", updatedTaxi.getCarburant(), "Carburant should be updated.");
        assertEquals(2.0, updatedTaxi.getPrixkm(), "Prix/km should be updated.");
    }

    @Test
    void all() throws Exception {
        List<Taxi> taxis = taxiServiceImpl.all();
        assertNotNull(taxis, "List of taxis should not be null.");
        assertFalse(taxis.isEmpty(), "List of taxis should not be empty.");
    }


    @Test
    void clientsForTaxi() throws Exception {
        List<Client> clients = taxiServiceImpl.clientsForTaxi(taxi.getIdtaxi());
        assertNotNull(clients, "List of clients should not be null.");
        assertFalse(clients.isEmpty(), "List of clients for the taxi should not be empty.");
    }

    @Test
    void readByImmatriculation() throws Exception {
        Taxi retrievedTaxi = taxiServiceImpl.getTaxiByImmatriculation(taxi.getImmatriculation());
        assertNotNull(retrievedTaxi, "Taxi should be retrievable by Immmatriculation.");
        assertEquals(taxi.getImmatriculation(), retrievedTaxi.getImmatriculation(), "Taxi immatriculation should match.");
    }

    @Test
    void locationsForTaxi() throws Exception {
        List<Location> locs = taxiServiceImpl.locationsForTaxi(taxi.getIdtaxi());
        assertNotNull(locs, "List of locations should not be null.");
        assertFalse(locs.isEmpty(), "List of locations for the taxi should not be empty.");
    }

    @Test
    void totKmTaxi() throws Exception {
        Location location1 = new Location(Date.valueOf(LocalDate.now().minusDays(1)), 10, 15.0, taxi, client, adDebut, adFin);
        Location location2 = new Location(Date.valueOf(LocalDate.now().minusDays(2)), 20, 25.0, taxi, client, adDebut, adFin);

        locationService.create(location1);
        locationService.create(location2);

        double expectedTotalKm = location.getKmtotal() + location1.getKmtotal() + location2.getKmtotal();

        Double retrievedTotalKm = taxiServiceImpl.totalKilometersForTaxi(taxi.getIdtaxi());

        assertNotNull(retrievedTotalKm, "Total kilometers should not be null.");
        assertEquals(expectedTotalKm, retrievedTotalKm, "Total kilometers should match the expected value.");

        locationService.delete(location1);
        locationService.delete(location2);
    }

    @Test
    void totalCostForTaxi() throws Exception {
        Location location1 = new Location(Date.valueOf(LocalDate.now().minusDays(1)), 10, 100.0, taxi, client, adDebut, adFin);
        Location location2 = new Location(Date.valueOf(LocalDate.now().minusDays(2)), 20, 150.0, taxi, client, adDebut, adFin);

        location1.setTotal(100.0);
        location1.setAcompte(10.0);

        location2.setTotal(150.0);
        location2.setAcompte(20.0);

        locationService.create(location1);
        locationService.create(location2);

        double expectedTotalCost = (location.getTotal() + location.getAcompte()) +
                (location1.getTotal() + location1.getAcompte()) +
                (location2.getTotal() + location2.getAcompte());

        Double retrievedTotalCost = taxiServiceImpl.totalCostForTaxi(taxi.getIdtaxi());

        assertNotNull(retrievedTotalCost, "Total cost should not be null.");
        assertEquals(expectedTotalCost, retrievedTotalCost, "Total cost should match the expected value.");

        locationService.delete(location1);
        locationService.delete(location2);
    }


}
