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
@Table(name = "CLIENT", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "CLIENT_ID_SEQ", allocationSize = 1)
    @Column(name = "IDCLIENT")
    private Integer idclient;

    @NonNull
    @Column(name = "MAIL")
    private String mail;

    @NonNull
    @Column(name = "NOM")
    private String nom;

    @NonNull
    @Column(name = "PRENOM")
    private String prenom;

    @NonNull
    @Column(name = "TEL")
    private String tel;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "clientfk")
    private Set<Location> locations;

}

