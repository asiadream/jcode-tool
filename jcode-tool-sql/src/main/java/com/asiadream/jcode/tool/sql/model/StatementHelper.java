package com.asiadream.jcode.tool.sql.model;

import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatementHelper {
    //
    private static final Logger logger = LoggerFactory.getLogger(StatementHelper.class);

    // -----------------------------------------------------------------------------------------------------------------
    // CREATE TABLE
    // -----------------------------------------------------------------------------------------------------------------
    public static String getSchemaNameFromCreateStatement(String statement) {
        //
        String schemaTableName = getSchemaTableNameFromCreateStatement(statement);
        int dotIndex = schemaTableName.indexOf(".");
        String schemaName = null;

        if (dotIndex > 0) {
            schemaName = schemaTableName.substring(0, dotIndex);
        }

        logger.debug("schemaName: {}", schemaName);
        return schemaName;
    }

    public static String getTableNameFromCreateStatement(String statement) {
        //
        String schemaTableName = getSchemaTableNameFromCreateStatement(statement);
        int dotIndex = schemaTableName.indexOf(".");
        String tableName = schemaTableName;

        if (dotIndex > 0) {
            tableName = schemaTableName.substring(dotIndex + 1);
        }
        logger.debug("tableName: {}", tableName);
        return tableName;
    }

    public static String getSchemaTableNameFromCreateStatement(String statement) {
        //
        checkCreateStatement(statement);
        String removed = StringUtil.removePrefix(statement, StatementType.CreateTable.getCommand() + " ");
        String schemaTableName = removed.substring(0, removed.indexOf("(")).trim();
        logger.debug("schemaTableName: {}", schemaTableName);
        return schemaTableName;
    }

    public static List<TableField> getTableFieldsFromCreateStatement(String statement) {
        //
        checkCreateStatement(statement);
        String fieldsBlock = stripOff(statement, '(', ')');

        return truncatesByField(fieldsBlock).stream()
                .map(TableField::new)
                .collect(Collectors.toList());
    }

    private static void checkCreateStatement(String statement) {
        //
        if (!statement.startsWith(StatementType.CreateTable.getCommand())) {
            throw new IllegalArgumentException("It's Not 'CREATE TABLE' statement.");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // COMMENT ON COLUMN
    // -----------------------------------------------------------------------------------------------------------------
    public static String getSchemaNameFromCommentOnColumnStatement(String statement) {
        //
        String schemaTableColumnName = getSchemaTableColumnNameFromCommentOnColumnStatement(statement);
        int dotCount = StringUtil.countMatches(schemaTableColumnName, ".");
        if (dotCount == 2) {
            return schemaTableColumnName.split("\\.")[0];
        }
        return null;
    }

    public static String getTableNameFromCommentOnColumnStatement(String statement) {
        //
        String schemaTableColumnName = getSchemaTableColumnNameFromCommentOnColumnStatement(statement);
        int dotCount = StringUtil.countMatches(schemaTableColumnName, ".");
        String[] splited = schemaTableColumnName.split("\\.");
        if (dotCount == 2) {
            return splited[1];
        } else if (dotCount == 1) {
            return splited[0];
        }
        return null;
    }

    public static String getColumnNameFromCommentOnColumnStatement(String statement) {
        //
        String schemaTableColumnName = getSchemaTableColumnNameFromCommentOnColumnStatement(statement);
        int dotCount = StringUtil.countMatches(schemaTableColumnName, ".");
        String[] splited = schemaTableColumnName.split("\\.");
        if (dotCount == 2) {
            return splited[2];
        } else if (dotCount == 1) {
            return splited[1];
        }
        return splited[0];
    }

    public static String getCommentFromCommentOnColumnStatement(String statement) {
        //
        checkCommentOnColumnStatement(statement);
        String quotedComment = statement.substring(statement.indexOf(" IS ") + 4);
        return stripOff(quotedComment, '\'');
    }

    private static String getSchemaTableColumnNameFromCommentOnColumnStatement(String statement) {
        //
        checkCommentOnColumnStatement(statement);
        String removed = StringUtil.removePrefix(statement, StatementType.CommentOnColumn.getCommand() + " ");
        String schemaTableColumnName = removed.substring(0, removed.indexOf(" IS ")).trim();
        logger.debug("schemaTableColumnName: {}", schemaTableColumnName);
        return schemaTableColumnName;
    }

    private static void checkCommentOnColumnStatement(String statement) {
        //
        if (!statement.startsWith(StatementType.CommentOnColumn.getCommand())) {
            throw new IllegalArgumentException("It's Not 'COMMENT ON COLUMN' statement.");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // COMMENT ON TABLE
    // -----------------------------------------------------------------------------------------------------------------
    public static String getSchemaNameFromCommentOnTableStatement(String statement) {
        //
        String schemaTableName = getSchemaTableNameFromCommentOnTableStatement(statement);
        int dotCount = StringUtil.countMatches(schemaTableName, ".");
        if (dotCount == 1) {
            return schemaTableName.split("\\.")[0];
        }
        return null;
    }

    public static String getTableNameFromCommentOnTableStatement(String statement) {
        //
        String schemaTableName = getSchemaTableNameFromCommentOnTableStatement(statement);
        int dotCount = StringUtil.countMatches(schemaTableName, ".");
        String[] splited = schemaTableName.split("\\.");
        if (dotCount == 1) {
            return splited[1];
        } else if (dotCount == 0) {
            return splited[0];
        }
        return null;
    }

    public static String getCommentFromCommentOnTableStatement(String statement) {
        //
        checkCommentOnTableStatement(statement);
        String quotedComment = statement.substring(statement.indexOf(" IS ") + 4);
        return stripOff(quotedComment, '\'');
    }

    private static String getSchemaTableNameFromCommentOnTableStatement(String statement) {
        //
        checkCommentOnTableStatement(statement);
        String removed = StringUtil.removePrefix(statement, StatementType.CommentOnTable.getCommand() + " ");
        String schemaTableName = removed.substring(0, removed.indexOf(" IS ")).trim();
        logger.debug("schemaTableName: {}", schemaTableName);
        return schemaTableName;
    }

    private static void checkCommentOnTableStatement(String statement) {
        //
        if (!statement.startsWith(StatementType.CommentOnTable.getCommand())) {
            throw new IllegalArgumentException("It's Not 'COMMENT ON TABLE' statement.");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Utils
    // -----------------------------------------------------------------------------------------------------------------

    // STDNO VARCHAR2(12 BYTE) NOT NULL, STD_PERSHIS_NO VARCHAR2(12 BYTE) NOT NULL, ...
    // -->
    // STDNO VARCHAR2(12 BYTE) NOT NULL
    // STD_PERSHIS_NO VARCHAR2(12 BYTE) NOT NULL
    private static List<String> truncatesByField(String fieldsBlock) {
        //
        int level = 0;
        StringBuilder sb = new StringBuilder();
        List<String> fields = new ArrayList<>();
        for (char c : fieldsBlock.toCharArray()) {
            if (c == '(') {
                level++;
            } else if (c == ')') {
                level--;
            }

            if (level == 0 && c == ',') {
                fields.add(sb.toString().trim());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }

        return fields;
    }

    // STDNO VARCHAR2(12 BYTE) NOT NULL
    // -->
    // [STDNO], [VARCHAR2(12 BYTE)], [NOT], [NULL]
    public static List<String> truncatesByFieldElement(String fieldStatement) {
        //
        int level = 0;
        StringBuilder sb = new StringBuilder();
        List<String> fieldElements = new ArrayList<>();
        for (char c : fieldStatement.toCharArray()) {
            if (c == '(') {
                level++;
            } else if (c == ')') {
                level--;
            }

            if (level == 0 && c == ' ') {
                fieldElements.add(sb.toString().trim());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }

        if (sb.length() > 0) {
            fieldElements.add(sb.toString().trim());
        }
        return fieldElements;
    }

    private static String stripOff(String statement, char marker) {
        //
        int count = StringUtil.countMatches(statement, marker);
        if (count == 2) {
            return statement.split(String.valueOf(marker))[1];
        }
        return null;
    }

    // CREATE TABLE UNI.SREG200( STDNO VARCHAR2(12 BYTE) NOT NULL, ...)
    // -->
    // STDNO VARCHAR2(12 BYTE) NOT NULL, ...
    private static String stripOff(String statement, char open, char close) {
        //
        if (open == close) {
            throw new IllegalArgumentException("Open and Close markers must be different. --> [" + open + "]");
        }
        if (statement == null || !statement.contains(String.valueOf(open)) || !statement.contains(String.valueOf(close))) {
            return null;
        }

        int level = 0;
        int openIndex = -1, closeIndex = -1;
        char[] charArray = statement.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            int levelChange = 0;
            if (c == open) {
                levelChange = 1;
            } else if (c == close) {
                levelChange = -1;
            }

            // Outermost bracket open : level 0 -> 1
            if (level == 0 && levelChange == 1) {
                // opened
                openIndex = i;
                logger.trace("# open --> " + i);
            }
            // Outermost bracket close : level 1 -> 0
            if (level == 1 && levelChange == -1) {
                // closed
                closeIndex = i;
                logger.trace("# close --> " + i);

                // Stop at the first parenthesis.
                break;
            }

            level = level + levelChange;
        }

        String stripOffed = statement.substring(openIndex + 1, closeIndex).trim();
        logger.trace("stripOffed: " + stripOffed);

        return stripOffed;
    }





    public static boolean getNotNullFromFieldElements(List<String> fieldElements) {
        //
        int index = index("NOT", fieldElements);
        return index > 0 && "NULL".equals(fieldElements.get(index + 1));
    }

    private static int index(String element, List<String> elements) {
        //
        return elements.indexOf(element);
    }

    public static String getDefaultValueFromFieldElements(List<String> fieldElements) {
        //
        int index = index("DEFAULT", fieldElements);
        if (index > 0) {
            return fieldElements.get(index + 1);
        }
        return null;
    }

    public static String getTypeFromFieldElements(List<String> fieldElements) {
        //
        String typeSize = fieldElements.get(1);
        int bracketIndex = typeSize.indexOf("(");
        if (bracketIndex >= 0) {
            return typeSize.substring(0, bracketIndex);
        }
        return typeSize;
    }

    public static String getNameFromFieldElements(List<String> fieldElements) {
        //
        return fieldElements.get(0);
    }

    public static int getSizeFromFieldElements(List<String> fieldElements) {
        String typeSize = fieldElements.get(1);
        String sizeStatement = stripOff(typeSize, '(', ')');
        return getNumberFromSizeStatement(sizeStatement);
    }

    // 4 BYTE -> 4
    // 6,2 -> 6
    private static int getNumberFromSizeStatement(String sizeStatement) {
        //
        if (StringUtil.isEmpty(sizeStatement)) {
            return 0;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : sizeStatement.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            } else {
                break;
            }
        }
        return Integer.parseInt(sb.toString());
    }
}
