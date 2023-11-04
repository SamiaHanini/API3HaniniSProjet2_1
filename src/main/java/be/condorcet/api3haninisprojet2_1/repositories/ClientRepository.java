package be.condorcet.api3haninisprojet2_1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.condorcet.api3haninisprojet2_1.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
    
    List<Client> findByName(String nom);

}
