create or replace NONEDITIONABLE PROCEDURE transfert (
    p_RIB_emetteur     IN COMPTE.RIB%TYPE,
    p_RIB_destinataire IN COMPTE.RIB%TYPE,
    p_montant          IN COMPTE.SOLDE%TYPE
)
AS 
    v_count_emetteur NUMBER;
    v_count_destinataire NUMBER;
    r_solde_emetteur COMPTE.SOLDE%TYPE;

BEGIN
    IF p_montant <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Erreur: Le montant du virement doit être positif.');
    END IF;

    IF p_RIB_emetteur = p_RIB_destinataire THEN
        RAISE_APPLICATION_ERROR(-20004, 'Erreur: Impossible de faire un virement vers le même compte.');
    END IF;

    SELECT COUNT(RIB) INTO v_count_emetteur 
    FROM COMPTE WHERE RIB = p_RIB_emetteur;
    
    IF v_count_emetteur = 0 THEN  
        RAISE_APPLICATION_ERROR(-20001, 'Erreur: Le compte émetteur (' || p_RIB_emetteur || ') n''existe pas.');
    END IF; 

    SELECT COUNT(RIB) INTO v_count_destinataire 
    FROM COMPTE WHERE RIB = p_RIB_destinataire;
    
    IF v_count_destinataire = 0 THEN  
        RAISE_APPLICATION_ERROR(-20001, 'Erreur: Le compte destinataire (' || p_RIB_destinataire || ') n''existe pas.');
    END IF; 
     
    SELECT SOLDE INTO r_solde_emetteur
    FROM COMPTE WHERE RIB = p_RIB_emetteur;
    
    IF r_solde_emetteur < p_montant THEN 
        RAISE_APPLICATION_ERROR(-20002, 'Erreur: Solde insuffisant (' || r_solde_emetteur || ' DHS).');
    END IF;

    UPDATE COMPTE  
    SET SOLDE = SOLDE - p_montant
    WHERE RIB = p_RIB_emetteur;

    UPDATE COMPTE  
    SET SOLDE = SOLDE + p_montant
    WHERE RIB = p_RIB_destinataire;
    
    INSERT INTO TRANSACTIONS (
       CODE_T,
       TYPE_T,
       DATE_TRANSACTION,
       MONTANT,
       RIB_EMETTEUR,     
       RIB_DESTINATAIRE  
    )
    VALUES (
        TRANSACTION_SEQ.NEXTVAL,
        'VIREMENT',
        SYSDATE,   
        p_montant,
        p_RIB_emetteur,
        p_RIB_destinataire
    );
    
    COMMIT;
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END transfert;