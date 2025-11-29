CREATE OR REPLACE PROCEDURE P_CHANGER_AFFECTATION(
    p_cin       IN VARCHAR2, 
    p_managerID IN VARCHAR2, 
    p_id_agence IN NUMBER
) 
IS 
    v_dummy NUMBER; 
BEGIN
    
    -- 1. REGLE MÉTIER : Un employé ne peut pas être son propre chef
    IF p_cin = p_managerID THEN
        RAISE_APPLICATION_ERROR(-20004, 'Erreur : Un employé ne peut pas être son propre manager.');
    END IF;

    -- 2. VERIFICATION DE L'EMPLOYÉ
    BEGIN 
        SELECT 1 INTO v_dummy FROM EMPLOYE WHERE CIN = p_cin; 
    EXCEPTION 
        WHEN NO_DATA_FOUND THEN 
            RAISE_APPLICATION_ERROR(-20001, 'Erreur : L''employé à affecter (' || p_cin || ') n''existe pas.'); 
    END; 

    -- 3. VERIFICATION DU NOUVEAU MANAGER (S'il est renseigné)
    IF p_managerID IS NOT NULL THEN 
        BEGIN 
            SELECT 1 INTO v_dummy FROM EMPLOYE WHERE CIN = p_managerID; 
        EXCEPTION 
            WHEN NO_DATA_FOUND THEN 
                RAISE_APPLICATION_ERROR(-20002, 'Erreur : Le manager indiqué (' || p_managerID || ') n''existe pas.'); 
        END; 
    END IF; 

    -- 4. VERIFICATION DE LA NOUVELLE AGENCE
    BEGIN 
        SELECT 1 INTO v_dummy FROM AGENCE WHERE ID_AGENCE = p_id_agence; 
    EXCEPTION 
        WHEN NO_DATA_FOUND THEN 
            RAISE_APPLICATION_ERROR(-20003, 'Erreur : L''agence de destination n''existe pas.'); 
    END; 

    -- 5. MISE A JOUR
    UPDATE EMPLOYE 
    SET ID_AGENCE = p_id_agence, 
        MANAGER_CIN = p_managerID 
    WHERE CIN = p_cin; 

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        -- On laisse passer nos erreurs métier (-20xxx)
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors de l''affectation : ' || SQLERRM);
        END IF;
END;
/