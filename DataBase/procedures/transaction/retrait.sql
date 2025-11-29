create or replace NONEDITIONABLE PROCEDURE retrait (
    p_RIB     IN COMPTE.RIB%TYPE,
    p_montant IN COMPTE.SOLDE%TYPE
)
AS 
    v_count NUMBER;
    r_solde COMPTE.SOLDE%TYPE;

BEGIN
    
    IF p_montant <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Erreur: Le montant du retrait doit être positif.');
    END IF;

    SELECT COUNT(RIB) INTO v_count 
    FROM COMPTE  
    WHERE RIB = p_RIB;
    
    IF v_count = 0 THEN  
        RAISE_APPLICATION_ERROR(-20001, 'Erreur: Le compte (' || p_RIB || ') n''existe pas.');
    END IF; 
     
    SELECT SOLDE INTO r_solde
    FROM COMPTE 
    WHERE RIB = p_RIB;
    
    IF r_solde < p_montant THEN 
        RAISE_APPLICATION_ERROR(-20002, 'Erreur: Solde (' || r_solde || ' DHS) insuffisant pour un retrait de ' || p_montant || ' DHS.');
    END IF;

    UPDATE COMPTE  
    SET SOLDE = SOLDE - p_montant
    WHERE RIB = p_RIB;
    
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
        'RETRAIT',
        SYSDATE,   
        p_montant,
        p_RIB,          
        NULL              
    );
    
    COMMIT;
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END retrait;