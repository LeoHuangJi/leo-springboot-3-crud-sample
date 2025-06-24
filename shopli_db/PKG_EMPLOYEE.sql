--------------------------------------------------------
--  DDL for Package PKG_EMPLOYEE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "SHOPLI"."PKG_EMPLOYEE" AS
   
    PROCEDURE get_sorted_employees_safe (
    p_columns     IN SYS.ODCIVARCHAR2LIST,  -- Tên cột
    p_directions  IN SYS.ODCIVARCHAR2LIST,  -- ASC hoặc DESC
    p_cursor      OUT SYS_REFCURSOR
);
    -- Procedure khai báo ở đây
    PROCEDURE add_employee (
        p_emp_id   IN NUMBER,
        p_emp_name IN VARCHAR2,
        p_salary   IN NUMBER
    );

    PROCEDURE update_salary (
        p_emp_id     IN NUMBER,
        p_new_salary IN NUMBER
    );

    PROCEDURE delete_employee (
        p_emp_id IN NUMBER
    );

END pkg_employee;

/
