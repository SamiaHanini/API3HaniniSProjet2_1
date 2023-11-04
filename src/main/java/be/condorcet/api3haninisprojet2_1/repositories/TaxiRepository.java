package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;

import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer>{
    
    List<Taxi> findByCarburant(String car);

    // @Query("SELECT DISTINCT c FROM Client c " +
    // "JOIN Location l ON l.client.id = c.id " +
    // "WHERE l.taxi.id = :idtaxi")
    // List<Client> getClientsByTaxi(@Param("idtaxi") Integer idtaxi);

}
