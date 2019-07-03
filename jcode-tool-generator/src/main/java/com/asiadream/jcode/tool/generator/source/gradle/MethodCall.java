package com.asiadream.jcode.tool.generator.source.gradle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MethodCall implements GradleElement, Argument {
    //
    private String methodName;
    private List<Argument> arguments;
    private MethodCall laterCall;

    private boolean printBracket;

    private static final String BRACKET_OPEN = "(";
    private static final String BRACKET_CLOSE = ")";

    private MethodCall() {
        //
        this.arguments = new ArrayList<>();
    }

    public MethodCall(String methodName) {
        //
        this();
        this.methodName = methodName;
    }

    public MethodCall(String methodName, String stringArgument) {
        //
        this(methodName, stringArgument, null);
    }

    public MethodCall(String methodName, String stringArgument, MethodCall laterCall) {
        //
        this();
        this.methodName = methodName;
        this.arguments.add(new GroovyString(stringArgument));
        this.laterCall = laterCall;
    }

    public MethodCall(String methodName, Argument argument) {
        this();
        this.methodName = methodName;
        this.arguments.add(argument);
    }

    @Override
    public String print(int level) {
        //
        StringBuffer sb = new StringBuffer();
        sb.append(blank(level));
        sb.append(methodName);

        if (printBracket || !existArguments()) {
            sb.append(BRACKET_OPEN);
        } else {
            sb.append(" ");
        }

        this.arguments.forEach(a -> sb.append(a.print()));

        if (printBracket || !existArguments()) {
            sb.append(BRACKET_CLOSE);
        }

        Optional.ofNullable(laterCall).ifPresent(laterCall -> sb.append(" ").append(laterCall.print()));

        return sb.toString();
    }

    public boolean existArguments() {
        //
        return this.arguments != null && this.arguments.size() > 0;
    }

    public MethodCall setPrintBracket(boolean printBracket) {
        this.printBracket = printBracket;
        return this;
    }
}
