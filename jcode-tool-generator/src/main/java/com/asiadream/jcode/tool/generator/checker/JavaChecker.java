package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.java.reader.JavaReader;
import com.asiadream.jcode.tool.java.source.JavaSource;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.spec.converter.ProjectItemConverter;
import com.asiadream.jcode.tool.spec.converter.ProjectItemType;

import java.io.IOException;

public class JavaChecker extends ProjectItemConverter {
    //
    private JavaReader javaReader;
    private JavaSourceChecker checker;
    private JavaSelfExtUseChecker extChecker;

    public JavaChecker(ProjectConfiguration sourceConfiguration, JavaSourceChecker checker, JavaSelfExtUseChecker extChecker, String itemNamePostfix) {
        //
        super(sourceConfiguration, null, ProjectItemType.Java, itemNamePostfix);
        this.javaReader = new JavaReader(sourceConfiguration);
        this.checker = checker;
        this.extChecker = extChecker;
    }
    
    @Override
    public String convert(String sourceFilePath) throws IOException {
        //
        JavaSource source = javaReader.read(sourceFilePath);
        checker.checkAndWarn(source);
        extChecker.checkAndWarn(source);
        return source.getClassName();
    }

}
