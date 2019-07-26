package com.asiadream.jcode.tool.share.exception;

public class ClassFileNotFoundException extends ToolException {
    //
    public ClassFileNotFoundException(String message) {
        super(message);
    }

    public ClassFileNotFoundException(String message, Throwable t) {
        super(message, t);
    }

    public ClassFileNotFoundException(Throwable t) {
        super(t);
    }
}
