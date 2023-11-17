package be.condorcet.api3haninisprojet2_1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "APIADRESSE", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adresse_generator")
    @SequenceGenerator(name = "adresse_generator", sequenceName = "APIADRESSE_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @NonNull
    @Column(name = "CP")
    private Integer cp;
    @NonNull
    @Column(name = "LOCALITE")
    private String localite;
    @NonNull
    @Column(name = "RUE")
    private String rue;
    @NonNull
    @Column(name = "NUM")
    private String num;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "adresseDebut", fetch = FetchType.LAZY)
    private List<Location> llocationsDebut;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "adresseFin", fetch = FetchType.LAZY)
    private List<Location> llocationsFin;




}
