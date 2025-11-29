CREATE OR REPLACE TRIGGER TRG_AUDIT_SALAIRE 
AFTER UPDATE OF SALAIRE ON EMPLOYE -- On précise "OF SALAIRE" pour être encore plus spécifique
FOR EACH ROW 
WHEN (NEW.SALAIRE <> OLD.SALAIRE) 
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
        HISTORIQUE_SALAIRE_SEQ.NEXTVAL, -- Correction : Nom de la séquence complet
        :NEW.CIN,                -- Le CIN de l'employé modifié
        :OLD.SALAIRE,            -- L'ancien salaire
        :NEW.SALAIRE,            -- Le nouveau salaire
        SYSDATE,                 -- La date et l'heure actuelle
        USER                     -- L'utilisateur Oracle connecté
    );
END; 
/