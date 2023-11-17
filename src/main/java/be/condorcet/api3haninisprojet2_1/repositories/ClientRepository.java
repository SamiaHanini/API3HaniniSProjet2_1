package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import be.condorcet.api3haninisprojet2_1.entities.Client;

@EnableJpaRepositories
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
    
    List<Client> findByNom(String nom);

    Client findByNomAndPrenomAndTel(String nom, String prenom, String tel);


}
