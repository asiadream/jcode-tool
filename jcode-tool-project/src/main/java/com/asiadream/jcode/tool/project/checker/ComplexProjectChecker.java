package com.asiadream.jcode.tool.project.checker;

import com.asiadream.jcode.tool.generator.checker.*;
import com.asiadream.jcode.tool.generator.converter.JavaAbstractParam;
import com.asiadream.jcode.tool.generator.converter.MultiItemPackageConverter;
import com.asiadream.jcode.tool.generator.converter.PackageConverter;
import com.asiadream.jcode.tool.project.complex.AmisConstants;
import com.asiadream.jcode.tool.project.complex.ConvertParameter;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.config.SourceFolders;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

public class ComplexProjectChecker {
    //
    private static final Logger logger  = LoggerFactory.getLogger(ComplexProjectChecker.class);
    
    private ConvertParameter param;
    private JavaAbstractParam javaAbstractParam;
    
    public ComplexProjectChecker(ConvertParameter param, JavaAbstractParam javaAbstractParam) {
        //
        this.param = param;
        this.javaAbstractParam = javaAbstractParam;
    }

    public void check() throws IOException {
        //
        ProjectConfiguration sourceConfig = new ProjectConfiguration(ConfigurationType.Source, param.getSourceProjectHomePath(), param.isLexicalPreserving());
        
        // Check VO, TO and Self Ext call.
        JavaSelfExtUseChecker extChecker = new JavaSelfExtUseChecker();
        JavaChecker extServiceChecker = new JavaChecker(sourceConfig, new JavaSourceChecker(new String[]{"ExtService"},"TO"), extChecker, javaAbstractParam.getTargetFilePostfix());
        JavaChecker serviceChecker = new JavaChecker(sourceConfig, new JavaSourceChecker(new String[]{"Service", "Dao"},"VO"), extChecker, null);
        new MultiItemPackageConverter()
            .add(extServiceChecker)
            .add(serviceChecker)
            .convert(param.getSourcePackage());
        
        // Check java Duplication
        JavaDuplicationChecker dupChecker = new JavaDuplicationChecker(sourceConfig);
        new PackageConverter(dupChecker)
            .convert(param.getSourcePackage());
        dupChecker.show();
        
        // Check xml Duplication
        String srcMainResources = param.getSourceSqlMapResourceFolder().replaceAll("/", Matcher.quoteReplacement(File.separator));
        SourceFolders sourceFolders = SourceFolders.newSourceFolders(null, srcMainResources);
        ProjectConfiguration sqlMapSourceConfig = new ProjectConfiguration(ConfigurationType.Source, param.getSourceSqlMapProjectHomePath(), sourceFolders, false);
        ResourceDuplicationChecker resDupChecker = new ResourceDuplicationChecker(sqlMapSourceConfig);
        new PackageConverter(resDupChecker)
            .convert(param.getSourceSqlMapPackage());
        resDupChecker.show();
        
        // Check xml parse(SAX Parser)
        //SAXParseChecker saxChecker = new SAXParseChecker(sqlMapSourceConfig);
        DomParseChecker domChecker = new DomParseChecker(sqlMapSourceConfig);
        new PackageConverter(domChecker)
            .convert(param.getSourceSqlMapPackage());
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    static String SOURCE_PROJECT_HOME = "C:\\AMIS3_DEV\\server\\workspace\\amis3";
    static String RESOURCE_PROJECT_HOME = "C:\\AMIS3_DEV\\server\\workspace\\amis3-resource";
    static String targetWorkspace = "./target/tested-files";
    
    public static void main(String[] args) throws Exception {
        //
        //initFileEnv();
        checkAll(AmisConstants.projectNames, "");
        checkAll(AmisConstants.projectNamesMdm, "-mdm");
        checkAll(AmisConstants.projectNamesEtc, "-etc");
    }
    
    public static void initFileEnv() throws Exception {
        //
        File testBundleDir = FileUtils.getFile("./log");
        if (testBundleDir.exists()) {
            FileUtils.forceDelete(testBundleDir);
        }
        FileUtils.forceMkdir(testBundleDir);
    }
    
    private static void checkAll(String[][] projectNames, String projectHomeSuffix) throws Exception {
        //
        ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2
        );
        
        for (int i = 0; i < projectNames.length; i++) {
            final String projectName1 = projectNames[i][0].toLowerCase();
            final String projectName2 = projectNames[i][1].toLowerCase();
            final String newPackageName = projectNames[i][2].toLowerCase();
            
            executorService.submit(() -> {
                long startMillis = System.currentTimeMillis();
                logger.info("{}.{} start converting", projectName1, projectName2);
                try {
                    check2Depth(projectName1, projectName2, newPackageName, projectHomeSuffix);
                } catch (IOException e) {
                    logger.info(e.getMessage(), e);
                }
                logger.info("{}.{} converted ({} ms)", projectName1, projectName2, (System.currentTimeMillis() - startMillis));
            });
        }
        executorService.shutdown();
    }
    
    private static void check2Depth(String projectName1, String projectName2, String newPackageName, String projectHomeSuffix) throws IOException {
        //
        ConvertParameter parameter = new ConvertParameter();
        parameter.setNewProjectName0("amis");
        parameter.setNewProjectName1(projectName1);
        parameter.setNewProjectName2(newPackageName);
        parameter.setNewBasePackage("kr.amc.amis");
        
        parameter.setSourcePackage(String.format("amis3.%s.%s", projectName1, projectName2));
        parameter.setSourceDtoPackage(String.format("amis3.vo.%s.%s", projectName1, projectName2));
        parameter.setSourceProjectHomePath(SOURCE_PROJECT_HOME + projectHomeSuffix);
        
        parameter.setSourceSqlMapProjectHomePath(RESOURCE_PROJECT_HOME + projectHomeSuffix);
        parameter.setSourceSqlMapResourceFolder("sqlmap/query");
        parameter.setSourceSqlMapPackage(String.format("%s.%s", projectName1, projectName2));
        
        parameter.setTargetWorkspace(targetWorkspace);
        
        JavaAbstractParam javaAbstractParam = new JavaAbstractParam();
        javaAbstractParam.setTargetFilePostfix("ExtService.java");
        javaAbstractParam.setSourcePackage(String.format("amis3.%s.%s", projectName1, projectName2));
        javaAbstractParam.setSourceDtoPackage(String.format("amis3.vo.%s.%s", projectName1, projectName2));
        javaAbstractParam.setNewProjectName1(projectName1);
        javaAbstractParam.setNewProjectName2(newPackageName);
  
        ComplexProjectChecker checker = new ComplexProjectChecker(parameter, javaAbstractParam);
        checker.check();
    }
    
}
