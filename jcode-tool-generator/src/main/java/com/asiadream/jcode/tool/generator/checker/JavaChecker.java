package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.generator.converter.ProjectItemConverter;
import com.asiadream.jcode.tool.generator.converter.ProjectItemType;
import com.asiadream.jcode.tool.generator.reader.JavaReader;
import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

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
