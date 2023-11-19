package be.condorcet.api3haninisprojet2_1.webservices;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.client.InterfClientService;
import be.condorcet.api3haninisprojet2_1.services.taxi.InterfTaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/clients")
public class RestClient {
    @Autowired
    private InterfClientService clientServiceImpl;

    @Autowired
    private InterfTaxiService taxiServiceImpl;


    //-------------------Retrouver le client correspondant à un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> getClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("recherche du client d' id " + id);
        Client client = clientServiceImpl.read(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //-------------------Retrouver les clients portant un nom donné--------------------------------------------------------
    @RequestMapping(value = "/nom={nom}", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listClientsNom(@PathVariable(value = "nom") String nom) throws Exception {
        System.out.println("recherche de " + nom);
        List<Client> clients;
        clients = clientServiceImpl.read(nom);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    //-------------------Retrouver le client correspondant à un triplet (nom,prénom,tel) unique donné--------------------------------------------------------
    @RequestMapping(value = "/{nom}/{prenom}/{tel}", method = RequestMethod.GET)
    public ResponseEntity<Client> getClientUnique(@PathVariable(value = "nom") String nom,
                                                  @PathVariable(value = "prenom") String prenom,
                                                  @PathVariable(value = "tel") String tel) throws Exception {
        System.out.println("recherche du client " + nom + " " + prenom + " " + tel);
        Client client = clientServiceImpl.read(nom, prenom, tel);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //-------------------Retrouver le client correspondant à un taxidonné--------------------------------------------------------@RequestMapping(value = "/idtaxi={idtaxi}", method = RequestMethod.GET)
   /* public ResponseEntity<List<Client>> getFactureTaxi(@PathVariable(value = "idtaxi") int id) throws Exception {
        System.out.println("recherche des clients du taxi d'id " + id);
        Taxi taxi = taxiServiceImpl.read(id);
        System.out.println(taxi);
        List<Client> llc = clientServiceImpl.read(taxi);
        return new ResponseEntity<>(llc, HttpStatus.OK);
    }*/
    

    //-------------------Créer un client--------------------------------------------------------
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws Exception {
        System.out.println("Création de Client " + client.getNom());
        clientServiceImpl.create(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //-------------------Mettre à jour un client d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Client> majClient(@PathVariable(value = "id") int id, @RequestBody Client nouvcli) throws Exception {
        System.out.println("maj de client id =  " + id);
        nouvcli.setId(id);
        Client clact = clientServiceImpl.update(nouvcli);
        return new ResponseEntity<>(clact, HttpStatus.OK);
    }

    //-------------------Effacer un client d'un id donné--------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteClient(@PathVariable(value = "id") int id) throws Exception {
        System.out.println("effacement du client d'id " + id);
        Client client = clientServiceImpl.read(id);
        clientServiceImpl.delete(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-------------------Retrouver tous les clients --------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> listClient() throws Exception {
        System.out.println("recherche de tous les clients");
        return new ResponseEntity<>(clientServiceImpl.all(), HttpStatus.OK);
    }

    //-------------------Gérer les erreurs--------------------------------------------------------
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleIOException(Exception ex) {
        System.out.println("erreur : " + ex.getMessage());
        return ResponseEntity.notFound().header("error", ex.getMessage()).build();
    }
}