package com.asiadream.jcode.tool.generator.source.sql;

import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SqlStatementBuilder {
    //
    private static final Logger logger = LoggerFactory.getLogger(SqlStatementBuilder.class);

    public static List<SqlStatement> build(String text) {
        //
        if (StringUtil.isEmpty(text) || !text.contains(";")) {
            return null;
        }

        List<SqlStatement> statements =  Arrays.stream(text.split(";"))
                .map(s -> s.trim()
                        .replace("\n", "").replace("\r", "") // remove newline
                        .replaceAll(" +", " ")) // remove double space
                .map(SqlStatementBuilder::buildStatement)
                .collect(Collectors.toList());

        updateComments(statements);

        return statements;
    }

    public static <T extends SqlStatement> List<T> findStatements(List<SqlStatement> statements, Class<T> type) {
        //
        List<T> list = new ArrayList<>();
        statements.forEach(sqlStatement -> {
            if (sqlStatement.getClass() == type) {
                list.add((T)sqlStatement);
            }
        });
        return list;
    }

    private static void updateComments(List<SqlStatement> statements) {
        //
        List<CreateTableStatement> createTableStatements = findStatements(statements, CreateTableStatement.class);
        List<CommentStatement> commentStatements = findStatements(statements, CommentStatement.class);

        createTableStatements.forEach(createTableStatement -> {
            updateComments(createTableStatement, commentStatements);
        });
    }

    private static void updateComments(CreateTableStatement createTableStatement, List<CommentStatement> commentStatements) {
        //
        String schemaName = createTableStatement.getSchemaName();
        String tableName =createTableStatement.getTableName();

        CommentStatement tableComment = findTableComment(schemaName, tableName, commentStatements);
        Optional.ofNullable(tableComment).ifPresent(_tableComment -> createTableStatement.setComment(_tableComment.getComment()));

        createTableStatement.getFields().forEach(tableField -> updateComments(tableField, schemaName, tableName, commentStatements));
    }

    private static void updateComments(TableField tableField, String schemaName, String tableName, List<CommentStatement> commentStatements) {
        //
        CommentStatement fieldComment = findColumnComment(schemaName, tableName, tableField.getName(), commentStatements);
        Optional.ofNullable(fieldComment).ifPresent(_fieldComment -> tableField.setComment(_fieldComment.getComment()));
    }

    private static CommentStatement findTableComment(String schemaName, String tableName, List<CommentStatement> commentStatements) {
        //
        return commentStatements.stream()
                .filter(commentStatement -> CommentStatement.CommentType.Table == commentStatement.getCommentType()
                        && schemaName.equals(commentStatement.getSchemaName())
                        && tableName.equals(commentStatement.getTableName()))
                .findAny()
                .orElse(null);
    }

    private static CommentStatement findColumnComment(String schemaName, String tableName, String fieldName, List<CommentStatement> commentStatements) {
        //
        return commentStatements.stream()
                .filter(commentStatement -> CommentStatement.CommentType.Column == commentStatement.getCommentType()
                        && schemaName.equals(commentStatement.getSchemaName())
                        && tableName.equals(commentStatement.getTableName())
                        && fieldName.equals(commentStatement.getColumnName()))
                .findAny()
                .orElse(null);
    }

    private static SqlStatement buildStatement(String statementText) {
        //
        switch (StatementType.statementType(statementText)) {
            case CreateTable:
                return new CreateTableStatement(statementText);
            case CommentOnTable:
                return new CommentStatement(CommentStatement.CommentType.Table, statementText);
            case CommentOnColumn:
                return new CommentStatement(CommentStatement.CommentType.Column, statementText);
            case Etc:
                return new EtcStatement(statementText);
            default:
                throw new IllegalStateException("Unexpected value: " + StatementType.statementType(statementText));
        }
    }
}
