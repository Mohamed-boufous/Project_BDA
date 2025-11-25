CREATE OR REPLACE TRIGGER TRG_AUDIT_SALAIRE 
AFTER UPDATE ON EMPLOYE 
FOR EACH ROW 
WHEN (NEW.SALAIRE <> OLD.SALAIRE) -- Optimisation : se déclenche uniquement si changement
BEGIN 
    -- Insertion dans la table d'historique
    INSERT INTO HISTORIQUE_SALAIRE (
        ID_LOG, 
        CIN_EMPLOYE, 
        ANCIEN_SALAIRE, 
        NOUVEAU_SALAIRE, 
        DATE_MODIFICATION, 
        MODIFIE_PAR
    ) VALUES (
        HISTORIQUE_SEQ.NEXTVAL, -- Utilisation de la séquence pour l'ID
        :NEW.CIN,               -- Le CIN de l'employé modifié
        :OLD.SALAIRE,           -- L'ancien salaire
        :NEW.SALAIRE,           -- Le nouveau salaire
        SYSDATE,                -- La date et l'heure actuelle
        USER                    -- L'utilisateur Oracle connecté (ex: ADMIN_USER)
    );
END; 
/