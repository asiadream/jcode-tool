package com.asiadream.jcode.tool.generator.source.sql;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SqlScriptTest {
    //
    private Gson gson = new Gson();

    @Test
    public void testGetStatements() {
        //
        SqlScript sqlScript = new SqlScript(makeScript());
        List<SqlStatement> statements = sqlScript.getStatements();
        statements.forEach(sqlStatement -> System.out.println(gson.toJson(sqlStatement)));
        Assert.assertEquals(3, statements.size());
    }

    @Test
    public void testGetStatementsByType() {
        //
        SqlScript sqlScript = new SqlScript(makeScript());
        List<CreateTableStatement> createTableStatements = sqlScript.getStatements(CreateTableStatement.class);
        createTableStatements.forEach(sqlStatement -> System.out.println(gson.toJson(sqlStatement)));
        Assert.assertEquals(1, createTableStatements.size());

        List<CommentStatement> commentStatements = sqlScript.getStatements(CommentStatement.class);
        commentStatements.forEach(sqlStatement -> System.out.println(gson.toJson(sqlStatement)));
        Assert.assertEquals(2, commentStatements.size());
    }

    @Test
    public void testComment() {
        //
        SqlScript sqlScript = new SqlScript(makeScript());
        List<CreateTableStatement> createTableStatements = sqlScript.getStatements(CreateTableStatement.class);
        Assert.assertEquals(1, createTableStatements.size());
        CreateTableStatement createTableStatement = createTableStatements.get(0);
        Assert.assertEquals("학적기본", createTableStatement.getComment());
        Assert.assertEquals("학번", createTableStatement.findField("STDNO").getComment());
    }

    private String makeScript() {
        //
        return "CREATE TABLE UNI.SREG200\n" +
                "(\n" +
                "  STDNO                VARCHAR2(12 BYTE)        NOT NULL,\n" +
                "  STD_PERSHIS_NO       VARCHAR2(12 BYTE)        NOT NULL,\n" +
                "  FLD_UNIV_CD          VARCHAR2(10 BYTE)        NOT NULL,\n" +
                "  CAMP_CD              VARCHAR2(10 BYTE)        NOT NULL,\n" +
                "  CORS_GBN             VARCHAR2(8 BYTE)         NOT NULL,\n" +
                "  CORS_DFSEQ           VARCHAR2(10 BYTE)        NOT NULL,\n" +
                "  HG_CD                VARCHAR2(10 BYTE),\n" +
                "  MJ_OCPT_CD           VARCHAR2(10 BYTE),\n" +
                "  DAYNGT_GBN           VARCHAR2(8 BYTE),\n" +
                "  HG_NM                VARCHAR2(200 BYTE)       NOT NULL,\n" +
                "  MJ_OCPT_NM           VARCHAR2(200 BYTE),\n" +
                "  SMGRP_CLASS          VARCHAR2(5 BYTE),\n" +
                "  CLASS_STD_NO         NUMBER(20),\n" +
                "  TLSN_DAYNGT_GBN      VARCHAR2(8 BYTE),\n" +
                "  TUTOR_NO             VARCHAR2(10 BYTE),\n" +
                "  SCHYY                VARCHAR2(1 BYTE),\n" +
                "  TLSN_SCHYY           VARCHAR2(1 BYTE),\n" +
                "  SCHREG_ST_GBN        VARCHAR2(8 BYTE)         NOT NULL,\n" +
                "  SCHREG_CHG_GBN       VARCHAR2(8 BYTE),\n" +
                "  SCHREG_CHG_DETA_GBN  VARCHAR2(8 BYTE),\n" +
                "  SCHREG_CHG_DT        DATE,\n" +
                "  ENTRSCH_DT           DATE,\n" +
                "  ENTRSCH_GBN          VARCHAR2(8 BYTE)         NOT NULL,\n" +
                "  PRMT_YY              VARCHAR2(4 BYTE),\n" +
                "  GRDT_GBN             VARCHAR2(8 BYTE),\n" +
                "  GRDT_SSCOR           NUMBER(6,2),\n" +
                "  GRDT_SCHYY           VARCHAR2(1 BYTE),\n" +
                "  EARLY_GRDT_REQ_YN    VARCHAR2(1 BYTE)         DEFAULT '0'                   NOT NULL,\n" +
                "  EARLY_GRDT_REQ_DT    DATE,\n" +
                "  ISSU_CTRL_YN         VARCHAR2(1 BYTE)         DEFAULT '0'                   NOT NULL,\n" +
                "  ISSU_CTRL_RESN       VARCHAR2(4000 BYTE),\n" +
                "  SCHCR_GBN            VARCHAR2(8 BYTE),\n" +
                "  RMK                  VARCHAR2(4000 BYTE),\n" +
                "  HSCH_MANDSTD_YN      VARCHAR2(1 BYTE)         NOT NULL,\n" +
                "  HRD_CONN_SCHREG_GBN  VARCHAR2(8 BYTE),\n" +
                "  MOTHER_HG_TLSN_YN    VARCHAR2(1 BYTE)         DEFAULT '0'                   NOT NULL,\n" +
                "  CORS_EVAL_STD_YN     VARCHAR2(1 BYTE)         DEFAULT '0'                   NOT NULL,\n" +
                "  SCHCR_INQ_OBJ_YN     VARCHAR2(1 BYTE)         DEFAULT '0'                   NOT NULL,\n" +
                "  NON_DEGR_TR_CNT      NUMBER(6)                DEFAULT 0,\n" +
                "  INPT_ID              VARCHAR2(30 BYTE),\n" +
                "  INPT_DTTM            TIMESTAMP(6),\n" +
                "  INPT_IP              VARCHAR2(50 BYTE),\n" +
                "  INPT_MENU_ID         VARCHAR2(30 BYTE),\n" +
                "  MOD_ID               VARCHAR2(30 BYTE),\n" +
                "  MOD_DTTM             TIMESTAMP(6),\n" +
                "  MOD_IP               VARCHAR2(50 BYTE),\n" +
                "  MOD_MENU_ID          VARCHAR2(30 BYTE),\n" +
                "  NEIS_NO              VARCHAR2(30 BYTE)\n" +
                ")\n" +
                "TABLESPACE TS_UNI_DAT\n" +
                "PCTUSED    0\n" +
                "PCTFREE    10\n" +
                "INITRANS   1\n" +
                "MAXTRANS   255\n" +
                "STORAGE    (\n" +
                "            INITIAL          64K\n" +
                "            NEXT             1M\n" +
                "            MINEXTENTS       1\n" +
                "            MAXEXTENTS       UNLIMITED\n" +
                "            PCTINCREASE      0\n" +
                "            BUFFER_POOL      DEFAULT\n" +
                "           )\n" +
                "LOGGING\n" +
                "NOCOMPRESS\n" +
                "NOCACHE\n" +
                "NOPARALLEL\n" +
                "MONITORING;\n" +
                "\n" +
                "COMMENT ON TABLE UNI.SREG200 IS '학적기본';\n" +
                "\n" +
                "COMMENT ON COLUMN UNI.SREG200.STDNO IS '학번';";
    }

}