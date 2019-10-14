package com.asiadream.jcode.tool.generator.converter;

import com.asiadream.jcode.tool.java.converter.JavaConverter;
import com.asiadream.jcode.tool.java.source.JavaSource;
import com.asiadream.jcode.tool.share.rule.PackageRule;
import com.asiadream.jcode.tool.share.util.file.PathUtil;
import com.asiadream.jcode.tool.spec.converter.ProjectItemConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DtoManagingJavaConverter extends ProjectItemConverter {
    //
    private static Logger logger = LoggerFactory.getLogger(DtoManagingJavaConverter.class);
    
    private PackageRule packageRule;
    private JavaConverter serviceJavaConverter;
    private JavaConverter stubJavaConverter;

    public DtoManagingJavaConverter(PackageRule packageRule, JavaConverter serviceJavaConverter, JavaConverter stubJavaConverter) {
        //
        super(serviceJavaConverter.getSourceConfiguration(), serviceJavaConverter.getProjectItemType());

        this.packageRule = packageRule;
        this.serviceJavaConverter = serviceJavaConverter;
        this.stubJavaConverter = stubJavaConverter;
    }

    @Override
    public String convert(String dtoSourceFileName) {
        //
        try {
            String dtoClassName = PathUtil.toClassName(dtoSourceFileName);
            if (packageRule.containsChangeImport(dtoClassName)) {
                return stubJavaConverter
                        .customCodeHandle(this::makeDtoCustomCode)
                        .convert(dtoSourceFileName);
            } else {
                return serviceJavaConverter
                        .customCodeHandle(this::makeDtoCustomCode)
                        .convert(dtoSourceFileName);
            }
        } catch (IOException e) {
            logger.error("Can't convert dto --> {}, {}", dtoSourceFileName, e.getMessage());
            return null;
        }
    }

    private void makeDtoCustomCode(JavaSource javaSource) {
        //
        boolean hasVOProperty = javaSource.hasProperty("VO", "TO");
        if (hasVOProperty) {
            if ("AMISDefaultVO".equals(javaSource.getExtendedType())) {
                //javaSource.setExtendedType("AbstractCompositeDTO", "kr.amc.amil.message.dto");
                javaSource.setExtendedType("AbstractDTO", "kr.amc.amil.message.dto");
            }
        } else {
            if ("AMISDefaultVO".equals(javaSource.getExtendedType())) {
                javaSource.setExtendedType("AbstractDTO", "kr.amc.amil.message.dto");
            }
        }

        // add annotation
        javaSource.addAnnotation("Getter", "lombok");
        javaSource.addAnnotation("Setter", "lombok");
        javaSource.addAnnotation("NoArgsConstructor", "lombok");
        //javaSource.addAnnotation("ToString", "lombok");

        // remove getter / setter / toString
        javaSource.removeGetterAndSetter();
        //javaSource.removeMethod("toString");

        // remove default constructor
        javaSource.removeNoArgsConstructor();

    }
}
