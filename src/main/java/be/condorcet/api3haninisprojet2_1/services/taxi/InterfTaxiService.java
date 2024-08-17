package be.condorcet.api3haninisprojet2_1.services.taxi;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;
import be.condorcet.api3haninisprojet2_1.entities.Client;

import java.util.List;


public interface InterfTaxiService extends InterfaceService<Taxi>{

    Taxi getTaxiByImmatriculation(String immatriculation) throws Exception;


    List<Client> clientsForTaxi(Integer idTaxi) throws Exception;

    List<Location> locationsForTaxi(Integer idTaxi) throws Exception;

    Double totalKilometersForTaxi(Integer idTaxi) throws Exception;

    Double totalCostForTaxi(Integer idTaxi) throws Exception;

    
}
