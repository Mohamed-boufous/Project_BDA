CREATE OR REPLACE PROCEDURE P_RECRUTER_EMPLOYE(
    p_cin          IN VARCHAR2, 
    p_MANAGER_CIN  IN VARCHAR2, 
    p_ID_AGENCE    IN NUMBER, 
    p_nom          IN VARCHAR2, 
    p_prenom       IN VARCHAR2, 
    p_post         IN VARCHAR2, 
    p_salaire      IN NUMBER, 
    p_mot_de_passe IN VARCHAR2, 
    p_email        IN VARCHAR2, 
    p_telephone    IN VARCHAR2 
)
IS 
    v_dummy NUMBER; 
BEGIN
    -- 1. VERIFICATION AGENCE
    BEGIN 
        SELECT 1 INTO v_dummy FROM AGENCE WHERE ID_AGENCE = p_ID_AGENCE; 
    EXCEPTION 
        WHEN NO_DATA_FOUND THEN 
            RAISE_APPLICATION_ERROR(-20001, 'Erreur : Agence introuvable'); 
    END;

    -- 2. VERIFICATION MANAGER (NOUVEAU BLOC)
    -- On ne vérifie que si un manager a été saisi (si c'est NULL, on saute)
    IF p_MANAGER_CIN IS NOT NULL THEN
        BEGIN
            SELECT 1 INTO v_dummy FROM EMPLOYE WHERE CIN = p_MANAGER_CIN;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20003, 'Erreur : Le Manager (Chef) indiqué n''existe pas.');
        END;
    END IF;

    -- 3. VERIFICATION DOUBLON (L'employé existe-t-il déjà ?)
    BEGIN 
        SELECT 1 INTO v_dummy FROM EMPLOYE WHERE CIN = p_cin;
        -- Si trouvé -> Erreur
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Cet employé existe déjà !'); 
    EXCEPTION 
        WHEN NO_DATA_FOUND THEN 
            NULL; -- Tout va bien, il n'existe pas
    END;

    -- 4. INSERTION
    INSERT INTO EMPLOYE (
        CIN, MANAGER_CIN, ID_AGENCE, NOM, PRENOM, 
        POSTE, SALAIRE, DATE_RECRUTEMENT, MOT_DE_PASSE, EMAIL, TELEPHONE
    ) 
    VALUES(
        p_cin, p_MANAGER_CIN, p_ID_AGENCE, p_nom, p_prenom, 
        p_post, p_salaire, SYSDATE, p_mot_de_passe, p_email, p_telephone
    );
    
    COMMIT;
END;
/