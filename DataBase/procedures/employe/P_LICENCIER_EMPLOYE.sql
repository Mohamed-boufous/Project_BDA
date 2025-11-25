create or replace procedure P_LICENCIER_EMPLOYE(p_cin in varchar2)
is 
    v_count number; 
begin 
    -- 1. Vérifier si cet employé est le manager de quelqu'un
    
    select count(*) into v_count from employe where MANAGER_CIN = p_cin;
    
    if v_count > 0 then 
        raise_application_error(-20001, 'Erreur : Impossible de licencier ce manager. Il dirige encore ' || v_count || ' employés. Veuillez réaffecter son équipe avant.'); 
    end if; 

    -- 2. Suppression
    delete from employe where cin = p_cin;

    -- 3. Vérifier si on a vraiment supprimé quelqu'un
    if SQL%ROWCOUNT = 0 then
        raise_application_error(-20002, 'Erreur : Ce CIN n''existe pas, impossible de licencier un fantôme.');
    end if;

    -- 4. Valider
    commit;
    
    DBMS_OUTPUT.PUT_LINE('Succès : Employé licencié.');

end;
/