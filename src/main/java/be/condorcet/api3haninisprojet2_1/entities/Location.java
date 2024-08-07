package be.condorcet.api3haninisprojet2_1.entities;

import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "LOCATION", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")
public class Location {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_generator")
        @SequenceGenerator(name = "location_generator", sequenceName = "LOCATION_ID_SEQ", allocationSize = 1)
        @Column(name = "IDLOCATION")
        private Integer idlocation;

        @NonNull
        @Column(name = "DATELOC")
        private LocalDate dateloc;

        @NonNull
        @Column(name = "KMTOTAL")
        private Integer kmtotal;

        @NonNull
        @Column(name = "ACOMPTE")
        private Double acompte;

        @NonNull
        @Column(name = "TOTAL")
        //a verifier
        @Formula("(SELECT l.KMTOTAL * t.PRIXKM FROM TAXI t WHERE t.IDTAXI = TAXIFK)")
        private Double total;

        @NonNull
        @ManyToOne 
        @JoinColumn(name = "TAXIFK")
        private Taxi taxifk;

        @NonNull
        @ManyToOne 
        @JoinColumn(name = "CLIENTFK")
        private Client clientfk;

        @NonNull
        @ManyToOne 
        @JoinColumn(name = "ADRESSEDEPART")
        private Adresse adressedepart;

        @NonNull
        @ManyToOne 
        @JoinColumn(name = "ADRESSEFIN")
        private Adresse adressefin;


        // public Double getTotal() {
        //         return total;
        // }

        // public void setTotal(Double total) {
        //         this.total = total;
        // }

}
