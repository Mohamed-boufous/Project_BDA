CREATE OR REPLACE TRIGGER TRG_CHECK_MANAGER_VALID
BEFORE INSERT OR UPDATE ON EMPLOYE
FOR EACH ROW
BEGIN
    -- 1. On ne vérifie que si un manager a été désigné (car NULL est autorisé)
    IF :NEW.MANAGER_CIN IS NOT NULL THEN
        
        -- 2. On compare le CIN de l'employé avec celui de son chef
        IF :NEW.CIN = :NEW.MANAGER_CIN THEN
            RAISE_APPLICATION_ERROR(-20005, 'Erreur : Un employé ne peut pas être son propre manager.');
        END IF;
        
    END IF;
END;
/