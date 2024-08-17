package be.condorcet.api3haninisprojet2_1.services.adresse;

import be.condorcet.api3haninisprojet2_1.entities.Adresse;
import be.condorcet.api3haninisprojet2_1.repositories.AdresseRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class AdresseServiceImpl implements InterfAdresseService{

     @Autowired
    private AdresseRepository adresseRepository;

    @Override
    public Adresse create(Adresse adresse) throws Exception {
        adresse.setLocalite(adresse.getLocalite().toLowerCase());
        adresseRepository.save(adresse);
        return adresse;
    }

    @Override
    public Adresse read(Integer id) throws Exception {
        Optional<Adresse> ocl= adresseRepository.findById(id);
        return ocl.get();
    }

    @Override
    public Adresse update(Adresse adresse) throws Exception {

        adresse.setLocalite(adresse.getLocalite().toLowerCase());
        adresseRepository.save(adresse);
        return adresse;
    }

    @Override
    public void delete(Adresse adresse) throws Exception {
        adresseRepository.deleteById(adresse.getIdadresse());
    }

    @Override
    public List<Adresse> all() throws Exception {
        return adresseRepository.findAll();
    }

    @Override
    public List<Adresse> read(String localite) throws Exception {

        return adresseRepository.findByLocalite(localite.toLowerCase());
    }
    
}
