CREATE OR REPLACE PROCEDURE P_AUGMENTER_SALAIRE(
    p_cin               IN VARCHAR2, 
    p_taux_augmentation IN NUMBER
)
IS 
    v_exist NUMBER;
BEGIN
    SELECT 1 INTO v_exist FROM EMPLOYE WHERE CIN = p_cin;

    UPDATE EMPLOYE 
    SET SALAIRE = SALAIRE * (1 + p_taux_augmentation / 100)
    WHERE CIN = p_cin; 

    COMMIT;
    
    DBMS_OUTPUT.PUT_LINE('Succès : Salaire mis à jour avec une augmentation de ' || p_taux_augmentation || '%.');

EXCEPTION 
    WHEN NO_DATA_FOUND THEN 
        RAISE_APPLICATION_ERROR(-20001, 'Erreur : Employé introuvable, impossible d''augmenter le salaire.');
END;
/