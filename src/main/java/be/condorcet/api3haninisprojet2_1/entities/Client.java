package be.condorcet.api3haninisprojet2_1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "APITCLIENT", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "APITCLIENT_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @NonNull
    @Column(name = "MAIL")
    private String mail;
    @NonNull
    @Column(name = "NOM")
    private String nom;
    @NonNull
    @Column(name = "PRENOM")
    private String  prenom;
    @NonNull
    @Column(name = "TEL")
    private String tel;
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Location> llocations;
    
}

