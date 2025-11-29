CREATE OR REPLACE PROCEDURE valider_pret (
    p_id_pret   IN NUMBER,
    p_interets  IN NUMBER,
    p_duree     IN TIMESTAMP, -- Date de fin du prêt ou durée calculée
    p_etat_pret IN NUMBER     -- Ex: 1 = Validé, 2 = Refusé
)
IS
    v_check NUMBER;
BEGIN
    -- 1. Validation : Le prêt existe-t-il ?
    SELECT COUNT(1) INTO v_check FROM PRET WHERE ID_PRET = p_id_pret;
    
    IF v_check = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : Le prêt avec l''ID ' || p_id_pret || ' n''existe pas.');
    END IF;

    -- 2. Validation : Vérifier les données cohérentes
    IF p_interets < 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Le taux d''intérêt ne peut pas être négatif.');
    END IF;

    -- 3. Mise à jour du prêt
    UPDATE PRET 
    SET INTERETS  = p_interets,
        DUREE     = p_duree,
        ETAT_PRET = p_etat_pret
    WHERE ID_PRET = p_id_pret;

    COMMIT; -- Valider la modification

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors de la validation du prêt : ' || SQLERRM);
        END IF;
END valider_pret;
/