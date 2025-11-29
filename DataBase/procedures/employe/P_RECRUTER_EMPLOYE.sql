CREATE OR REPLACE PROCEDURE P_RECRUTER_EMPLOYE(
    p_cin          IN VARCHAR2, 
    p_MANAGER_CIN  IN VARCHAR2, 
    p_ID_AGENCE    IN NUMBER, 
    p_nom          IN VARCHAR2, 
    p_prenom       IN VARCHAR2, 
    p_poste        IN VARCHAR2, -- Attention: nom de colonne est POSTE
    p_salaire      IN NUMBER, 
    p_mot_de_passe IN VARCHAR2, 
    p_email        IN VARCHAR2, 
    p_telephone    IN VARCHAR2 
)
IS 
    v_count NUMBER; 
BEGIN
    -- 1. Validation de base
    IF p_salaire < 0 THEN
        RAISE_APPLICATION_ERROR(-20005, 'Erreur : Le salaire ne peut pas être négatif.');
    END IF;

    -- 2. Vérification AGENCE
    SELECT COUNT(1) INTO v_count FROM AGENCE WHERE ID_AGENCE = p_ID_AGENCE;
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : Agence introuvable.'); 
    END IF;

    -- 3. Vérification MANAGER (Si renseigné)
    IF p_MANAGER_CIN IS NOT NULL THEN
        SELECT COUNT(1) INTO v_count FROM EMPLOYE WHERE CIN = p_MANAGER_CIN;
        IF v_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20003, 'Erreur : Le Manager indiqué n''existe pas.');
        END IF;
    END IF;

    -- 4. Vérification DOUBLON CIN
    SELECT COUNT(1) INTO v_count FROM EMPLOYE WHERE CIN = p_cin;
    IF v_count > 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Cet employé (CIN) existe déjà !'); 
    END IF;

    -- 5. Vérification DOUBLON EMAIL (Important !)
    SELECT COUNT(1) INTO v_count FROM EMPLOYE WHERE EMAIL = p_email;
    IF v_count > 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 'Erreur : Cet email est déjà utilisé par un autre employé.'); 
    END IF;

    -- 6. INSERTION
    INSERT INTO EMPLOYE (
        CIN, MANAGER_CIN, ID_AGENCE, NOM, PRENOM, 
        POSTE, SALAIRE, DATE_RECRUTEMENT, MOT_DE_PASSE, EMAIL, TELEPHONE
    ) 
    VALUES(
        p_cin, p_MANAGER_CIN, p_ID_AGENCE, p_nom, p_prenom, 
        p_poste, p_salaire, SYSDATE, p_mot_de_passe, p_email, p_telephone
    );
    
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        -- On laisse passer nos erreurs métiers, on trappe les autres
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors du recrutement : ' || SQLERRM);
        END IF;
END;
/