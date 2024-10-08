package be.condorcet.api3haninisprojet2_1.entities;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "ADRESSE", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adresse_generator")
    @SequenceGenerator(name = "adresse_generator", sequenceName = "ADRESSE_ID_SEQ", allocationSize = 1)
    @Column(name = "IDADRESSE")
    private Integer idadresse;

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


}
