package be.condorcet.api3haninisprojet2_1.webservices;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.taxi.InterfTaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "Content-Type", exposedHeaders = "*")
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
    @RequestMapping(value="/immatriculation/{immatriculation}",method = RequestMethod.GET)
    public ResponseEntity<Taxi> getTaxiByImmatriculation(@PathVariable(value = "immatriculation") String immatriculation) throws Exception {
        Taxi taxi=TaxiServiceImpl.getTaxiByImmatriculation(immatriculation);
        return new ResponseEntity<>(taxi, HttpStatus.OK);
    }


    //-------------------Clients pour un taxi--------------------------------------------------------
    @RequestMapping(value="/identifiant/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Client>> clientsForTaxi(@PathVariable(value = "id") int id) throws Exception {
        List<Client> clients =TaxiServiceImpl.clientsForTaxi(id);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    //-------------------Location pour un taxi--------------------------------------------------------
    @RequestMapping(value="/identifiantloc/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Location>> locationsForTaxi(@PathVariable(value = "id") int id) throws Exception {
        List<Location> locations =TaxiServiceImpl.locationsForTaxi(id);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    //-------------------Nb total km pour un taxi--------------------------------------------------------
    @RequestMapping(value="/identifiantkmtot",method = RequestMethod.POST)
    public ResponseEntity<List<Double>> totalKilometersForTaxi(@RequestBody List<Integer> idtaxis) throws Exception {
        List<Double> kmtots = new ArrayList<>();
        for(int idtaxi:idtaxis){
            Double totalkm =TaxiServiceImpl.totalKilometersForTaxi(idtaxi);
            kmtots.add(totalkm);
        }

        return new ResponseEntity<>(kmtots, HttpStatus.OK);
    }

    //-------------------Nb total km pour un taxi--------------------------------------------------------
    @RequestMapping(value="/identifiantmontant",method = RequestMethod.POST)
    public ResponseEntity<List<Double>> totalCostForTaxi(@RequestBody List<Integer> idtaxis) throws Exception {
        List<Double> monttots = new ArrayList<>();
        for(int idtaxi:idtaxis){
            Double monttot=TaxiServiceImpl.totalCostForTaxi(idtaxi);
            monttots.add(monttot);
        }
        return new ResponseEntity<>(monttots, HttpStatus.OK);
    }

    //-------------------Créer un taxi--------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Taxi> createTaxi(@RequestBody Taxi taxi){
        try {
            TaxiServiceImpl.create(taxi);
            return new ResponseEntity<>(taxi, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //-------------------Mettre à jour un taxi avec un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Taxi> majTaxi(@PathVariable(value = "id") int id, @RequestBody Taxi nouvTaxi) throws Exception {
        System.out.println("maj de taxi id =  " + id);
        nouvTaxi.setIdtaxi(id);
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

