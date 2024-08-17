package be.condorcet.api3haninisprojet2_1.services.client;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.repositories.ClientRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ClientServiceImpl implements InterfClientService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client create(Client client) throws Exception {
        client.setMail(client.getMail().toLowerCase(Locale.ROOT));
        client.setNom(client.getNom().toLowerCase(Locale.ROOT));
        client.setPrenom(client.getPrenom().toLowerCase(Locale.ROOT));
        clientRepository.save(client);
        return client;
    }

    @Override
    public Client read(Integer id) throws Exception {
        Optional<Client> ocl= clientRepository.findById(id);
        return ocl.get();
    }

    @Override
    public Client update(Client client) throws Exception {

        client.setMail(client.getMail().toLowerCase());
        client.setNom(client.getNom().toLowerCase());
        client.setPrenom(client.getPrenom().toLowerCase());
        clientRepository.save(client);
        return client;
    }

    @Override
    public void delete(Client client) throws Exception {
        clientRepository.deleteById(client.getIdclient());
    }

    @Override
    public List<Client> all() throws Exception {
        return clientRepository.findAll();
    }


    @Override
    public Client read(String nom, String prenom, String tel) throws Exception {

        return clientRepository.findByNomAndPrenomAndTel(nom.toLowerCase(), prenom.toLowerCase(), tel.toLowerCase());
    }

    @Override
    public List<Location> locationsForClient(Integer idClient) throws Exception{
        return clientRepository.locationsForClient(idClient);
    }


}