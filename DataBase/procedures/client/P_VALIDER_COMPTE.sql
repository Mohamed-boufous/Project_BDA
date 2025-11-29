CREATE OR REPLACE PROCEDURE P_VALIDER_COMPTE (
    p_id_client IN NUMBER
)
IS
    v_check NUMBER;
    v_etat_actuel NUMBER;
BEGIN
    -- 1. Vérifier l'existence du compte pour ce client
    BEGIN
        SELECT ETAT_COMPTE INTO v_etat_actuel
        FROM COMPTE 
        WHERE ID_CLIENT = p_id_client;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20001, 'Erreur : Aucun compte associé à ce client.');
    END;

    -- 2. Vérifier si déjà validé
    IF v_etat_actuel = 1 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Info : Ce compte est déjà actif.');
    END IF;

    -- 3. Validation (Passage à 1)
    UPDATE COMPTE
    SET ETAT_COMPTE = 1 -- 1 = Actif
    WHERE ID_CLIENT = p_id_client;

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        -- Propagation des erreurs
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors de la validation : ' || SQLERRM);
        END IF;
END;
/