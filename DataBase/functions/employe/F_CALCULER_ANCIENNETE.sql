create or replace function F_CALCULER_ANCIENNETE(p_cin in varchar2) 
return number 
is 
    
    v_date_rec employe.DATE_RECRUTEMENT%TYPE; 
    v_number_years number;
begin 
    -- 1. Récupération de la date
    select DATE_RECRUTEMENT into v_date_rec from employe where cin = p_cin;
    
    -- 2. Calcul
    v_number_years := (SYSDATE - CAST(v_date_rec AS DATE));
    
    --  On retourne un chiffre arrondi 
    -- Exemple : 5.8 années au lieu de 5.83920...
    return ROUND(v_number_years, 1); 

exception 
    when NO_DATA_FOUND then 
        -- Option A : Lever une erreur (Votre choix actuel)
        raise_application_error(-20001, 'Erreur : Employé introuvable pour calcul ancienneté.');
        
        
end;
/