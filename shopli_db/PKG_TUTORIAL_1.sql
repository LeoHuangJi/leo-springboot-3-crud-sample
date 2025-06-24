--------------------------------------------------------
--  DDL for Package Body PKG_TUTORIAL
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "SHOPLI"."PKG_TUTORIAL" AS
PROCEDURE search_tutorials (
    pi_keyword             IN VARCHAR2,
    pi_title               IN VARCHAR2,
    pi_description         IN VARCHAR2,
    pi_published           IN NUMBER,
    pi_order_by_query_builder IN VARCHAR2,
    pi_page                IN NUMBER,
    pi_size                IN NUMBER,
    po_result              OUT PKG_COMMON.ref_cursor,
    po_total               OUT NUMBER,
    po_error_msg           OUT VARCHAR2
) IS
    v_offset       NUMBER := (pi_page - 1) * pi_size;
    v_where_clause VARCHAR2(4000) := ' WHERE 1=1 ';
    v_sql_count    CLOB;
    v_sql_data     CLOB;
BEGIN
    po_error_msg := NULL;

    IF pi_keyword IS NOT NULL THEN
        v_where_clause := v_where_clause || 
            ' AND (t.TITLE LIKE ''%' || TRIM(pi_keyword) || '%'' ' ||
            'OR t.DESCRIPTION LIKE ''%' || TRIM(pi_keyword) || '%'' ' ||
            'OR t.CODE LIKE ''%' || TRIM(pi_keyword) || '%'') ';
    END IF;

    IF pi_title IS NOT NULL THEN
        v_where_clause := v_where_clause || 
            ' AND t.TITLE LIKE ''%' || TRIM(pi_title) || '%'' ';
    END IF;

    IF pi_description IS NOT NULL  THEN
        v_where_clause := v_where_clause || 
            ' AND t.DESCRIPTION LIKE ''%' || TRIM(pi_description) || '%'' ';
    END IF;

    IF pi_published IS NOT NULL THEN
        v_where_clause := v_where_clause || ' AND t.PUBLISHED = ' || pi_published;
    END IF;
  
            -- Đếm tổng bản ghi
            v_sql_count := 'SELECT COUNT(*) FROM tutorials t ' || v_where_clause;
            EXECUTE IMMEDIATE v_sql_count INTO po_total;

            -- Chuẩn bị câu SQL lấy dữ liệu
            v_sql_data := 'SELECT * FROM tutorials t ' || v_where_clause;
        
            IF pi_order_by_query_builder IS NULL OR TRIM(pi_order_by_query_builder) = '' THEN
                v_sql_data := v_sql_data || ' ORDER BY t.CREATEDDATE DESC ';
            ELSE
                v_sql_data := v_sql_data || ' ORDER BY ' || pi_order_by_query_builder;
            END IF;
        
            v_sql_data := v_sql_data || ' OFFSET ' || v_offset || ' ROWS FETCH NEXT ' || pi_size || ' ROWS ONLY';
         DBMS_OUTPUT.PUT_LINE(v_sql_data);
            -- Mở cursor động
            OPEN po_result FOR v_sql_data;

EXCEPTION
    WHEN OTHERS THEN
        po_total := -1;
        po_error_msg := 'Lỗi: ' || SQLERRM;
        LOG_ERROR('search_tutorials', po_error_msg); 
        DBMS_OUTPUT.PUT_LINE(po_error_msg);
END search_tutorials;
END PKG_TUTORIAL;

/
