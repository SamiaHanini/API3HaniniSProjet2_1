package be.condorcet.api3haninisprojet2_1.services.adresse;

import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

@SpringBootTest
class AdresseServiceImplTest {

    @Autowired
    private InterfAdresseService adresseServiceImpl;

    Adresse adresse;

    @BeforeEach
    void setUp() {
        try {
            adresse = new Adresse(null, 8000, "Bruxelles", "Avenue de la Liberté", "12A");
            adresseServiceImpl.create(adresse);

            System.out.println("Création de l'adresse : " + adresse);
        } catch (Exception e) {
            System.out.println("Erreur lors de la création de l'adresse : " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            adresseServiceImpl.delete(adresse);

            System.out.println("Suppression de l'adresse : " + adresse);
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression de l'adresse : " + e.getMessage());
        }
    }

    @Test
    void create() {
        assertNotEquals(0, adresse.getId(), "L'ID de l'adresse n'a pas été incrémenté");
        assertEquals(7390, adresse.getCp(), "Le code postal enregistré est incorrect : " + adresse.getCp() + " au lieu de 7000");
        assertEquals("Quaregnon", adresse.getLocalite(), "La ville enregistrée est incorrecte : " + adresse.getLocalite() + " au lieu de Mons");
        assertEquals("Rue des pommes", adresse.getRue(), "La rue enregistrée est incorrecte : " + adresse.getRue() + " au lieu de Rue de la chaussée");
        assertEquals("23", adresse.getNum(), "Le numéro enregistré est incorrect : " + adresse.getNum() + " au lieu de 1");
    }
    

    @Test
    void read() {
        try {
            int idadresse = adresse.getId();
            Adresse adresse2 = (Adresse) adresseServiceImpl.read(idadresse);
    
            assertEquals(adresse.getCp(), adresse2.getCp(), "Le code postal ne correspond pas : " + adresse.getCp() + " au lieu de " + adresse2.getCp());
            assertEquals(adresse.getLocalite(), adresse2.getLocalite(), "La localité est différente : " + adresse.getLocalite() + " au lieu de " + adresse2.getLocalite());
            assertEquals(adresse.getRue(), adresse2.getRue(), "La rue est différente : " + adresse.getRue() + " au lieu de " + adresse2.getRue());
            assertEquals(adresse.getNum(), adresse2.getNum(), "Le numéro est différent : " + adresse.getNum() + " au lieu de " + adresse2.getNum());
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture de l'adresse : " + adresse + " Erreur : " + e);
        }
    }
    

    @Test
    void rechCodePostal() {
        try {
            List<Adresse> adresses = adresseServiceImpl.read(7000); 
            boolean trouve = false;
            for (Adresse adresse : adresses) {
                if (adresse.getCp() == 7000) { 
                    trouve = true;
                } else {
                    fail("L'adresse actuelle n'a pas le code postal spécifié.");
                }
            }
            assertTrue(trouve, "Aucune adresse trouvée dans la liste pour le code postal spécifié.");
        } catch (Exception e) {
            // En cas d'erreur, affichons un message explicatif
            System.out.println("Erreur lors de la recherche d'adresses par code postal : " + e.getMessage());
        }
    }
    

    @Test
    void all() {
        try {
            List<Adresse> adresses = adresseServiceImpl.all();
            assertNotEquals(0, adresses.size(), "Aucune adresse trouvée");
        } catch (Exception e) {
            System.out.println("Erreur de création de l'adresse : " + adresse + " erreur : " + e);
        }
    }

    @Test
    void update() {
        try {
            adresse.setCp(1000);
            adresse.setLocalite("Bruxelles-Ville");
            adresse.setRue("Rue de la Loi");
            adresse.setNum("1");
            adresse = adresseServiceImpl.update(adresse);

            assertEquals(1000, adresse.getCp(), "Code postal différent " + adresse.getCp() + " au lieu de 1000");
            assertEquals("Bruxelles-Ville", adresse.getLocalite(), "Localité différente " + adresse.getLocalite() + " au lieu de Bruxelles-Ville");
            assertEquals("Rue de la Loi", adresse.getRue(), "Rue différente " + adresse.getRue() + " au lieu de Rue de la Loi");
            assertEquals("1", adresse.getNum(), "Numéro différent " + adresse.getNum() + " au lieu de 1");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour de l'adresse : " + e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            adresseServiceImpl.delete(adresse);
            Assertions.assertThrows(Exception.class, () -> {
                adresseServiceImpl.read(adresse.getId());
            }, "Record non effacé");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression de l'adresse : " + e.getMessage());
        }
    }
}
