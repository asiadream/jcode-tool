package com.asiadream.jcode.tool.project.complex;

import com.asiadream.jcode.tool.generator.converter.JavaAbstractParam;
import com.asiadream.jcode.tool.share.rule.NameRule;
import com.asiadream.jcode.tool.share.rule.PackageRule;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AmisAllProjectConverter {
    //
	private static final Logger logger  = LoggerFactory.getLogger(AmisAllProjectConverter.class);
	
	static final String testDirName = "./target/tested-files";
	//static final String logDirName = "./log";
	
	public static void main(String[] args) throws Exception {
		//
		AmisAllProjectConverter amisAllProjectConverter = new AmisAllProjectConverter();
		//amisAllProjectConverter.initFileEnv(logDirName);
		amisAllProjectConverter.initFileEnv(testDirName);
		amisAllProjectConverter.convert();
	}
	
    public void initFileEnv(String dirName) throws Exception {
        //
        File testBundleDir = FileUtils.getFile(dirName);
        if (testBundleDir.exists()) {
            FileUtils.forceDelete(testBundleDir);
        }
        FileUtils.forceMkdir(testBundleDir);
    }

    public void convert() throws Exception {
        //
    	ExecutorService executorService= Executors.newFixedThreadPool(
    		Runtime.getRuntime().availableProcessors() * 2
        );
    	
    	// main
    	for (int i = 0; i < AmisConstants.projectNames.length; i++) {
    		String projectName1 = AmisConstants.projectNames[i][0].toLowerCase();
			String projectName2 = AmisConstants.projectNames[i][1].toLowerCase();
			String newPackageName = AmisConstants.projectNames[i][2].toLowerCase();
			
	    	Runnable task = new OneProjectConverter(projectName1, projectName2, newPackageName, "");
	        
	        executorService.submit(task);
	        logger.info("{}.{} task submitted", projectName1, projectName2);
    	}
    	
    	// mdm
    	for (int i = 0; i < AmisConstants.projectNamesMdm.length; i++) {
            String projectName1 = AmisConstants.projectNamesMdm[i][0].toLowerCase();
            String projectName2 = AmisConstants.projectNamesMdm[i][1].toLowerCase();
            String newPackageName = AmisConstants.projectNamesMdm[i][2].toLowerCase();
            
            Runnable task = new OneProjectConverter(projectName1, projectName2, newPackageName, "-mdm");
            
            executorService.submit(task);
            logger.info("{}.{} task submitted", projectName1, projectName2);
        }
    	
    	// etc
        for (int i = 0; i < AmisConstants.projectNamesEtc.length; i++) {
            String projectName1 = AmisConstants.projectNamesEtc[i][0].toLowerCase();
            String projectName2 = AmisConstants.projectNamesEtc[i][1].toLowerCase();
            String newPackageName = AmisConstants.projectNamesEtc[i][2].toLowerCase();
            
            Runnable task = new OneProjectConverter(projectName1, projectName2, newPackageName, "-etc");
            
            executorService.submit(task);
            logger.info("{}.{} task submitted", projectName1, projectName2);
        }

    	executorService.shutdown();
    }
}

class OneProjectConverter implements Runnable {
	//
	private static final Logger logger  = LoggerFactory.getLogger(OneProjectConverter.class);
	
	String SOURCE_PROJECT_HOME = "C:\\AMIS3_DEV\\server\\workspace\\amis3";
	String RESOURCE_PROJECT_HOME = "C:\\AMIS3_DEV\\server\\workspace\\amis3-resource";
	
	private String projectName1;
	private String projectName2;
	private String newPackageName;
	private String projectHomeSuffix;
	private String targetWorkspace = "./target/tested-files";
	
	public OneProjectConverter(String projectName1, String projectName2, String newPackageName, String projectHomeSuffix) {
		//
		this.projectName1 = projectName1;
		this.projectName2 = projectName2;
		this.newPackageName = newPackageName;
		this.projectHomeSuffix = projectHomeSuffix;
	}

	@Override
    public void run() {
		//
		long startMillis = System.currentTimeMillis();
		logger.info("{}.{} start converting", projectName1, projectName2);
		try {
			convert2Depth(projectName1, projectName2);
		}
		catch (Exception exc) {
			logger.info(exc.getMessage(), exc);
		}
		logger.info("{}.{} converted ({} ms)", projectName1, projectName2, (System.currentTimeMillis() - startMillis));
    }
	
