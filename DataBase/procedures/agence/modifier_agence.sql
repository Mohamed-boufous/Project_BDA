CREATE OR REPLACE PROCEDURE modifier_agence (
    p_id_agence      IN NUMBER,
    p_nom_agence     IN VARCHAR2,
    p_num_telephone  IN VARCHAR2,
    p_adresse        IN VARCHAR2,
    p_ville          IN VARCHAR2,
    p_code_postal    IN VARCHAR2,
    p_date_creation  IN TIMESTAMP
)
IS
    v_check NUMBER;
BEGIN
    -- 1. Vérifier si l'agence à modifier existe vraiment
    SELECT COUNT(1) INTO v_check FROM AGENCE WHERE ID_AGENCE = p_id_agence;
    
    IF v_check = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : L''agence avec l''ID ' || p_id_agence || ' n''existe pas.');
    END IF;

    -- 2. Vérification nom_agence unique (SAUF pour l'agence elle-même)
    SELECT COUNT(1) INTO v_check 
    FROM AGENCE 
    WHERE LOWER(NOM_AGENCE) = LOWER(p_nom_agence)
      AND ID_AGENCE != p_id_agence; -- Crucial : On s'exclut soi-même de la vérification
      
    IF v_check > 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Ce nom d''agence est déjà utilisé par une autre agence.');
    END IF;
    
    -- 3. Vérification adresse unique (SAUF pour l'agence elle-même)
    SELECT COUNT(1) INTO v_check 
    FROM AGENCE 
    WHERE LOWER(ADRESSE) = LOWER(p_adresse)
      AND ID_AGENCE != p_id_agence;
      
    IF v_check > 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Erreur : Une autre agence existe déjà à cette adresse.');
    END IF;
    
    -- 4. Vérification téléphone unique (SAUF pour l'agence elle-même)
    SELECT COUNT(1) INTO v_check 
    FROM AGENCE 
    WHERE NUM_TELEPHONE = p_num_telephone
      AND ID_AGENCE != p_id_agence;
      
    IF v_check > 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 'Erreur : Ce numéro de téléphone est déjà utilisé par une autre agence.');
    END IF;
    
    -- 5. Modification de l'agence
    UPDATE AGENCE
    SET NOM_AGENCE    = p_nom_agence,
        NUM_TELEPHONE = p_num_telephone,
        ADRESSE       = p_adresse,
        VILLE         = p_ville,
        CODE_POSTAL   = p_code_postal,
        -- Optionnel : Met à jour la date de création si fourni, sinon garde l'ancienne ?
        -- Ici on met à jour selon votre demande :
        DATE_CREATION = p_date_creation 
    WHERE ID_AGENCE = p_id_agence;

    COMMIT; 

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        -- Propagation propre de l'erreur vers Java
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors de la modification : ' || SQLERRM);
        END IF;
END modifier_agence;
/