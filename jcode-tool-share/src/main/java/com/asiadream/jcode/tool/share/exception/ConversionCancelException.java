package com.asiadream.jcode.tool.share.exception;

public class ConversionCancelException extends ToolException {
    public ConversionCancelException(String message) {
        super(message);
    }

    public ConversionCancelException(String message, Throwable t) {
        super(message, t);
    }

    public ConversionCancelException(Throwable t) {
        super(t);
    }
}
