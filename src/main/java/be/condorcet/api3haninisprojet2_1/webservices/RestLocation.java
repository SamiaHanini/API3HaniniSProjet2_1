package be.condorcet.api3haninisprojet2_1.webservices;


import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.services.taxi.InterfTaxiService;
import be.condorcet.api3haninisprojet2_1.services.client.InterfClientService;
import be.condorcet.api3haninisprojet2_1.services.location.InterfLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        List<Location> lloc = locationServiceImpl.getLocationsByTaxi(taxi);
        return new ResponseEntity<>(lloc, HttpStatus.OK);
    }

    //-------------------Retrouver la location correspondant à un client donné--------------------------------------------------------
    @RequestMapping(value = "/idclient={id}", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocationClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche des commandes du client d'id " + id);
        Client cl = clientServiceImpl.read(id);
        List<Location> lloc = locationServiceImpl.getLocationsByClient(cl);
        return new ResponseEntity<>(lloc, HttpStatus.OK);
    }

    //-------------------Retrouver la location correspondant à une période et un taxi--------------------------------------------------------
    @RequestMapping(value = "/idtaxi={id}&start={start}&end={end}", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocationBetweenDatesAndTaxi(
            @PathVariable(value = "id") int id,
            @PathVariable(value = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @PathVariable(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) throws Exception {
        System.out.println("Recherche des locations du taxi d'id " + id + " dans la période du " + start + " au " + end);
        
        Taxi taxi = taxiServiceImpl.read(id);
        List<Location> locations = locationServiceImpl.getLocationByDateLocBetweenAndTaxi(start, end, taxi);
        
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }


    //-------------------Créer une location--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Location> createLocation(@RequestBody Location loc) throws Exception {
        System.out.println("Création de la location du taxi " + loc.getTaxi());
        System.out.println(loc);
        locationServiceImpl.create(loc);
        return new ResponseEntity<>(loc, HttpStatus.OK);
    }
    //-------------------Mettre à jour une commande d'un n° donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Location> majLocationTaxi(@PathVariable(value = "id") int id, @RequestBody Location newloc) throws Exception {
        System.out.println("maj de la location n° " + id);
        newloc.setId(id);
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

    //-------------------Retrouver toutes les locations --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> listLocation() throws Exception {
        System.out.println("recherche de toutes les locations");
        return new ResponseEntity<>(locationServiceImpl.all(), HttpStatus.OK);
    }


    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}