	private void convert2Depth(String projectName1, String projectName2) throws IOException {
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
        parameter.setLexicalPreserving(true);
        
        addDependenciesToParam(parameter, projectName1, projectName2);
        
        JavaAbstractParam javaAbstractParam = new JavaAbstractParam();
        javaAbstractParam.setTargetFilePostfix("ExtService.java");
		javaAbstractParam.setSourcePackage(String.format("amis3.%s.%s", projectName1, projectName2));
        javaAbstractParam.setSourceDtoPackage(String.format("amis3.vo.%s.%s", projectName1, projectName2));
        javaAbstractParam.setNewProjectName1(projectName1);
        javaAbstractParam.setNewProjectName2(newPackageName);

        PackageRule javaConvertPackageRule = PackageRule.newInstance()
                .add(0, "amis3"     	, "kr.amc.amis")
                // .add(2, projectName2    , newPackageName) --> addSecondPackageRules()
                .add(4, "controller"	, "rest")
                .add(4, "dao"       	, "store")
                .add(4, "service"   	, "service")
                .add(1, "vo"        	, 4)           // amis3.vo.mc.oo.od -> amis3.mc.oo.od.vo
                .add(4, "vo"        	, "entity")    // amis3.mc.oo.od.vo -> amis3.mc.oo.od.entity
                // this rule would execute after name changed.
                .add("ExtServiceLogic"  , "ext.logic")
                .add("ExtService"      	, "ext.spec")
                .addChangeImport("amis3.fw.batch.step.AbstractProcessor", "kr.amc.amil.batch.step.AbstractProcessor")
                .addChangeImport("amis3.fw.batch.step.AbstractTasklet", "kr.amc.amil.batch.step.AbstractTasklet")
                .addChangeImport("amis3.fw.core.aspect.NoMessageAllowed", "kr.amc.amil.amcmessage.annotation.NoMessageAllowed")
                .addChangeImport("amis3.fw.core.binding.annotation.ConvertIgnore", "kr.amc.amil.amcmessage.annotation.ConvertIgnore")
                .addChangeImport("amis3.fw.core.binding.annotation.PreDataParam", "kr.amc.amil.amcmessage.annotation.PreDataParam")
                .addChangeImport("amis3.fw.core.binding.annotation.RecordSetParam", "kr.amc.amil.amcmessage.annotation.RecordSetParam")
                .addChangeImport("amis3.fw.core.binding.annotation.Telegram", "kr.amc.amil.amcmessage.annotation.Telegram")
                .addChangeImport("amis3.fw.core.binding.annotation.TelegramKey", "kr.amc.amil.amcmessage.annotation.TelegramKey")
                .addChangeImport("amis3.fw.core.binding.message.AMCData", "kr.amc.amil.amcmessage.AMCData")
                .addChangeImport("amis3.fw.core.binding.message.AMCDataFactory", "kr.amc.amil.amcmessage.AMCDataFactory")
                .addChangeImport("amis3.fw.core.binding.message.AMCDataMessageType", "kr.amc.amil.amcmessage.AMCDataMessageType")
                .addChangeImport("amis3.fw.core.binding.message.AMCDataResultCode", "kr.amc.amil.amcmessage.AMCDataResultCode")
                .addChangeImport("amis3.fw.core.binding.message.DateTimeFormat", "kr.amc.amil.amcmessage.util.DateTimeFormat")
                .addChangeImport("amis3.fw.core.binding.message.Header", "kr.amc.amil.amcmessage.Header")
                .addChangeImport("amis3.fw.core.binding.message.PreData", "kr.amc.amil.amcmessage.PreData")
                .addChangeImport("amis3.fw.core.binding.message.Record", "kr.amc.amil.amcmessage.Record")
                .addChangeImport("amis3.fw.core.binding.message.RecordSet", "kr.amc.amil.amcmessage.RecordSet")
                .addChangeImport("amis3.fw.core.binding.message.RecordSetType", "kr.amc.amil.amcmessage.RecordSetType")
                .addChangeImport("amis3.fw.core.binding.message.TelegramType", "kr.amc.amil.amcmessage.annotation.TelegramType")
                .addChangeImport("amis3.fw.core.binding.message.TransferObject", "kr.amc.amil.amcmessage.TransferObject")
                .addChangeImport("amis3.fw.core.binding.PreDataUtil", "kr.amc.amil.amcmessage.binding.PreDataUtil")
                .addChangeImport("amis3.fw.core.binding.RecordSetUtil", "kr.amc.amil.amcmessage.binding.RecordSetUtil")
                .addChangeImport("amis3.fw.core.cst.Constants", "kr.amc.amil.cst.Constants")
                .addChangeImport("amis3.fw.core.exception.AMISBizException", "kr.amc.amil.exception.AMISBizException")
                .addChangeImport("amis3.fw.core.util.BeanUtil", "kr.amc.amil.context.BeanUtil")
                .addChangeImport("amis3.fw.core.util.CommunicationUtil", "kr.amc.amil.protocol.CommunicationUtil")
                .addChangeImport("amis3.fw.core.util.ContextUtil", "kr.amc.amil.context.ContextUtil")
                .addChangeImport("amis3.fw.core.util.ConverterUtil", "kr.amc.amil.utils.data.ConverterUtil")
                .addChangeImport("amis3.fw.core.util.CryptoUtil", "kr.amc.amil.crypto.CryptoUtil")
                .addChangeImport("amis3.fw.core.util.DateUtil", "kr.amc.amil.utils.date.DateUtil")
                .addChangeImport("devonframe.util.DateUtil", "kr.amc.amil.utils.date.DateUtil")
                .addChangeImport("amis3.fw.core.util.DBUtil", "kr.amc.amil.utils.db.DBUtil")
                .addChangeImport("amis3.fw.core.util.EaiUtil", "kr.amc.amil.eai.EaiUtil")
                .addChangeImport("amis3.fw.core.util.JsonUtil", "kr.amc.amil.utils.json.JsonUtil")
                .addChangeImport("amis3.fw.core.util.RestUtil", "kr.amc.amil.protocol.http.RestUtil")
                .addChangeImport("amis3.fw.core.util.RuleUtil", "kr.amc.amil.rule.RuleUtil")
                .addChangeImport("amis3.fw.core.util.StringUtil", "kr.amc.amil.utils.text.StringUtil")
                .addChangeImport("devonframe.util.StringUtil", "kr.amc.amil.utils.text.StringUtil")
                .addChangeImport("amis3.fw.core.util.SystemEnvUtil", "kr.amc.amil.utils.SystemEnvUtil")
                .addChangeImport("amis3.fw.core.util.VOUtil", "kr.amc.amil.utils.data.DTOUtil")
                .addChangeImport("amis3.fw.core.util.MessageUtil", "kr.amc.amil.context.MessageUtil")
                .addChangeImport("devonframe.paging.model.PagingList", "kr.amc.amil.persistence.paging.PageList")
                .addChangeImport("devonframe.dataaccess.exception.DuplicationKeysException", "org.springframework.dao.DuplicateKeyException")
                .addChangeImport("devonframe.exception.SystemException", "kr.amc.amil.exception.AmilSysException")
                .addRemoveImport("amis3.fw.core.vo.AMISDefaultVO")
                .addRemoveImport("amis3.fw.core.persistence.AMISCommonDao");
        
        addSecondPackageRules(javaConvertPackageRule);
        
        PackageRule packageRuleForCheckStubDto = PackageRule.copyOf(javaConvertPackageRule)
                .set(1, "vo", 3)                // amis3.vo.mc.oo.od -> amis3.mc.oo.vo.od
                .set(3, "vo", "ext.spec.sdo");  // amis3.mc.oo.vo.od -> amis3.mc.oo.ext.spec.sdo.od
        
        NameRule javaConvertNameRule = NameRule.newInstance()
                .add("VO", "DTO")
                .addExceptionPattern("springframework");
        
        PackageRule namespaceRule = PackageRule.newInstance()
                .setPrefix("kr.amc.amis")
                .add(1, projectName2, newPackageName)
                .setPostfix("store.mapper");
        
        ComplexProjectConverter converter = new ComplexProjectConverter(parameter, javaAbstractParam, 
                javaConvertNameRule, javaConvertPackageRule, packageRuleForCheckStubDto, namespaceRule);
        converter.convert();
	}

