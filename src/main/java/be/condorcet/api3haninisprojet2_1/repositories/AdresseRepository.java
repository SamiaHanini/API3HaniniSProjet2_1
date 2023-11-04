package be.condorcet.api3haninisprojet2_1.repositories;

import be.condorcet.api3haninisprojet2_1.entities.Adresse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer>{

    List<Adresse> findByCp(int cp);
}
    

