package com.asiadream.jcode.tool.generator.source.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SqlStatement {
    //
    private static final Logger logger = LoggerFactory.getLogger(SqlStatement.class);

    private String text;

    public SqlStatement(String text) {
        this.text = text;
        debug();
    }

    public String getText() {
        return text;
    }

    private void debug() {
        if (logger.isDebugEnabled()) {
            logger.debug("{}[{}]", getClass().getSimpleName(), text);
        }
    }
}
