CREATE OR REPLACE PROCEDURE P_AUGMENTER_SALAIRE(
    p_cin               IN VARCHAR2, 
    p_taux_augmentation IN NUMBER -- Ex: 5 pour 5%
)
IS 
    v_check NUMBER;
BEGIN
    -- 1. Validation de sécurité : Taux positif
    IF p_taux_augmentation <= 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erreur : Le taux d''augmentation doit être supérieur à 0.');
    END IF;

    -- 2. Vérification de l'existence de l'employé
    BEGIN
        SELECT 1 INTO v_check FROM EMPLOYE WHERE CIN = p_cin;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN 
            RAISE_APPLICATION_ERROR(-20001, 'Erreur : Employé introuvable avec le CIN ' || p_cin);
    END;

    -- 3. Mise à jour du salaire
    -- NOTE : Cette instruction va déclencher le TRIGGER 'TRG_AUDIT_SALAIRE' automatiquement.
    -- L'historique sera donc sauvegardé dans HISTORIQUE_SALAIRE.
    UPDATE EMPLOYE 
    SET SALAIRE = SALAIRE * (1 + p_taux_augmentation / 100)
    WHERE CIN = p_cin; 

    COMMIT; -- Valide la modif salaire ET l'insertion dans l'historique (via le trigger)

EXCEPTION 
    WHEN OTHERS THEN 
        ROLLBACK;
        -- On relance nos erreurs personnalisées (-20xxx)
        IF SQLCODE BETWEEN -20999 AND -20000 THEN
            RAISE;
        ELSE
            RAISE_APPLICATION_ERROR(-20000, 'Erreur technique lors de l''augmentation : ' || SQLERRM);
        END IF;
END;
/