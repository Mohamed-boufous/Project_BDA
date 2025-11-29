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
@ToString(exclude = {"transactionsEmises", "transactionsRecues", "client"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Compte {
    @Id
    @Column(name = "RIB", nullable = false)
    @EqualsAndHashCode.Include
    private String rib;

    @Basic
    @Column(name = "SOLDE", precision = 12, scale = 2)
    private BigDecimal solde;

    @Basic
    @Column(name = "DATE_CREATION")
    private Timestamp dateCreation;

    @Basic
    @Column(name = "ETAT_COMPTE")
    private Short etatCompte;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID_CLIENT", nullable = false, unique = true)
    private Client client;

    @OneToMany(mappedBy = "emetteur", cascade = CascadeType.ALL)
    private Collection<Transactions> transactionsEmises;

    @OneToMany(mappedBy = "destinataire", cascade = CascadeType.ALL)
    private Collection<Transactions> transactionsRecues;

}
