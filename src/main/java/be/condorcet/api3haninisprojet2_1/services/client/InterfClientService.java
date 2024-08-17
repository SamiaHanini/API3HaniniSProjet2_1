package be.condorcet.api3haninisprojet2_1.services.client;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;


import java.util.List;

public interface InterfClientService extends InterfaceService<Client>{


    Client read(String nom, String prenom, String tel) throws Exception;
    List<Location> locationsForClient(Integer idClient) throws Exception;

}
