package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.java.source.JavaSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JavaSelfExtUseChecker {
    //
    private static Logger logger = LoggerFactory.getLogger("selfext-checker");

    public void checkAndWarn(JavaSource source) {
        //
        String packageName = source.getPackageName();
        String packageName012 = makePackage012(packageName);
        if (packageName012 == null) return;
        
        List<String> imports = source.getImports();
        for(String imp : imports) {
            if (imp.endsWith("ExtService") && imp.startsWith(packageName012)) {
                logger.warn(source.getClassName() + " --> " + imp);
            }
        }
    }

    private String makePackage012(String packageName) {
        //
        String[] paths = packageName.split("\\.");
        if (paths.length >= 3) {
            return paths[0] + "." + paths[1] + "." + paths[2];
        }
        return null;
    }

}
