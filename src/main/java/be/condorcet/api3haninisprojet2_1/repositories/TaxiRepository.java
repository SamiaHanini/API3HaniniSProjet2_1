package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;

import org.springframework.stereotype.Repository;

//@EnableJpaRepositories
@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer>{
    
    List<Taxi> findByCarburantLike(String carburant);

    @Query(value = """
            SELECT DISTINCT c
            FROM Client c
                     JOIN Location l ON l.client.id = c.id
                     JOIN Taxi t ON t.id = l.taxi.id
            WHERE t.id = :taxiId""")
    List<Client> clientsForTaxi(@Param("taxiId") Integer taxiId);

    /*@Query(value = "SELECT l FROM Location l WHERE l.taxi.id = :taxiId")
    List<Location> locationsForTaxi(@Param("taxiId") Integer taxiId);*/

    @Query(value = "SELECT SUM(l.kmtotal) FROM Location l WHERE l.taxi.id = :taxiId")
    Double totalKilometersForTaxi(@Param("taxiId") Integer taxiId);

    @Query(value = "SELECT SUM(l.kmtotal * t.prixKm) FROM Location l JOIN l.taxi t WHERE t.id = :taxiId")
    Double totalCostForTaxi(@Param("taxiId") Integer taxiId);








}
