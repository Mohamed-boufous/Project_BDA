CREATE OR REPLACE PROCEDURE ajouter_agence (
    p_nom_agence     IN VARCHAR2,
    p_num_telephone  IN VARCHAR2,
    p_adresse        IN VARCHAR2,
    p_ville          IN VARCHAR2,
    p_code_postal    IN VARCHAR2,
    p_date_creation  IN TIMESTAMP
)
IS
    -- Variable temporaire pour les vérifications
    v_count NUMBER;
BEGIN
    -- 1. Vérification nom_agence unique (Case insensitive)
    SELECT COUNT(1) INTO v_count 
    FROM AGENCE 
    WHERE LOWER(NOM_AGENCE) = LOWER(p_nom_agence);
    
    IF v_count > 0 THEN
        -- Code -20001 pour Nom Dupliqué
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : Une agence avec ce nom existe déjà.');
    END IF;
    
    -- 2. Vérification adresse unique
    SELECT COUNT(1) INTO v_count 
    FROM AGENCE 
    WHERE LOWER(ADRESSE) = LOWER(p_adresse);
    
    IF v_count > 0 THEN
        -- Code -20002 pour Adresse Dupliquée
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Une agence existe déjà à cette adresse.');
    END IF;
    
    -- 3. Vérification num_tele unique
    SELECT COUNT(1) INTO v_count 
    FROM AGENCE 
    WHERE NUM_TELEPHONE = p_num_telephone;
    
    IF v_count > 0 THEN
        -- Code -20003 pour Téléphone Dupliqué
        RAISE_APPLICATION_ERROR(-20003, 'Erreur : Ce numéro de téléphone est déjà utilisé.');
    END IF;
    
    -- 4. Insertion (Si toutes les validations passent)
    INSERT INTO AGENCE (
        ID_AGENCE, 
        NOM_AGENCE, 
        NUM_TELEPHONE, 
        ADRESSE, 
        VILLE, 
        CODE_POSTAL, 
        DATE_CREATION
    ) VALUES (
        AGENCE_SEQ.NEXTVAL, 
        p_nom_agence, 
        p_num_telephone, 
        p_adresse, 
        p_ville, 
        p_code_postal, 
        NVL(p_date_creation, SYSDATE) -- Utilise la date actuelle si p_date_creation est NULL
    );

    COMMIT; 

EXCEPTION
    -- Gestion des erreurs inattendues (ex: problème de séquence, erreur système)
    WHEN OTHERS THEN
        ROLLBACK;
        -- On relance l'exception pour que Java la capture (sauf si c'est déjà nos erreurs personnalisées)
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE; -- C'est une de nos erreurs, on la laisse passer
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors de la création de l''agence : ' || SQLERRM);
        END IF;
END ajouter_agence;
/