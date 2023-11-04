package be.condorcet.api3haninisprojet2_1.services.location;

import be.condorcet.api3haninisprojet2_1.entities.Location;
import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.repositories.LocationRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;

@Service
@Transactional(rollbackOn = Exception.class)
public class LocationServiceImpl implements InterfLocationService{

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location create(Location location) throws Exception {
        locationRepository.save(location);
        return location;
    }

    @Override
    public Location read(Integer id) throws Exception {
        return locationRepository.findById(id).get();
    }

    @Override
    public Location update(Location location) throws Exception {
        read(location.getId());
        locationRepository.save(location);
        return location;
    }

    @Override
    public void delete(Location location) throws Exception {
        locationRepository.deleteById(location.getId());
    }

    @Override
    public List<Location> all() throws Exception {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> getLocationsByTaxi(Taxi t) throws Exception{
        List<Location> ll = locationRepository.findLocationByTaxi(t);
        return ll;
    }

    @Override
    public List<Location> getLocationsByDates(LocalDate now, LocalDate localDate) throws Exception{
        return locationRepository.findLocationByDates(now, localDate);
    }

    // @Override
    // public List<Location> getLocationsByTaxiAndDates(Taxi t, LocalDate d1, LocalDate d2) {
    //     return locationRepository.findLocationsByTaxiAndDates(t, d1, d2);
    // }
    
}
