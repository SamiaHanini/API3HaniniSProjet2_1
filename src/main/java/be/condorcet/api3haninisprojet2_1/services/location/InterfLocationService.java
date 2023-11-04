package be.condorcet.api3haninisprojet2_1.services.location;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;

import java.time.LocalDate;
import java.util.List;

public interface InterfLocationService extends InterfaceService<Location> {
    
    List<Location> getLocationsByTaxi(Taxi t) throws Exception;

    List<Location> getLocationsByDates(LocalDate now, LocalDate localDate) throws Exception;

   // List<Location> getLocationsByTaxiAndDates(Taxi taxi, LocalDate now, LocalDate localDate) throws Exception;
}
