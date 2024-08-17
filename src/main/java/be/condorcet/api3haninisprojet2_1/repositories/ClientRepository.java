package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

    Client findByNomAndPrenomAndTel(String nom, String prenom, String tel);

    @Query(value = "SELECT l FROM Location l WHERE l.clientfk.idclient = :clientId")
    List<Location> locationsForClient(@Param("clientId") Integer clientId);


}
