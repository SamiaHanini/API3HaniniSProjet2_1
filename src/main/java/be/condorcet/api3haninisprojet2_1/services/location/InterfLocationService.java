package be.condorcet.api3haninisprojet2_1.services.location;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;

import java.util.List;
import java.sql.Date;


public interface InterfLocationService extends InterfaceService<Location> {
    
    List<Location> read(Taxi t) throws Exception;

    List<Location> getLocationsByTaxiIdAndDateRange(Integer id, Date d1, Date d2) throws Exception;

     List<Location> read(Client cl) throws Exception;
     List<Location> readByDate(Date datel) throws Exception;
}
