package be.condorcet.api3haninisprojet2_1.services.adresse;

import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdresseServiceImplTest {

    @Autowired
    private InterfAdresseService adresseServiceImpl;

    private Adresse adresse;

    @BeforeEach
    void setUp() throws Exception {
        adresse = new Adresse(1200, "Baudour", "Rue des parapluies", "5");
        adresse = adresseServiceImpl.create(adresse);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (adresse != null && adresse.getIdadresse() != null) {
            adresseServiceImpl.delete(adresse);
            assertThrows(Exception.class, () -> adresseServiceImpl.read(adresse.getIdadresse()),
                    "L'adresse ne devrait plus être accessible après suppression");
        }
    }

    @Test
    void create() {
        assertNotNull(adresse.getIdadresse(), "L'ID de l'adresse devrait être généré après la création");
        assertAll("Vérification de la création de l'adresse",
                () -> assertNotEquals(0, adresse.getIdadresse(), "L'ID de l'adresse ne devrait pas être zéro"),
                () -> assertEquals(1200, adresse.getCp(), "Le code postal est incorrect"),
                () -> assertEquals("Baudour", adresse.getLocalite(), "La localité est incorrecte"),
                () -> assertEquals("Rue des parapluies", adresse.getRue(), "La rue est incorrecte"),
                () -> assertEquals("5", adresse.getNum(), "Le numéro est incorrect")
        );
    }

    @Test
    void read() throws Exception {
        Adresse adresse2 = adresseServiceImpl.read(adresse.getIdadresse());
        assertNotNull(adresse2, "L'adresse lue ne devrait pas être null");

        assertAll("Vérification des données de l'adresse lue",
                () -> assertEquals(adresse.getCp(), adresse2.getCp(), "Le code postal est incorrect"),
                () -> assertEquals(adresse.getLocalite(), adresse2.getLocalite(), "La localité est incorrecte"),
                () -> assertEquals(adresse.getRue(), adresse2.getRue(), "La rue est incorrecte"),
                () -> assertEquals(adresse.getNum(), adresse2.getNum(), "Le numéro est incorrect")
        );
    }

    @Test
    void rechLocalite() throws Exception {
        List<Adresse> adresses = adresseServiceImpl.read("Baudour");
        assertTrue(adresses.stream().anyMatch(a -> "Baudour".equals(a.getLocalite())),
                "Aucune adresse trouvée pour la localité spécifiée");
    }

    @Test
    void all() throws Exception {
        List<Adresse> adresses = adresseServiceImpl.all();
        assertFalse(adresses.isEmpty(), "Aucune adresse trouvée");
    }

    @Test
    void update() throws Exception {
        adresse.setCp(1000);
        adresse.setLocalite("Bruxelles-Ville");
        adresse.setRue("Rue de la Loi");
        adresse.setNum("1");
        Adresse updatedAdresse = adresseServiceImpl.update(adresse);

        assertAll("Vérification de la mise à jour de l'adresse",
                () -> assertEquals(1000, updatedAdresse.getCp(), "Le code postal après mise à jour est incorrect"),
                () -> assertEquals("Bruxelles-Ville", updatedAdresse.getLocalite(), "La localité après mise à jour est incorrecte"),
                () -> assertEquals("Rue de la Loi", updatedAdresse.getRue(), "La rue après mise à jour est incorrecte"),
                () -> assertEquals("1", updatedAdresse.getNum(), "Le numéro après mise à jour est incorrect")
        );
    }

    @Test
    void delete() throws Exception {
        adresseServiceImpl.delete(adresse);
        assertThrows(Exception.class, () -> adresseServiceImpl.read(adresse.getIdadresse()),
                "L'adresse devrait être supprimée et inaccessible");
    }
}
