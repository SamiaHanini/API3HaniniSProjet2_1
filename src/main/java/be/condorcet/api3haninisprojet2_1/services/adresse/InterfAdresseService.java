package be.condorcet.api3haninisprojet2_1.services.adresse;

import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;

import java.util.List;

public interface InterfAdresseService extends InterfaceService<Adresse> {

    List<Adresse> read(String localite) throws Exception;

}
