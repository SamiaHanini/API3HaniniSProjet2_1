package be.condorcet.api3haninisprojet2_1.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "APITCLIENT", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "API_TCLIENT_SEQ", allocationSize = 1)
    
    private Integer id;
    private String mail, nom, prenom, tel;


    
}
