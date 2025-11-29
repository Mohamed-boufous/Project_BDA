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
@ToString(exclude = {"emetteur", "destinataire"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Transactions {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_gen")
    @SequenceGenerator(
            name = "transaction_gen",
            sequenceName = "TRANSACTION_SEQ",
            allocationSize = 1
    )
    @Id
    @Column(name = "CODE_T", nullable = false, precision = 0)
    @EqualsAndHashCode.Include
    private Long codeT;

    @Basic
    @Column(name = "MONTANT", nullable = true, precision = 12, scale = 2)
    private BigDecimal montant;
    @Basic
    @Column(name = "DATE_TRANSACTION", nullable = true)
    private Timestamp dateTransaction;

    @Basic
    @Column(name = "TYPE_T", nullable = true, length = 10)
    private String typeT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RIB_EMETTEUR", referencedColumnName = "RIB", nullable = false)
    private Compte emetteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RIB_DESTINATAIRE", referencedColumnName = "RIB", nullable = true)
    private Compte destinataire;

}
