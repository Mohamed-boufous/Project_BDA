package com.bd.gestion_bancaire.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"clients", "employes"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Agence {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agence_gen")
    @SequenceGenerator(
            name = "agence_gen",
            sequenceName = "AGENCE_SEQ",
            allocationSize = 1
    )
    @Id
    @Column(name = "ID_AGENCE", nullable = false)
    @EqualsAndHashCode.Include
    private Long idAgence;

    @Basic
    @Column(name = "NOM_AGENCE", length = 50)
    private String nomAgence;

    @Basic
    @Column(name = "NUM_TELEPHONE", length = 15)
    private String numTelephone;

    @Basic
    @Column(name = "VILLE", length = 15)
    private String ville;

    @Basic
    @Column(name = "CODE_POSTAL", length = 10)
    private String codePostal;

    @Basic
    @Column(name = "DATE_CREATION")
    private Timestamp dateCreation;

    @Basic
    @Column(name = "ADRESSE", length = 30)
    private String adresse;

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    private Collection<Client> clients;

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    private Collection<Employe> employes;

}
