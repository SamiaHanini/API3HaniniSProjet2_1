package be.condorcet.api3haninisprojet2_1.services.taxi;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.repositories.TaxiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)

public class TaxiServiceImpl implements InterfTaxiService{

     @Autowired
    private TaxiRepository taxiRepository;

    @Override
    public Taxi create(Taxi taxi) throws Exception {
        taxiRepository.save(taxi);
        return taxi;
    }

    @Override
    public Taxi read(Integer id) throws Exception {
        Optional<Taxi> ocl= taxiRepository.findById(id);
        return ocl.get();
    }

    @Override
    public Taxi update(Taxi taxi) throws Exception {
        read(taxi.getId());
        taxiRepository.save(taxi);
        return taxi;
    }

    @Override
    public void delete(Taxi taxi) throws Exception {
        taxiRepository.deleteById(taxi.getId());
    }

    @Override
    public List<Taxi> all() throws Exception {
        return taxiRepository.findAll();
    }

    @Override
    public List<Taxi> read(String car) throws Exception {
        return taxiRepository.findByCarburant(car+"%");
    }

    @Override
    public List<Client> getClientsByTaxi(Integer idTaxi) throws Exception {
        Taxi t = read(idTaxi);
        return t.getClientsByTaxi();
    }

    @Override
    public int getKilometresParcourus(Integer idTaxi) throws Exception {
        Taxi taxi = read(idTaxi);
        return taxi.getKilometresParcourus();
    }

    @Override
    public float getMontantTotalDesLocations(Integer idTaxi) throws Exception {
        Taxi taxi = read(idTaxi);
        return taxi.getMontantTotalDesLocations();
    }

    
}
