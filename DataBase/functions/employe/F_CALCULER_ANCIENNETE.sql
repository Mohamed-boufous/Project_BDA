create or replace function F_CALCULER_ANCIENNETE(p_cin in varchar2) 
return number 
is 
    v_date_rec DATE; -- On utilise DATE directement pour faciliter le calcul
    v_years number;
begin 
    -- 1. Récupération et conversion immédiate du TIMESTAMP en DATE
    -- (MONTHS_BETWEEN préfère les types DATE)
    select CAST(DATE_RECRUTEMENT AS DATE) into v_date_rec 
    from EMPLOYE 
    where CIN = p_cin;
    
    -- 2. Calcul précis des années
    -- MONTHS_BETWEEN donne le nombre de mois. On divise par 12 pour avoir les années.
    v_years := MONTHS_BETWEEN(SYSDATE, v_date_rec) / 12;
    
    -- 3. Retour arrondi à 1 décimale (ex: 5.5 ans)
    -- On utilise ABS pour éviter des nombres négatifs si la date est dans le futur (erreur de saisie)
    return ROUND(ABS(v_years), 1); 

exception 
    when NO_DATA_FOUND then 
        -- Lever une erreur est correct pour une procédure, 
        -- mais attention : cela fera planter une requête SQL globale (SELECT F_... FROM EMPLOYE).
        -- Pour l'instant, on garde votre logique :
        raise_application_error(-20001, 'Erreur : Employé avec CIN ' || p_cin || ' introuvable.');
        
    when OTHERS then
        raise_application_error(-20002, 'Erreur inattendue lors du calcul d''ancienneté.');
end;
/