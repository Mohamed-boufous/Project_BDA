/*==============================================================*/
/* 2. CRÉATION DES RÔLES                                        */
/*==============================================================*/
CREATE ROLE R_CLIENT;
CREATE ROLE R_EMPLOYE;
CREATE ROLE R_ADMIN;
CREATE ROLE R_SUPER_ADMIN;

/*==============================================================*/
/* 3. ATTRIBUTION DES PRIVILÈGES : R_CLIENT                     */
/*==============================================================*/
-- Lecture des données personnelles
GRANT SELECT ON CLIENT TO R_CLIENT;
GRANT SELECT ON COMPTE TO R_CLIENT;
GRANT SELECT ON PRET TO R_CLIENT;
GRANT SELECT ON REMBOURSEMENT TO R_CLIENT;
GRANT SELECT ON TRANSACTIONS TO R_CLIENT;

-- Modification du mot de passe uniquement
GRANT UPDATE (MOT_DE_PASSE) ON CLIENT TO R_CLIENT;

-- Transactions et Demandes
GRANT EXECUTE ON TRANSFERT TO R_CLIENT;      -- Pour faire un virement
GRANT EXECUTE ON DEMANDER_PRET TO R_CLIENT;  -- Nouvelle procédure ajoutée

/*==============================================================*/
/* 4. ATTRIBUTION DES PRIVILÈGES : R_EMPLOYE                    */
/*==============================================================*/
-- Lecture globale
GRANT SELECT ON AGENCE TO R_EMPLOYE;
GRANT SELECT ON CLIENT TO R_EMPLOYE;
GRANT SELECT ON COMPTE TO R_EMPLOYE;
GRANT SELECT ON PRET TO R_EMPLOYE;
GRANT SELECT ON REMBOURSEMENT TO R_EMPLOYE;
GRANT SELECT ON TRANSACTIONS TO R_EMPLOYE;

-- Gestion des Clients et Comptes
GRANT INSERT, UPDATE ON CLIENT TO R_EMPLOYE;
GRANT INSERT, UPDATE ON COMPTE TO R_EMPLOYE;

-- Opérations de Guichet (Procédures transactionnelles)
GRANT EXECUTE ON DEPOT TO R_EMPLOYE;
GRANT EXECUTE ON RETRAIT TO R_EMPLOYE;
GRANT EXECUTE ON TRANSFERT TO R_EMPLOYE;

-- Gestion des Prêts
GRANT INSERT, UPDATE ON PRET TO R_EMPLOYE;
GRANT INSERT ON REMBOURSEMENT TO R_EMPLOYE;

-- Validation et Demande pour le compte du client
GRANT EXECUTE ON DEMANDER_PRET TO R_EMPLOYE; -- L'employé peut saisir pour le client
GRANT EXECUTE ON VALIDER_PRET TO R_EMPLOYE;  -- Nouvelle procédure ajoutée

/*==============================================================*/
/* 5. ATTRIBUTION DES PRIVILÈGES : R_ADMIN		        */
/*==============================================================*/
-- Héritage : Un Admin peut tout faire ce qu'un Employé fait
GRANT R_EMPLOYE TO R_ADMIN;

-- Gestion des employés de l'agence (CRUD Table)
GRANT SELECT, INSERT, UPDATE, DELETE ON EMPLOYE TO R_ADMIN;

-- Audit
GRANT SELECT ON HISTORIQUE_SALAIRE TO R_ADMIN;

-- Procédures RH (Gestion d'équipe) - NOUVEAUX AJOUTS
GRANT EXECUTE ON P_RECRUTER_EMPLOYE TO R_ADMIN;
GRANT EXECUTE ON P_CHANGER_AFFECTATION TO R_ADMIN;
GRANT EXECUTE ON P_AUGMENTER_SALAIRE TO R_ADMIN;
GRANT EXECUTE ON P_LICENCIER_EMPLOYE TO R_ADMIN;

-- Fonctions RH (Consultation) - NOUVEAUX AJOUTS
GRANT EXECUTE ON F_GET_NOM_MANAGER TO R_ADMIN;
GRANT EXECUTE ON F_CALCULER_ANCIENNETE TO R_ADMIN;
GRANT EXECUTE ON F_SALAIRE_TOTAL_AGENCE TO R_ADMIN;

/*==============================================================*/
/* 6. ATTRIBUTION DES PRIVILÈGES : R_SUPER_ADMIN                */
/*==============================================================*/
-- Héritage : Un Super-Admin est aussi un Admin
GRANT R_ADMIN TO R_SUPER_ADMIN;

-- Gestion des Agences (CRUD Table)
GRANT SELECT, INSERT, UPDATE, DELETE ON AGENCE TO R_SUPER_ADMIN;

-- Procédures de gestion d'Agence - NOUVEAUX AJOUTS
GRANT EXECUTE ON AJOUTER_AGENCE TO R_SUPER_ADMIN;
GRANT EXECUTE ON MODIFIER_AGENCE TO R_SUPER_ADMIN;

-- Droits étendus pour la maintenance
GRANT ALL PRIVILEGES ON CLIENT TO R_SUPER_ADMIN;
GRANT ALL PRIVILEGES ON COMPTE TO R_SUPER_ADMIN;

/*==============================================================*/
/* 7. CRÉATION DE L'UTILISATEUR TECHNIQUE (SPRING BOOT)         */
/*==============================================================*/
CREATE USER BANK_APP_USER IDENTIFIED BY "gb1234";

GRANT CONNECT, RESOURCE TO BANK_APP_USER;
GRANT UNLIMITED TABLESPACE TO BANK_APP_USER;

-- Rôle Suprême pour l'application (le code Java filtre les accès)
GRANT R_SUPER_ADMIN TO BANK_APP_USER;

-- Accès aux Séquences (Indispensable pour JPA)
GRANT SELECT ON AGENCE_SEQ TO BANK_APP_USER;
GRANT SELECT ON CLIENT_SEQ TO BANK_APP_USER;
GRANT SELECT ON TRANSACTIONS_SEQ TO BANK_APP_USER;
GRANT SELECT ON PRET_SEQ TO BANK_APP_USER;
GRANT SELECT ON REMBOURSEMENT_SEQ TO BANK_APP_USER;
GRANT SELECT ON HISTORIQUE_SALAIRE_SEQ TO BANK_APP_USER;