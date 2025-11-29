package com.bd.gestion_bancaire.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pret"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Remboursement {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remboursement_gen")
    @SequenceGenerator(
            name = "remboursement_gen",
            sequenceName = "REMBOURSEMENT_SEQ",
            allocationSize = 1
    )
    @Id
    @Column(name = "ID_REMB", nullable = false)
    @EqualsAndHashCode.Include
    private Long idRemb;

    @Basic
    @Column(name = "DATE_REMB")
    private Timestamp dateRemb;

    @Basic
    @Column(name = "MONTANT", precision = 12, scale = 2)
    private BigDecimal montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRET", referencedColumnName = "ID_PRET", nullable = false)
    private Pret pret;

}
