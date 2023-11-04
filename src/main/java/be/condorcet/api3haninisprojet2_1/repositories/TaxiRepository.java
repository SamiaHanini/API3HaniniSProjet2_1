package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;

import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer>{
    
    List<Taxi> findByCarburant(String car);


}
