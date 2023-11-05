package be.condorcet.api3haninisprojet2_1.entities;

import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "APILOCATION", schema = "ORA13", catalog = "OCRL.CONDORCET.BE")
public class Location {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_generator")
        @SequenceGenerator(name = "location_generator", sequenceName = "APILOCATION_SEQ", allocationSize = 1)
        @Column(name = "ID")
        private Integer id;
        @NonNull
        @Column(name = "DATELOC")
        private LocalDate dateLoc;
        @NonNull
        @Column(name = "KMTOTAL")
        private Integer kmtotal;
        @NonNull
        @Column(name = "ACOMPTE")
        private Double acompte;
        @Column(name = "TOTAL")
        @Formula("(SELECT l.KMTOTAL * t.PRIXKM FROM APITAXI t WHERE t.id = ID_3)")
        private Double total;

        @NonNull
        @ManyToOne @JoinColumn(name = "ID_1")
        private Adresse adresseDebut;

        @NonNull
        @ManyToOne @JoinColumn(name = "ID_2")
        private Adresse adresseFin;

        @NonNull
        @ManyToOne @JoinColumn(name = "ID_3")
        private Taxi taxi;

        @NonNull
        @ManyToOne @JoinColumn(name = "ID_4")
        private Client client;

        public Double getTotal() {
                return total;
        }

        public void setTotal(Double total) {
                this.total = total;
        }

}
