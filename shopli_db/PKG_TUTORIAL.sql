--------------------------------------------------------
--  DDL for Package PKG_TUTORIAL
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "SHOPLI"."PKG_TUTORIAL" AS

    PROCEDURE search_tutorials (
       pi_keyword             IN VARCHAR2,
    pi_title               IN VARCHAR2,
    pi_description         IN VARCHAR2,
    pi_published           IN NUMBER,
    pi_order_by_query_builder            IN VARCHAR2,
    pi_page                IN NUMBER,
    pi_size                IN NUMBER,
     po_result              OUT PKG_COMMON.ref_cursor,
    po_total               OUT NUMBER,
    po_error_msg           OUT VARCHAR2
    );
END PKG_TUTORIAL;

/
