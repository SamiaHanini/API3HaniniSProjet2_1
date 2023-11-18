package be.condorcet.api3haninisprojet2_1.services.client;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.InterfaceService;


import java.util.List;

public interface InterfClientService extends InterfaceService<Client>{

    List<Client> read(String nom) throws Exception;

    Client read(String nom, String prenom, String tel) throws Exception;

    /*List<Client> read(Taxi taxi) throws Exception;*/


}
