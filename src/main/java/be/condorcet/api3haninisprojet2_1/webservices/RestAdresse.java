package be.condorcet.api3haninisprojet2_1.webservices;


import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.services.adresse.InterfAdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/adresses")
public class RestAdresse {
    @Autowired
    private InterfAdresseService AdresseServiceImpl;


    //-------------------Retrouver l'adresse correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Adresse> getAdresse(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche de l'adresse avec l'id " + id);
        Adresse adresse = AdresseServiceImpl.read(id);
        return new ResponseEntity<>(adresse, HttpStatus.OK);
    }

    //-------------------Retrouver les adresses portant une localité donnée--------------------------------------------------------
    @RequestMapping(value = "/{localite}", method = RequestMethod.GET)
    public ResponseEntity<List<Adresse>> listAdressesLocalite(@PathVariable(value = "localite") String localite) throws Exception {
        System.out.println("recherche de la localite " + localite);
        List<Adresse> adresses;
        adresses = AdresseServiceImpl.read(localite);
        return new ResponseEntity<>(adresses, HttpStatus.OK);
    }

    //-------------------Créer un adresse--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Adresse> createAdresse(@RequestBody Adresse adresse) throws Exception {
        System.out.println("Création de l'adresse " + adresse);
        AdresseServiceImpl.create(adresse);
        return new ResponseEntity<>(adresse, HttpStatus.OK);
    }

    //-------------------Mettre à jour une adresse avec un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Adresse> majAdresse(@PathVariable(value = "id") int id, @RequestBody Adresse nouvAdresse) throws Exception {
        System.out.println("maj de adresse id =  " + id);
        nouvAdresse.setId(id);
        Adresse adresse = AdresseServiceImpl.update(nouvAdresse);
        return new ResponseEntity<>(adresse, HttpStatus.OK);
    }

    //-------------------Effacer une adresse avec un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAdresse(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("effacement du adresse d'id " + id);
        Adresse adresse = AdresseServiceImpl.read(id);
        AdresseServiceImpl.delete(adresse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver tous les adresses --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Adresse>> listAdresse() throws Exception {
        System.out.println("recherche de tous les adresses");
        return new ResponseEntity<>(AdresseServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}
