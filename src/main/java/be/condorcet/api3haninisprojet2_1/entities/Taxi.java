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
    @OneToMany(mappedBy = "taxifk")
    private List<Location> locations;


}
