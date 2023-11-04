package be.condorcet.api3haninisprojet2_1.repositories;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{
    
     List<Location> findLocationByTaxi(Taxi taxi);

    List<Location> findLocationByDates(LocalDate d1, LocalDate d2);

   // List<Location> findLocationsByTaxiAndDates(Taxi Taxi, LocalDate d1, LocalDate d2);
}
