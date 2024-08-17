package be.condorcet.api3haninisprojet2_1.repositories;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Client;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

    @Query("SELECT DISTINCT l FROM Location l JOIN l.taxifk t WHERE t.idtaxi = :taxiId AND l.dateloc BETWEEN :startDate AND :endDate")
    List<Location> findLocationsByTaxiIdAndDateRange(@Param("taxiId") Integer taxiId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Location> findByClientfk(Client cl);

    List<Location> findByTaxifk(Taxi taxi);

    @Query(value ="SELECT l FROM Location l WHERE l.dateloc = :datel")
    List<Location> findByDateloc(@Param("datel") Date datel);

}
