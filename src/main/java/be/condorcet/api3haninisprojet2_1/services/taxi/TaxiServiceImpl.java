package be.condorcet.api3haninisprojet2_1.services.taxi;

import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.repositories.LocationRepository;
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

    @Autowired
    private LocationRepository locationRepository;

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

    public Taxi update(Taxi taxi) throws Exception {
        read(taxi.getIdtaxi());
        taxiRepository.save(taxi);

        List<Location> locations = locationRepository.findByTaxifk(taxi);

        for (Location location : locations) {
            double newTotal = location.getKmtotal() * taxi.getPrixkm();
            location.setTotal(newTotal);
        }

        locationRepository.saveAll(locations);
        return taxi;
    }


    @Override
    public void delete(Taxi taxi) throws Exception {
        taxiRepository.deleteById(taxi.getIdtaxi());
    }

    @Override
    public List<Taxi> all() throws Exception {
        List<Taxi> taxis=taxiRepository.findAll();
        return taxis;
    }

    @Override
    public Taxi getTaxiByImmatriculation(String immatriculation) throws Exception {
        return taxiRepository.findByImmatriculation(immatriculation);
    }

    @Override
    public List<Client> clientsForTaxi(Integer idTaxi) throws Exception{
        return taxiRepository.clientsForTaxi(idTaxi);

    }
    @Override
    public List<Location> locationsForTaxi(Integer idTaxi) throws Exception{
        return taxiRepository.locationsForTaxi(idTaxi);
    }

    @Override
    public Double totalKilometersForTaxi(Integer idTaxi) throws Exception {
       return taxiRepository.totalKilometersForTaxi(idTaxi);

    }

    @Override
    public Double totalCostForTaxi(Integer idTaxi) throws Exception {
        return taxiRepository.totalCostForTaxi(idTaxi);

    }

    
}
