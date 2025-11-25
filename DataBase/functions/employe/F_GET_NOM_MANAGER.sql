CREATE OR REPLACE FUNCTION F_GET_NOM_MANAGER (p_cin_emp IN VARCHAR2) 
RETURN VARCHAR2 
IS 
   
    v_manager_nom    employe.NOM%TYPE; 
    v_manager_prenom employe.PRENOM%TYPE; 
    v_manager_cin    employe.MANAGER_CIN%TYPE;
BEGIN 
    
    -- ETAPE 1 : On cherche d'abord le CIN du manager de cet employé
    BEGIN
        SELECT MANAGER_CIN INTO v_manager_cin 
        FROM EMPLOYE 
        WHERE CIN = p_cin_emp;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Si on ne trouve même pas l'employé
            RAISE_APPLICATION_ERROR(-20001, 'Erreur : Cet employé est introuvable.');
    END;

    -- ETAPE 2 : On vérifie si ce manager existe (Est-ce que c'est NULL ?)
    IF v_manager_cin IS NULL THEN
        RETURN 'Cet employé n''a pas de manager (Autonome).';
    END IF;

    -- ETAPE 3 : Maintenant qu'on a le CIN du chef, on va chercher son nom
    SELECT NOM, PRENOM 
    INTO v_manager_nom, v_manager_prenom 
    FROM EMPLOYE 
    WHERE CIN = v_manager_cin;

    
    RETURN 'Le manager est : ' || v_manager_nom || ' ' || v_manager_prenom || ' (CIN: ' || v_manager_cin || ')';

EXCEPTION  
    WHEN NO_DATA_FOUND THEN 
        
        RETURN 'Erreur : Le manager indiqué n''existe pas en base.';
END;
/