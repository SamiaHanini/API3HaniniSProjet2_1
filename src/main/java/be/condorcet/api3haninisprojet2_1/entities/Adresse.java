package be.condorcet.api3haninisprojet2_1.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "APIADRESSE", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")

public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adresse_generator")
    @SequenceGenerator(name = "adresse_generator", sequenceName = "API_ADRESSE_SEQ", allocationSize = 1)


    private Integer id, cp;
    private String localite, rue, num;

}
