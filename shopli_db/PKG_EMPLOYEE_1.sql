--------------------------------------------------------
--  DDL for Package Body PKG_EMPLOYEE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "SHOPLI"."PKG_EMPLOYEE" AS

 PROCEDURE get_sorted_employees_safe (
    p_columns     IN SYS.ODCIVARCHAR2LIST,  -- Tên cột
    p_directions  IN SYS.ODCIVARCHAR2LIST,  -- ASC hoặc DESC
    p_cursor      OUT SYS_REFCURSOR
)
IS
    v_sql        VARCHAR2(4000);
    v_order_by   VARCHAR2(2000);

    v_allowed_columns CONSTANT SYS.ODCIVARCHAR2LIST := SYS.ODCIVARCHAR2LIST(
        'EMP_ID', 'EMP_NAME', 'SALARY'
    );

    -- Hàm kiểm tra giá trị có nằm trong danh sách hay không
    FUNCTION is_in_list(p_value VARCHAR2, p_list SYS.ODCIVARCHAR2LIST) RETURN BOOLEAN IS
    BEGIN
        FOR i IN 1 .. p_list.COUNT LOOP
            IF UPPER(p_value) = UPPER(p_list(i)) THEN
                RETURN TRUE;
            END IF;
        END LOOP;
        RETURN FALSE;
    END;
BEGIN
    IF p_columns.COUNT != p_directions.COUNT THEN
        RAISE_APPLICATION_ERROR(-20001, 'Số lượng cột và thứ tự không khớp.');
    END IF;

    -- Ghép ORDER BY từ các thành phần đã kiểm tra
    FOR i IN 1 .. p_columns.COUNT LOOP
        IF NOT is_in_list(p_columns(i), v_allowed_columns) THEN
            RAISE_APPLICATION_ERROR(-20002, 'Tên cột không hợp lệ: ' || p_columns(i));
        END IF;

        IF UPPER(p_directions(i)) NOT IN ('ASC', 'DESC') THEN
            RAISE_APPLICATION_ERROR(-20003, 'Kiểu sắp xếp không hợp lệ: ' || p_directions(i));
        END IF;

        IF i > 1 THEN
            v_order_by := v_order_by || ', ';
        END IF;

        -- Ghép chuỗi ORDER BY
        v_order_by := v_order_by || UPPER(p_columns(i)) || ' ' || UPPER(p_directions(i));
    END LOOP;

    v_sql := 'SELECT EMP_ID, EMP_NAME, SALARY FROM employees';
    
    IF v_order_by IS NOT NULL THEN
        v_sql := v_sql || ' ORDER BY ' || v_order_by;
    END IF;

    OPEN p_cursor FOR v_sql;
END;





    PROCEDURE add_employee (
        p_emp_id   IN NUMBER,
        p_emp_name IN VARCHAR2,
        p_salary   IN NUMBER
    ) IS
        vcode VARCHAR2(10000):='TITLE,CODE';
        v_sql VARCHAR2(10000);
    BEGIN
       

        vcode := replace('UPPER('||vcode||')' ,',', '||''-''||');
        
        
       v_sql := 'INSERT INTO employees (emp_id, emp_name, salary) ' ||
             'SELECT 1, ora_hash('||vcode||', 100000), 1 ' ||
             'FROM tutorials';

    -- Debug: In ra câu lệnh SQL để kiểm tra
    DBMS_OUTPUT.PUT_LINE(v_sql);

    -- Thực thi câu SQL động
    EXECUTE IMMEDIATE v_sql;


        COMMIT;
    END add_employee;







    PROCEDURE update_salary (
        p_emp_id     IN NUMBER,
        p_new_salary IN NUMBER
    ) IS
    BEGIN
        UPDATE employees
        SET
            salary = p_new_salary
        WHERE
            emp_id = p_emp_id;

        COMMIT;
    END update_salary;

    PROCEDURE delete_employee (
        p_emp_id IN NUMBER
    ) IS
    BEGIN
        DELETE FROM employees
        WHERE
            emp_id = p_emp_id;

        COMMIT;
    END delete_employee;

END pkg_employee;

/
