package be.condorcet.api3haninisprojet2_1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "APITAXI", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")

public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxi_generator")
    @SequenceGenerator(name = "taxi_generator", sequenceName = "API_TAXI_SEQ", allocationSize = 1)

    private Integer id;
    private String immatriculation, carburant;
    private Double prixKm;
    
    @OneToMany(mappedBy = "taxi")
    @ToString.Exclude
    private List<Location> locations = new ArrayList<>();

    public List<Client> getClientsByTaxi() {
        List<Client> uniqueClients = new ArrayList<>();
        for (Location location : this.locations) {
            Client client = location.getClient();
            if (!uniqueClients.contains(client)) {
                uniqueClients.add(client);
            }
        }
        return uniqueClients;
    }
    
    
    public int getKilometresParcourus() {
        int totalKm = 0;
        for (Location location : this.locations) {
            totalKm += location.getKmtotal();
        }
        return totalKm;
    }
    
    public float getMontantTotalDesLocations() {
        float totalMontant = 0;
        for (Location location : this.locations) {
            totalMontant += location.getTotal();
        }
        return totalMontant;
    }
    

    public List<Location> getLocationEntreDeuxDates(LocalDate d1, LocalDate d2) {
    return locations.stream()
            .filter(loc -> loc.getDateLoc().isAfter(d1.minusDays(1)) && loc.getDateLoc().isBefore(d2.plusDays(1)))
            .collect(Collectors.toList());
        }

}
