--------------------------------------------------------
--  DDL for Procedure LOG_ERROR
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "SHOPLI"."LOG_ERROR" (
    pi_proc_name IN VARCHAR2,
    pi_err_msg   IN VARCHAR2
) AS
BEGIN
    INSERT INTO error_logs (proc_name, err_msg)
    VALUES (pi_proc_name, pi_err_msg);
EXCEPTION
    WHEN OTHERS THEN
        -- Nếu lỗi ngay cả khi ghi log, thì in ra để tránh vòng lặp lỗi
        DBMS_OUTPUT.PUT_LINE('Lỗi khi ghi log: ' || SQLERRM);
END LOG_ERROR;

/
