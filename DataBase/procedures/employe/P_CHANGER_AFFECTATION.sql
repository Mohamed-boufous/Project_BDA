CREATE OR REPLACE PROCEDURE P_CHANGER_AFFECTATION(
    p_cin       IN VARCHAR2, 
    p_managerID IN VARCHAR2, 
    p_id_agence IN NUMBER
) 
IS 
    v_dummy NUMBER; 
BEGIN
    
    -- 1. VERIFICATION DE L'EMPLOYÉ (Celui qu'on veut muter)
    BEGIN 
        SELECT 1 INTO v_dummy FROM EMPLOYE WHERE CIN = p_cin; 
    EXCEPTION 
        WHEN NO_DATA_FOUND THEN 
            RAISE_APPLICATION_ERROR(-20001, 'Erreur : L''employé à affecter n''existe pas.'); 
    END; 

    -- 2. VERIFICATION DU NOUVEAU MANAGER (S'il est renseigné)
    IF p_managerID IS NOT NULL THEN 
        BEGIN 
            SELECT 1 INTO v_dummy FROM EMPLOYE WHERE CIN = p_managerID; 
        EXCEPTION 
            WHEN NO_DATA_FOUND THEN 
                RAISE_APPLICATION_ERROR(-20002, 'Erreur : Le nouveau manager n''existe pas.'); 
        END; 
    END IF; 

    -- 3. VERIFICATION DE LA NOUVELLE AGENCE
    BEGIN 
        SELECT 1 INTO v_dummy FROM AGENCE WHERE ID_AGENCE = p_id_agence; 
    EXCEPTION 
        WHEN NO_DATA_FOUND THEN 
            RAISE_APPLICATION_ERROR(-20003, 'Erreur : L''agence de destination n''existe pas.'); 
    END; 

    -- 4. MISE A JOUR (UPDATE)
    -- Notez bien la VIRGULE (,) et le nom de table corrigé
    UPDATE EMPLOYE 
    SET ID_AGENCE = p_id_agence, 
        MANAGER_CIN = p_managerID 
    WHERE CIN = p_cin; 

    -- 5. SAUVEGARDE
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Succès : Affectation modifiée pour l''employé ' || p_cin);

END;
/