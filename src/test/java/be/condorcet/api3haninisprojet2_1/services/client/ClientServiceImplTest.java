package be.condorcet.api3haninisprojet2_1.services.client;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        try {
            client = new Client(null, "testmail@test.com", "TestNom", "TestPrenom", "123");
            clientService.create(client);
            System.out.println("Création du client : " + client);
        } catch (Exception e) {
            System.out.println("Erreur lors de la création du client : " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            clientService.delete(client);
            System.out.println("Suppression du client : " + client);
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

    @Test
    void create() {
        assertNotEquals(0, client.getId(), "L'ID du client n'a pas été incrémenté");
        assertEquals("testmail@test.com", client.getMail(), "L'adresse email est incorrecte : " + client.getMail() + " au lieu de testmail@test.com");
        assertEquals("TestNom", client.getNom(), "Le nom est incorrect : " + client.getNom() + " au lieu de TestNom");
        assertEquals("TestPrenom", client.getPrenom(), "Le prénom est incorrect : " + client.getPrenom() + " au lieu de TestPrenom");
        assertEquals("123", client.getTel(), "Le numéro de téléphone est incorrect : " + client.getTel() + " au lieu de 123");
    }

    @Test
    void read() {
        try {
            int idClient = client.getId();
            Client client2 = clientService.read(idClient);

            assertEquals(client.getMail(), client2.getMail(), "L'adresse email est différente : " + client.getMail() + " au lieu de " + client2.getMail());
            assertEquals(client.getNom(), client2.getNom(), "Le nom est différent : " + client.getNom() + " au lieu de " + client2.getNom());
            assertEquals(client.getPrenom(), client2.getPrenom(), "Le prénom est différent : " + client.getPrenom() + " au lieu de " + client2.getPrenom());
            assertEquals(client.getTel(), client2.getTel(), "Le numéro de téléphone est différent : " + client.getTel() + " au lieu de " + client2.getTel());
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du client : " + client + " Erreur : " + e.getMessage());
        }
    }

    @Test
    void update() {
        try {
            client.setMail("newemail@test.com");
            client.setNom("NewNom");
            client.setPrenom("NewPrenom");
            client.setTel("456");
            client = clientService.update(client);

            assertEquals("newemail@test.com", client.getMail(), "L'adresse email a été mise à jour");
            assertEquals("NewNom", client.getNom(), "Le nom a été mis à jour");
            assertEquals("NewPrenom", client.getPrenom(), "Le prénom a été mis à jour");
            assertEquals("456", client.getTel(), "Le numéro de téléphone a été mis à jour");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour du client : " + e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            clientService.delete(client);
            assertThrows(Exception.class, () -> {
                clientService.read(client.getId());
            }, "Enregistrement non supprimé");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

    @Test
    void all() {
        try {
            List<Client> clients = clientService.all();
            assertTrue(clients.size() > 0, "Aucun client trouvé");
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la liste des clients : " + e.getMessage());
        }
    }
}
