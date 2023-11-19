package be.condorcet.api3haninisprojet2_1.webservices;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.taxi.InterfTaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/taxis")
public class RestTaxi {
    @Autowired
    private InterfTaxiService TaxiServiceImpl;

    //-------------------Retrouver le taxi correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Taxi> getTaxi(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche de le taxi avec l'id " + id);
        Taxi taxi = TaxiServiceImpl.read(id);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }

    //-------------------Retrouver les taxis avec un carburant donnée--------------------------------------------------------
    @RequestMapping(value = "/carburant={carburant}", method = RequestMethod.GET)
    public ResponseEntity<List<Taxi>> listTaxisCarburant(@PathVariable(value = "carburant") String carburant) throws Exception {
        System.out.println("recherche de la carburant " + carburant);
        List<Taxi> taxis;
        taxis = TaxiServiceImpl.read(carburant);
        return new ResponseEntity<>(taxis, HttpStatus.OK);
    }

    //-------------------Créer un taxi--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Taxi> createTaxi(@RequestBody Taxi taxi) throws Exception {
        System.out.println("Création de le taxi " + taxi);
        TaxiServiceImpl.create(taxi);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }

    //-------------------Mettre à jour un taxi avec un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Taxi> majTaxi(@PathVariable(value = "id") int id, @RequestBody Taxi nouvTaxi) throws Exception {
        System.out.println("maj de taxi id =  " + id);
        nouvTaxi.setId(id);
        Taxi taxi = TaxiServiceImpl.update(nouvTaxi);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }

    //-------------------Effacer un taxi avec un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTaxi(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("effacement du taxi d'id " + id);
        Taxi taxi = TaxiServiceImpl.read(id);
        TaxiServiceImpl.delete(taxi);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver tous les taxis --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Taxi>> listTaxi() throws Exception {
        System.out.println("recherche de tous les taxis");
        return new ResponseEntity<>(TaxiServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}

