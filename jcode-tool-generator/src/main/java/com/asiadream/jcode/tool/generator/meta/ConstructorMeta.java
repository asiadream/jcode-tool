package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.ConstructorModel;
import com.asiadream.jcode.tool.generator.model.FieldModel;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.generator.model.ParameterModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstructorMeta {
    //
    private List<String> parameters;

    public ConstructorModel toConstructorModel(String simpleClassName, JavaModel javaModel) {
        //
        ConstructorModel constructorModel = new ConstructorModel(simpleClassName);
        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter -> {
            FieldModel fieldModel = javaModel.findFieldByName(parameter);
            constructorModel.addParameterModel(fieldModel);
        }));

        constructorModel.body(parameters -> {
            List<String> stmts = new ArrayList<>();
            for (ParameterModel param : parameters) {
                stmts.add("this." + param.getVarName() + " = " + param.getVarName() + ";");
            }
            return stmts;
        });

        return constructorModel;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
