package be.condorcet.api3haninisprojet2_1.services.location;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;

import java.time.LocalDate;
import java.util.List;

public interface InterfLocationService extends InterfaceService<Location> {
    
    List<Location> getLocationsByTaxi(Taxi t) throws Exception;

    List<Location> getLocationByDateLocBetweenAndTaxi(LocalDate d1, LocalDate d2, Taxi t) throws Exception;

     List<Location> getLocationsByClient(Client cl) throws Exception;

}
