CREATE OR REPLACE FUNCTION F_CHECK_LOGIN(
    p_cin IN VARCHAR2, 
    p_password IN VARCHAR2
) 
RETURN NUMBER 
IS
    v_check NUMBER;
BEGIN
    SELECT COUNT(*) 
    INTO v_check 
    FROM EMPLOYE 
    WHERE CIN = p_cin AND MOT_DE_PASSE = p_password;
    IF v_check = 1 THEN
        RETURN 1;
    ELSE
        RETURN 0; 
    END IF;
END;
/