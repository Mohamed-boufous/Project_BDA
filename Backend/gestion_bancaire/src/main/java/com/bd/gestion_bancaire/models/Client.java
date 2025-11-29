package com.bd.gestion_bancaire.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"agence", "compte", "prets"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Client {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @SequenceGenerator(
            name = "client_gen",
            sequenceName = "CLIENT_SEQ",
            allocationSize = 1
    )
    @Id
    @Column(name = "ID_CLIENT", nullable = false)
    @EqualsAndHashCode.Include
    private Long idClient;

    @Basic
    @Column(name = "TYPE_CLIENT", length = 30)
    private String typeClient;

    @Basic
    @Column(name = "NOM", length = 50)
    private String nom;

    @Basic
    @Column(name = "PRENOM", length = 50)
    private String prenom;

    @Basic
    @Column(name = "NATIONALITE", length = 15)
    private String nationalite;

    @Basic
    @Column(name = "RAISON_SOCIALE", length = 20)
    private String raisonSociale;

    @Basic
    @Column(name = "CIN__PASSPORT", length = 20)
    private String cinPassport;

    @Basic
    @Column(name = "EMAIL", length = 30)
    private String email;

    @Basic
    @Column(name = "TELEPHONE", length = 20)
    private String telephone;

    @Basic
    @Column(name = "MOT_DE_PASSE", length = 100)
    private String motDePasse;

    @Basic
    @Column(name = "ADRESSE", length = 30)
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "ID_AGENCE", referencedColumnName = "ID_AGENCE", nullable = false)
    private Agence agence;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Compte compte;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Pret> prets;

}
