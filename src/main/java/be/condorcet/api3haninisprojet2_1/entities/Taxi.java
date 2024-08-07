package be.condorcet.api3haninisprojet2_1.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "TAXI", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")

public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxi_generator")
    @SequenceGenerator(name = "taxi_generator", sequenceName = "TAXI_ID_SEQ", allocationSize = 1)
    @Column(name = "IDTAXI")
    private Integer idtaxi;

    @NonNull
    @Column(name = "IMMATRICULATION")
    private String immatriculation;

    @NonNull
    @Column(name = "CARBURANT")
    private String carburant;

    @NonNull
    @Column(name = "PRIXKM")
    private Double prixkm;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "TAXIFK", fetch = FetchType.LAZY)
    private List<Location> llocations;


    // public List<Client> getClientsByTaxi() {
    //      List<Client> uniqueClients = new ArrayList<>();
    //      for (Location location : this.llocations) {
    //          Client client = location.getClient();
    //         if (!uniqueClients.contains(client)) {
    //              uniqueClients.add(client);
    //          }
    //      }
    //      return uniqueClients;
    //  }


    //  public int getKilometresParcourus() {
    //      int totalKm = 0;
    //      for (Location location : this.llocations) {
    //          totalKm += location.getKmtotal();
    //      }
    //      return totalKm;
    //  }

    //  public float getMontantTotalDesLocations() {
    //      float totalMontant = 0;
    //      for (Location location : this.llocations) {
    //          totalMontant += location.getTotal();
    //      }
    //      return totalMontant;
    // }


    //  public List<Location> getLocationEntreDeuxDates(LocalDate d1, LocalDate d2) {
    //  return llocations.stream()
    //         .filter(loc -> loc.getDateLoc().isAfter(d1.minusDays(1)) && loc.getDateLoc().isBefore(d2.plusDays(1)))
    //          .collect(Collectors.toList());
    //      }

}
