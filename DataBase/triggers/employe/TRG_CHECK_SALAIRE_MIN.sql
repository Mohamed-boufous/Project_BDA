CREATE OR REPLACE TRIGGER TRG_CHECK_SALAIRE_MIN
BEFORE INSERT OR UPDATE OF SALAIRE ON EMPLOYE -- Optimisation : se déclenche seulement si on touche au salaire
FOR EACH ROW
BEGIN
    -- On vérifie si le salaire est inférieur au SMIG OU s'il est vide
    IF :NEW.SALAIRE < 3000 OR :NEW.SALAIRE IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : Le salaire est invalide. Il doit être renseigné et supérieur au SMIG (3000 DH).');
    END IF;
END;
/