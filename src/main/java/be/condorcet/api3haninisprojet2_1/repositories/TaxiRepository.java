package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Location;

import org.springframework.stereotype.Repository;


@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer>{

    Taxi findByImmatriculation(String immatriculation);

    @Query("SELECT DISTINCT c FROM Client c JOIN c.locations l JOIN l.taxifk t WHERE t.idtaxi = :taxiId")
    List<Client> clientsForTaxi(@Param("taxiId") Integer taxiId);

    @Query(value = "SELECT l FROM Location l WHERE l.taxifk.idtaxi = :taxiId")
    List<Location> locationsForTaxi(@Param("taxiId") Integer taxiId);

    @Query(value = "SELECT SUM(l.kmtotal) FROM Location l WHERE l.taxifk.idtaxi = :taxiId")
    Double totalKilometersForTaxi(@Param("taxiId") Integer taxiId);

    @Query(value = "SELECT SUM(l.total + l.acompte) FROM Location l JOIN Taxi t ON l.taxifk.idtaxi = t.idtaxi WHERE t.idtaxi = :taxiId")
    Double totalCostForTaxi(@Param("taxiId") Integer taxiId);
}
