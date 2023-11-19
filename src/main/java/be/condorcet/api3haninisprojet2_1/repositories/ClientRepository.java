package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@EnableJpaRepositories
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
    
    List<Client> findByNomLike(String nom);

    Client findByNomAndPrenomAndTel(String nom, String prenom, String tel);
   // List<Client> findByTaxi(Taxi taxi);


}
