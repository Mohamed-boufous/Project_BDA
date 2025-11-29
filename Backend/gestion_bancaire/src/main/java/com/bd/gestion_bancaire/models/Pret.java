package com.bd.gestion_bancaire.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"client", "remboursements"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pret {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pret_gen")
    @SequenceGenerator(
            name = "pret_gen",
            sequenceName = "PRET_SEQ",
            allocationSize = 1
    )
    @Id
    @Column(name = "ID_PRET", nullable = false)
    @EqualsAndHashCode.Include
    private Long idPret;

    @Basic
    @Column(name = "MONTANT", precision = 12, scale = 2)
    private BigDecimal montant;

    @Basic
    @Column(name = "DATE_PRET")
    private Timestamp datePret;

    @Basic
    @Column(name = "ETAT_PRET")
    private Short etatPret;

    @Basic
    @Column(name = "INTERETS", precision = 10, scale = 2)
    private BigDecimal interets;

    @Basic
    @Column(name = "DUREE")
    private Timestamp duree;

    @Basic
    @Column(name = "MONTANT_PAYE", precision = 10, scale = 2)
    private BigDecimal montantPaye;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID_CLIENT", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "pret")
    private Collection<Remboursement> remboursements;

}
