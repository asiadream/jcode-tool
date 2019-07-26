package com.asiadream.jcode.tool.share.exception;

public class ToolException extends RuntimeException {
    //
    public ToolException(String message) {
        super(message);
    }

    public ToolException(String message, Throwable t) {
        super(message, t);
    }

    public ToolException(Throwable t) {
        super(t);
    }

}
