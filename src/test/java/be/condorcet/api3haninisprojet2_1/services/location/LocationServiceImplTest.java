package be.condorcet.api3haninisprojet2_1.services.location;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.adresse.AdresseServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.client.ClientServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.taxi.TaxiServiceImpl;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LocationServiceImplTest {

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private TaxiServiceImpl taxiService;

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
        adDebut = new Adresse(7000, "Mons", "Rue des arbres", "1A");
        adFin = new Adresse(7300, "Saint-Ghislain", "Rue des rochers", "34");
        adresseService.create(adDebut);
        adresseService.create(adFin);

        taxi = new Taxi("T-000-EST", "ESSENCE", 10.0);
        taxiService.create(taxi);

        client = new Client("clienttest@gmail.com", "TestNom", "TestPrenom", "048476378");
        clientService.create(client);

        Date date=Date.valueOf(LocalDate.now().toString());
        location = new Location(date, 30, 25.0, taxi, client, adDebut, adFin);
        locationService.create(location);
    }

    @AfterEach
    void tearDown() throws Exception {
        locationService.delete(location);
        clientService.delete(location.getClientfk());
        taxiService.delete(location.getTaxifk());
        adresseService.delete(location.getAdressedepart());
        adresseService.delete(location.getAdressefin());
    }

    @Test
    void create() {
        Date date=Date.valueOf(LocalDate.now().toString());
        assertNotEquals(0, location.getIdlocation(), "Location ID not incremented");
        assertEquals(date, location.getDateloc(), "Location date not set correctly");
        assertEquals(location.getTaxifk().getIdtaxi(), taxi.getIdtaxi(), "Taxi not set correctly");
        assertEquals(location.getClientfk().getIdclient(), client.getIdclient(), "Client not set correctly");
    }

    @Test
    void read() throws Exception {
        Location fetchedLocation = locationService.read(location.getIdlocation());
        assertEquals(location.getIdlocation(), fetchedLocation.getIdlocation(), "Fetched location ID does not match");
    }

    @Test
    void update() throws Exception {
        location.setKmtotal(50);
        Location updatedLocation = locationService.update(location);

        assertEquals(50, updatedLocation.getKmtotal(), "Km total not updated correctly");
    }

    @Test
    void delete() throws Exception {
        locationService.delete(location);

        assertThrows(Exception.class, () -> locationService.read(location.getIdlocation()), "Location not deleted");
    }

    @Test
    void all() throws Exception {
        List<Location> locations = locationService.all();
        assertNotEquals(0, locations.size(), "No locations found in the database");
    }

    @Test
    void readByDatesAndTaxi() throws Exception {

        Date start = Date.valueOf(location.getDateloc().toLocalDate().minusDays(10));
        Date end = Date.valueOf(location.getDateloc().toLocalDate().plusDays(10));

        List<Location> locations = locationService.getLocationsByTaxiIdAndDateRange(taxi.getIdtaxi(), start, end);
        assertNotEquals(0, locations.size(), "No locations found between the given dates for the specified taxi");
    }

    @Test
    void readByDate() throws Exception {

        Date datel = Date.valueOf(location.getDateloc().toLocalDate());
        List<Location> locations = locationService.readByDate(datel);
        assertNotEquals(0, locations.size(), "No locations found between the given date");
    }

    @Test
    void readByTaxi() throws Exception {

        List<Location> locations = locationService.read(taxi);
        assertNotEquals(0, locations.size(), "No locations found for the given taxi");
    }

    @Test
    void readByClient() throws Exception {

        List<Location> locations = locationService.read(client);
        assertNotEquals(0, locations.size(), "No locations found for the given client");
    }


}
