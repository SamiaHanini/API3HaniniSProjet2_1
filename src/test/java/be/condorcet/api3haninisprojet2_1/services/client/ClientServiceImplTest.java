package be.condorcet.api3haninisprojet2_1.services.client;

import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.adresse.AdresseServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.location.LocationServiceImpl;
import be.condorcet.api3haninisprojet2_1.services.taxi.TaxiServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientService;

    private Client client;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private TaxiServiceImpl taxiServiceImpl;

    @Autowired
    private AdresseServiceImpl adresseService;

    private Location location;

    private Taxi taxi;
    private Adresse adDebut;
    private Adresse adFin;


    @BeforeEach
    void setUp() throws Exception {
        client = new Client("testmail@test.com", "TestNom", "TestPrenom", "123");
        client = clientService.create(client);

        taxi = new Taxi("TEST-A12", "Essence", 10.0);
        taxiServiceImpl.create(taxi);

        adDebut = new Adresse(7000, "Mons", "Rue des arbres", "1A");
        adresseService.create(adDebut);

        adFin = new Adresse(7300, "Saint-Ghislain", "Rue des rochers", "34");
        adresseService.create(adFin);

        Date date=Date.valueOf(LocalDate.now().toString());
        location = new Location(date, 30, 25.0, taxi, client, adDebut, adFin);
        locationService.create(location);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (client != null && client.getIdclient() != null) {
            clientService.delete(client);
            assertThrows(Exception.class, () -> clientService.read(client.getIdclient()),
                    "Client should be deleted and not retrievable");
        }
        locationService.delete(location);
        taxiServiceImpl.delete(taxi);
        adresseService.delete(adDebut);
        adresseService.delete(adFin);
    }

    @Test
    void create() {
        assertNotNull(client.getIdclient(), "Client ID should not be null after creation");
        assertAll("Client creation verification",
                () -> assertNotEquals(0, client.getIdclient(), "Client ID should be generated"),
                () -> assertEquals("testmail@test.com", client.getMail(), "Email should match"),
                () -> assertEquals("TestNom", client.getNom(), "Name should match"),
                () -> assertEquals("TestPrenom", client.getPrenom(), "First name should match"),
                () -> assertEquals("123", client.getTel(), "Phone number should match")
        );
    }

    @Test
    void read() throws Exception {
        Client retrievedClient = clientService.read(client.getIdclient());
        assertNotNull(retrievedClient, "Client should be retrievable by ID");

        assertAll("Client retrieval verification",
                () -> assertEquals(client.getMail(), retrievedClient.getMail(), "Emails should match"),
                () -> assertEquals(client.getNom(), retrievedClient.getNom(), "Names should match"),
                () -> assertEquals(client.getPrenom(), retrievedClient.getPrenom(), "First names should match"),
                () -> assertEquals(client.getTel(), retrievedClient.getTel(), "Phone numbers should match")
        );
    }

    @Test
    void update() throws Exception {
        client.setMail("newemail@test.com");
        client.setNom("NewNom");
        client.setPrenom("NewPrenom");
        client.setTel("456");
        client = clientService.update(client);

        assertNotNull(client, "Updated client should not be null");

        assertAll("Client update verification",
                () -> assertEquals("newemail@test.com", client.getMail(), "Email should be updated"),
                () -> assertEquals("NewNom", client.getNom(), "Name should be updated"),
                () -> assertEquals("NewPrenom", client.getPrenom(), "First name should be updated"),
                () -> assertEquals("456", client.getTel(), "Phone number should be updated")
        );
    }

    @Test
    void delete() throws Exception {
        clientService.delete(client);
        assertThrows(Exception.class, () -> clientService.read(client.getIdclient()),
                "Deleted client should not be retrievable");
        client = null;
    }

    @Test
    void all() throws Exception {
        List<Client> clients = clientService.all();
        assertFalse(clients.isEmpty(), "Client list should not be empty");
        boolean containsClient = clients.stream().anyMatch(c -> c.getIdclient().equals(client.getIdclient()));

        assertTrue(containsClient, "Client list should contain the created client with matching ID");
    }


    @Test
    void readByFullnameAndPhoneNumber() throws Exception {

        Client retrievedClient = clientService.read("TestNom", "TestPrenom", "123");

        assertNotNull(retrievedClient, "Client should be retrievable by full name and phone number");

        assertAll("Client retrieval by full name and phone number verification",
                () -> assertEquals("testmail@test.com", retrievedClient.getMail(), "Emails should match"),
                () -> assertEquals("TestNom", retrievedClient.getNom(), "Names should match"),
                () -> assertEquals("TestPrenom", retrievedClient.getPrenom(), "First names should match"),
                () -> assertEquals("123", retrievedClient.getTel(), "Phone numbers should match")
        );


    }

    @Test
    void locationsForClient() throws Exception {
        List<Location> locs = clientService.locationsForClient(client.getIdclient());
        assertNotNull(locs, "List of locations should not be null.");
        assertFalse(locs.isEmpty(), "List of locations for the taxi should not be empty.");
    }


}
