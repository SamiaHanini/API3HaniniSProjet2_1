package be.condorcet.api3haninisprojet2_1.services.location;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.entities.Client;
import be.condorcet.api3haninisprojet2_1.repositories.LocationRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class LocationServiceImpl implements InterfLocationService{

    @Autowired
    private LocationRepository locationRepository;


    @Override
    public Location create(Location location) throws Exception {
        Location savedLocation = locationRepository.save(location);

        Location locationWithTotal = locationRepository.findById(savedLocation.getIdlocation())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Double total = locationWithTotal.getKmtotal() * locationWithTotal.getTaxifk().getPrixkm();

        locationWithTotal.setTotal(total);

        locationRepository.save(locationWithTotal);

        return locationWithTotal;
    }

    @Override
    public Location read(Integer id) throws Exception {
        return locationRepository.findById(id).get();
    }

    @Override
    public Location update(Location location) throws Exception {

        Location existingLocation = locationRepository.findById(location.getIdlocation())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        existingLocation.setKmtotal(location.getKmtotal());

        Taxi currentTaxi = location.getTaxifk();

        double newTotal = existingLocation.getKmtotal() * currentTaxi.getPrixkm();

        existingLocation.setTotal(newTotal);

        locationRepository.save(existingLocation);

        return existingLocation;
    }


    @Override
    public void delete(Location location) throws Exception {
        locationRepository.deleteById(location.getIdlocation());
    }

    @Override
    public List<Location> all() throws Exception {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> read(Taxi t) throws Exception{
        List<Location> ll = locationRepository.findByTaxifk(t);
        return ll;
    }

    @Override
    public List<Location> getLocationsByTaxiIdAndDateRange(Integer idtaxi, Date d1, Date d2) throws Exception{
        return locationRepository.findLocationsByTaxiIdAndDateRange(idtaxi, d1, d2);
    }

    @Override
    public List<Location> read(Client client) {
        List<Location> ll = locationRepository.findByClientfk(client);
        return ll;
    }

    @Override
    public List<Location> readByDate(Date datel) throws Exception {
        List<Location> ll = locationRepository.findByDateloc(datel);
        return ll;
    }


}
