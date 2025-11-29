CREATE OR REPLACE PROCEDURE P_LICENCIER_EMPLOYE(p_cin IN VARCHAR2)
IS 
    v_count NUMBER; 
    v_existe NUMBER;
BEGIN 
    -- 1. Vérifier si l'employé existe
    SELECT COUNT(1) INTO v_existe FROM EMPLOYE WHERE CIN = p_cin;
    IF v_existe = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Ce CIN n''existe pas.');
    END IF;

    -- 2. Vérifier s'il est manager d'une équipe active
    -- On ne compte que les subordonnés qui sont encore ACTIFS
    SELECT COUNT(*) INTO v_count 
    FROM EMPLOYE 
    WHERE MANAGER_CIN = p_cin AND EST_ACTIF = 1;
    
    IF v_count > 0 THEN 
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : Impossible de licencier ce manager. Il dirige encore ' || v_count || ' employés actifs.'); 
    END IF; 

    -- 3. "Suppression" (Désactivation)
    UPDATE EMPLOYE 
    SET EST_ACTIF = 0,
        DATE_RECRUTEMENT = NULL -- Ou ajouter une colonne DATE_DEPART
    WHERE CIN = p_cin;

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors du licenciement : ' || SQLERRM);
        END IF;
END;
/