    private void addSecondPackageRules(PackageRule packageRule) {
        //
        addSecondPackageRules(packageRule, AmisConstants.projectNames);
        addSecondPackageRules(packageRule, AmisConstants.projectNamesMdm);
        addSecondPackageRules(packageRule, AmisConstants.projectNamesEtc);
    }
    
    private void addSecondPackageRules(PackageRule packageRule, String[][] prjNames) {
        //
        for (int i = 0; i < prjNames.length; i++) {
            String prjName2 = prjNames[i][1];
            String newPackageName = prjNames[i][2];
            if (!packageRule.exist(2, prjName2)) {
                packageRule.add(2, prjName2, newPackageName);
            }
        }
    }

    private void addDependenciesToParam(ConvertParameter parameter, String projectName1, String projectName2) {
        //
        String[] dependencyList = findDependency(projectName1, projectName2);
        if (dependencyList == null) {
            return;
        }
        
        for (int i = 0; i < dependencyList.length; i++) {
            String depPackage = dependencyList[i];
            String[] deparr = depPackage.split("\\.");
            parameter.addDependency(deparr[0], findNewName(deparr[0], deparr[1]));
        }
    }

    private String findNewName(String name1, String name2) {
        String[][] prjNames = AmisConstants.projectNames;
        for (int i = 0; i < prjNames.length; i++) {
            if (prjNames[i][0].equals(name1) && prjNames[i][1].equals(name2)) {
                return prjNames[i][2];
            }
        }
        
        prjNames = AmisConstants.projectNamesMdm;
        for (int i = 0; i < prjNames.length; i++) {
            if (prjNames[i][0].equals(name1) && prjNames[i][1].equals(name2)) {
                return prjNames[i][2];
            }
        }
        
        prjNames = AmisConstants.projectNamesEtc;
        for (int i = 0; i < prjNames.length; i++) {
            if (prjNames[i][0].equals(name1) && prjNames[i][1].equals(name2)) {
                return prjNames[i][2];
            }
        }
        throw new RuntimeException("new name not found...");
    }

    private String[] findDependency(String projectName1, String projectName2) {
        //
        Object[][] deps = AmisConstants.projectDependencies;
        for (int i = 0; i < deps.length; i++) {
            String name1 = (String) deps[i][0];
            String name2 = (String) deps[i][1];
            String[] depList = (String[]) deps[i][2];
            if (name1.equals(projectName1) && name2.equals(projectName2)) {
                return depList;
            }
        }
        return null;
    }
}