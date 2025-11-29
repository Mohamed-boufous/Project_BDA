CREATE OR REPLACE PROCEDURE demander_pret (
    p_id_client IN NUMBER, -- INTEGER est un alias de NUMBER
    p_montant   IN NUMBER
    -- p_result OUT VARCHAR2 : SUPPRIMÉ (On utilise les Exceptions)
)
IS
    v_check_client NUMBER;
BEGIN
    -- 1. Validation : Montant positif
    IF p_montant <= 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : Le montant du prêt doit être supérieur à 0.');
    END IF;

    -- 2. Validation : Le client existe-t-il ?
    SELECT COUNT(1) INTO v_check_client 
    FROM CLIENT 
    WHERE ID_CLIENT = p_id_client;

    IF v_check_client = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Le client demandeur n''existe pas.');
    END IF;

    -- 3. Insertion du nouveau prêt
    -- Note : On met ETAT_PRET à 0 (En Attente) par défaut.
    -- INTERETS et DUREE sont NULL car ils seront définis/négociés lors de la validation par l'employé.
    INSERT INTO PRET (
        ID_PRET, 
        ID_CLIENT, 
        MONTANT, 
        DATE_PRET, 
        ETAT_PRET, 
        INTERETS, 
        DUREE
    ) VALUES (
        PRET_SEQ.NEXTVAL, 
        p_id_client, 
        p_montant, 
        SYSDATE, 
        0,    -- 0 = Statut "En Attente de validation"
        NULL, -- Sera rempli par l'agent
        NULL  -- Sera rempli par l'agent (ou vous pouvez l'ajouter en paramètre d'entrée)
    );

    COMMIT; -- Valider la transaction

EXCEPTION
    -- Gestion des erreurs inattendues
    WHEN OTHERS THEN
        ROLLBACK;
        -- Si c'est déjà une de nos erreurs (-20xxx), on la relance telle quelle
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            -- Sinon, c'est une erreur technique (FK, Table space, etc.)
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors de la demande de prêt : ' || SQLERRM);
        END IF;
END demander_pret;
/