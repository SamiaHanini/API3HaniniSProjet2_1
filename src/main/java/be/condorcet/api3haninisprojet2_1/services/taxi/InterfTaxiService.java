package be.condorcet.api3haninisprojet2_1.services.taxi;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;
import be.condorcet.api3haninisprojet2_1.entities.Client;

import java.util.List;


public interface InterfTaxiService extends InterfaceService<Taxi>{

     List<Taxi> read(String carburant) throws Exception;

    List<Client> getClientsByTaxi(Integer idTaxi) throws Exception;

    int getKilometresParcourus(Integer idTaxi) throws Exception;

    float getMontantTotalDesLocations(Integer idTaxi) throws Exception;

    
}
