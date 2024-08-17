package be.condorcet.api3haninisprojet2_1.webservices;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.services.taxi.InterfTaxiService;
import be.condorcet.api3haninisprojet2_1.services.client.InterfClientService;
import be.condorcet.api3haninisprojet2_1.services.location.InterfLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.sql.Date;


@CrossOrigin(origins = "*", allowedHeaders = "*",exposedHeaders = "*")
@RestController
@RequestMapping("/locations")
public class RestLocation {

    @Autowired
    private InterfLocationService locationServiceImpl;
    @Autowired
    private InterfTaxiService taxiServiceImpl;
     @Autowired
    private InterfClientService clientServiceImpl;

    //-------------------Retrouver la location correspondant à un n° donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Location> getLocation(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche de la location n° " + id);
        Location loc = locationServiceImpl.read(id);
        return new ResponseEntity<>(loc, HttpStatus.OK);
    }

    //-------------------Retrouver la location correspondant à un taxi donné--------------------------------------------------------
    @RequestMapping(value = "/idtaxi={id}", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocationTaxi(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche des locations du taxi d'id " + id);
        Taxi taxi = taxiServiceImpl.read(id);
        List<Location> lloc = locationServiceImpl.read(taxi);
        return new ResponseEntity<>(lloc, HttpStatus.OK);
    }

    //-------------------Retrouver la location correspondant à un client donné--------------------------------------------------------
    @RequestMapping(value = "/idclient={id}", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocationClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche des locations du client d'id " + id);
        Client cl = clientServiceImpl.read(id);
        List<Location> lloc = locationServiceImpl.read(cl);
        return new ResponseEntity<>(lloc, HttpStatus.OK);
    }

    //-------------------Retrouver la location correspondant à une période et un taxi--------------------------------------------------------
    @RequestMapping(value = "/{id}/{d1}/{d2}", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocationBetweenDatesAndTaxi(
            @PathVariable(value = "id") int id,
            @PathVariable(value = "d1") Date d1,
            @PathVariable(value = "d2") Date d2
            ) throws Exception {
        System.out.println("Recherche des locations du taxi d'id " + id + " dans la période du " + d1  + " au " + d2);
        List<Location> locations = locationServiceImpl.getLocationsByTaxiIdAndDateRange(id,d1, d2);
        
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }


    //-------------------Créer une location--------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Location> createLocation(@RequestBody Location loc) {
    try{
        System.out.println("Création de la location du taxi " + loc.getTaxifk());
        System.out.println(loc);
        locationServiceImpl.create(loc);
        return new ResponseEntity<>(loc, HttpStatus.OK);
    }catch (Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------Retrouver toutes les locations --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> listLocation() throws Exception {
        System.out.println("recherche de toutes les locations");
        return new ResponseEntity<>(locationServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Mettre à jour une commande d'un n° donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Location> majLocationTaxi(@PathVariable(value = "id") int id, @RequestBody Location newloc) throws Exception {
        System.out.println("maj de la location n° " + id);
        Location locact = locationServiceImpl.update(newloc);
        return new ResponseEntity<>(locact, HttpStatus.OK);
    }

    //-------------------Effacer une location d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteLocation(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("effacement de la location n°" + id);
        Location loc = locationServiceImpl.read(id);
        locationServiceImpl.delete(loc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver les locations pour une date--------------------------------------------------------
    @RequestMapping(value = "/datelocation/{datel}", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocationDate(@PathVariable(value = "datel") Date datel) throws Exception {
        System.out.println("recherche des locations de la date " + datel);
        List<Location> lloc = locationServiceImpl.readByDate(datel);
        return new ResponseEntity<>(lloc, HttpStatus.OK);
    }


    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }


}
