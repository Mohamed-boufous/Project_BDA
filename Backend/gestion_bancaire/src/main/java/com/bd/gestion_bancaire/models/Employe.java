package com.bd.gestion_bancaire.models;

import com.bd.gestion_bancaire.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"subordonnes", "manager", "agence"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Employe {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employe_gen")
    @SequenceGenerator(
            name = "employe_gen",
            sequenceName = "EMPLOYE_SEQ",
            allocationSize = 1
    )
    @Id
    @Column(name = "CIN", nullable = false, length = 10)
    @EqualsAndHashCode.Include
    private String cin;

    @Basic
    @Column(name = "NOM", length = 50)
    private String nom;

    @Basic
    @Column(name = "PRENOM", length = 50)
    private String prenom;

    @Basic
    @Column(name = "POSTE", length = 20)
    private String poste;

    @Basic
    @Column(name = "SALAIRE", precision = 10, scale = 2)
    private BigDecimal salaire;

    @Basic
    @Column(name = "DATE_RECRUTEMENT")
    private Timestamp dateRecrutement;

    @Basic
    @Column(name = "MOT_DE_PASSE", length = 100)
    private String motDePasse;

    @Basic
    @Column(name = "EMAIL", length = 50)
    private String email;

    @Basic
    @Column(name = "TELEPHONE", length = 20)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, length = 15)
    private RoleName role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_CIN", referencedColumnName = "CIN")
    private Employe manager;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private Collection<Employe> subordonnes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AGENCE", referencedColumnName = "ID_AGENCE", nullable = false)
    private Agence agence;

}
