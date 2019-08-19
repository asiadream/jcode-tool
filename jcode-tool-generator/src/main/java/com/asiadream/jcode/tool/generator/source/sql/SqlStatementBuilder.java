package com.asiadream.jcode.tool.generator.source.sql;

import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SqlStatementBuilder {
    //
    private static final Logger logger = LoggerFactory.getLogger(SqlStatementBuilder.class);

    public static List<SqlStatement> build(String text) {
        //
        if (StringUtil.isEmpty(text) || !text.contains(";")) {
            return null;
        }

        return Arrays.stream(text.split(";"))
                .map(s -> s.trim()
                        .replace("\n", "").replace("\r", "") // remove newline
                        .replaceAll(" +", " ")) // remove double space
                .map(SqlStatementBuilder::buildStatement)
                .collect(Collectors.toList());
    }

    private static SqlStatement buildStatement(String statementText) {
        //
        switch (statementType(statementText)) {
            case CreateTable:
                logger.debug(statementText);
                return new CreateTableStatement(statementText);
            case Etc:
                return new EtcStatement(statementText);
            default:
            throw new IllegalStateException("Unexpected value: " + statementType(statementText));
        }
    }

    private static StatementType statementType(String text) {
        //
        if (text.startsWith(StatementType.CreateTable.getCommand())) {
            return StatementType.CreateTable;
        }
        return StatementType.Etc;
    }
}
