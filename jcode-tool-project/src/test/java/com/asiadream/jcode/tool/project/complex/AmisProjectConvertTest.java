package com.asiadream.jcode.tool.project.complex;

import com.asiadream.jcode.tool.generator.converter.JavaAbstractParam;
import com.asiadream.jcode.tool.share.rule.NameRule;
import com.asiadream.jcode.tool.share.rule.PackageRule;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

public class AmisProjectConvertTest extends BaseFileTest {
    //
    private static final String SOURCE_PROJECT_HOME = "D:\\Projects\\AMIS\\workspace\\amis3";
    private static final String RESOURCE_PROJECT_HOME = "D:\\Projects\\AMIS\\workspace\\amis3-resource";
    private final String TARGET_WORKSPACE = super.testDirName;
    
    //@Test
    public void testConvert() throws Exception {
        //
        ConvertParameter parameter = new ConvertParameter();
        parameter.setNewProjectName0("amis");
        parameter.setNewProjectName1("mc");
        parameter.setNewProjectName2("order");
        parameter.setNewBasePackage("kr.amc.amis");
        
        parameter.setSourcePackage("amis3.mc.oo");
        parameter.setSourceDtoPackage("amis3.vo.mc.oo");
        parameter.setSourceProjectHomePath(SOURCE_PROJECT_HOME);
        
        parameter.setSourceSqlMapProjectHomePath(RESOURCE_PROJECT_HOME);
        parameter.setSourceSqlMapResourceFolder("sqlmap/query");
        parameter.setSourceSqlMapPackage("mc.oo");
        
        parameter.setTargetWorkspace(TARGET_WORKSPACE);
        
        parameter.addDependency("name1", "name2");
        
        JavaAbstractParam javaAbstractParam = new JavaAbstractParam();
        javaAbstractParam.setTargetFilePostfix("ExtService.java");
        javaAbstractParam.setSourcePackage("amis3.mc.oo");
        javaAbstractParam.setSourceDtoPackage("amis3.vo.mc.oo");
        javaAbstractParam.setNewProjectName1("mc");
        javaAbstractParam.setNewProjectName2("order");

        PackageRule javaConvertPackageRule = PackageRule.newInstance()
                .add(0, "amis3"     , "kr.amc.amis")
                .add(2, "oo"        , "order")
                .add(4, "controller", "rest")
                .add(4, "dao"       , "store")
                .add(4, "service"   , "service")
                .add(1, "vo"        , 4)
                .add(4, "vo"        , "entity")
                // this rule would excute after name changed.
                .add("ExtServiceLogic"  , "ext.logic")
                .add("ExtService"      , "ext.spec")
                .addChangeImport("amis3.fw.core.util.DateUtil", "kr.amc.amil.util.date.DateUtil")
                .addChangeImport("amis3.fw.core.util.ContextUtil", "kr.amc.amil.util.context.ContextUtil")
                .addChangeImport("amis3.fw.core.util.MessageUtil", "kr.amc.amil.message.MessageUtil")
                .addChangeImport("amis3.fw.core.binding.message.DateTimeFormat", "kr.amc.amil.util.date.DateTimeFormat")
                .addChangeImport("amis3.fw.core.exception.AMISBizException", "kr.amc.amil.util.exception.AMISBizException")
                .addRemoveImport("amis3.fw.core.vo.AMISDefaultVO")
                .addRemoveImport("amis3.fw.core.binding.annotation.Telegram")
                .addRemoveImport("amis3.fw.core.binding.message.TelegramType")
                .addRemoveImport("amis3.fw.core.binding.message.AMCData")
                .addRemoveImport("amis3.fw.core.binding.message.AMCDataFactory")
                .addRemoveImport("amis3.fw.core.binding.annotation.PreDataParam")
                .addRemoveImport("amis3.fw.core.persistence.AMISCommonDao");
        
        PackageRule packageRuleForCheckStubDto = PackageRule.copyOf(javaConvertPackageRule)
                .set(4, "vo", "ext.spec.sdo", 1);
        
        NameRule javaConvertNameRule = NameRule.newInstance()
                .add("VO", "DTO")
                .addExceptionPattern("springframework");
        
        PackageRule namespaceRule = PackageRule.newInstance()
                .setPrefix("kr.amc.amis")
                .add(1, "oo", "order")
                .setPostfix("store.mapper");
        
        ComplexProjectConverter converter = new ComplexProjectConverter(parameter, javaAbstractParam, 
                javaConvertNameRule, javaConvertPackageRule, packageRuleForCheckStubDto, namespaceRule);
        converter.convert();
    }
}
