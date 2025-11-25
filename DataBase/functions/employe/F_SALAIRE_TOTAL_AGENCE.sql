CREATE OR REPLACE FUNCTION F_SALAIRE_TOTAL_AGENCE(p_id_agence IN NUMBER) 
RETURN NUMBER 
IS 
    v_salaire_total NUMBER; 
    v_check NUMBER; 
BEGIN 
    -- 1. Étape de Sécurité : Vérifier l'agence
    BEGIN
        SELECT 1 INTO v_check FROM AGENCE WHERE ID_AGENCE = p_id_agence;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20001, 'Erreur : L''agence demandée n''existe pas.');
    END;

    -- 2. Étape de Calcul
    -- Utilisation de NVL : Si la somme est NULL (agence vide), on met 0.
    SELECT NVL(SUM(SALAIRE), 0) INTO v_salaire_total 
    FROM EMPLOYE 
    WHERE ID_AGENCE = p_id_agence; 
    
    RETURN v_salaire_total; 

END;
/