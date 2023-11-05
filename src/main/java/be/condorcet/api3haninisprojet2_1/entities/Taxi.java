package be.condorcet.api3haninisprojet2_1.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "APITAXI", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")

public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxi_generator")
    @SequenceGenerator(name = "taxi_generator", sequenceName = "APITAXI_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @NonNull
    @Column(name = "IMMATRICULATION")
    private String immatriculation;
    @NonNull
    @Column(name = "CARBURANT")
    private String carburant;
    @NonNull
    @Column(name = "PRIXKM")
    private Double prixKm;
    @JsonIgnore
    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Location> llocations;

    public List<Client> getClientsByTaxi() {
        List<Client> uniqueClients = new ArrayList<>();
        for (Location location : this.llocations) {
            Client client = location.getClient();
            if (!uniqueClients.contains(client)) {
                uniqueClients.add(client);
            }
        }
        return uniqueClients;
    }
    
    
    public int getKilometresParcourus() {
        int totalKm = 0;
        for (Location location : this.llocations) {
            totalKm += location.getKmtotal();
        }
        return totalKm;
    }
    
    public float getMontantTotalDesLocations() {
        float totalMontant = 0;
        for (Location location : this.llocations) {
            totalMontant += location.getTotal();
        }
        return totalMontant;
    }
    

    public List<Location> getLocationEntreDeuxDates(LocalDate d1, LocalDate d2) {
    return llocations.stream()
            .filter(loc -> loc.getDateLoc().isAfter(d1.minusDays(1)) && loc.getDateLoc().isBefore(d2.plusDays(1)))
            .collect(Collectors.toList());
        }

}
