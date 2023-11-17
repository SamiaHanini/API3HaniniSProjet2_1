package be.condorcet.api3haninisprojet2_1.repositories;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Client;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{
    
     List<Location> findLocationByTaxi(Taxi taxi);

    List<Location> findLocationByDatesAndTaxi(Taxi t,LocalDate d1, LocalDate d2);

    List<Location> findLocationsByClient(Client cl);
}
