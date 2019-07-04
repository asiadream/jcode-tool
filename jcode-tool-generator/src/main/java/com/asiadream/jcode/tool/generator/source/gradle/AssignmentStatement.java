package com.asiadream.jcode.tool.generator.source.gradle;

public class AssignmentStatement implements GradleElement {
    //
    private GroovyVariable variable;
    private GroovyExpression expression;

    private static final String EQUAL_SIGN = "=";

    public AssignmentStatement(String varName, String value) {
        //
        this.variable = new GroovyVariable(varName);
        this.expression = new GroovyString(value);
    }

    public AssignmentStatement(GroovyVariable variable, String value) {
        //
        this.variable = variable;
        this.expression = new GroovyString(value);
    }

    public AssignmentStatement(String varName, GroovyVariable variableExpression) {
        //
        this.variable = new GroovyVariable(varName);
        this.expression = variableExpression;
    }

    @Override
    public String print(int level) {
        return blank(level) + variable.print() + " " + EQUAL_SIGN + " " + expression.print();
    }

    public GroovyVariable getVariable() {
        return variable;
    }

    public GroovyExpression getExpression() {
        return expression;
    }
}
