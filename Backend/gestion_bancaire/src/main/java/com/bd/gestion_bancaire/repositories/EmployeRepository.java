package com.bd.gestion_bancaire.repositories;

import com.bd.gestion_bancaire.models.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, String> {

    Optional<Employe> findByEmail(String email);
    Optional<Employe> findByCin(String cin);
    boolean existsByEmail(String email);
    @Procedure(procedureName = "P_RECRUTER_EMPLOYE")
    void recruterEmploye(
            @Param("p_cin") String cin,
            @Param("p_MANAGER_CIN") String managerCin,
            @Param("p_ID_AGENCE") Long idAgence,
            @Param("p_nom") String nom,
            @Param("p_prenom") String prenom,
            @Param("p_post") String poste,
            @Param("p_salaire") Double salaire,
            @Param("p_mot_de_passe") String motDePasse,
            @Param("p_email") String email,
            @Param("p_telephone") String telephone
    );
    @Procedure(procedureName = "P_LICENCIER_EMPLOYE")
    void licencierEmploye(@Param("p_cin") String cin);

    @Procedure(procedureName = "P_CHANGER_AFFECTATION")
    void changerAffectation(
            @Param("p_cin") String cin,
            @Param("p_managerID") String managerCin,
            @Param("p_id_agence") Long idAgence
    );

    @Procedure(procedureName = "P_AUGMENTER_SALAIRE")
    void augmenterSalaire(
            @Param("p_cin") String cin,
            @Param("p_taux_augmentation")BigDecimal taux_augmentation
    );

    @Query(value = "SELECT F_CALCULER_ANCIENNETE(:p_cin) FROM DUAL", nativeQuery = true)
    BigDecimal calculerAnciennete(@Param("p_cin") String cin);
}
