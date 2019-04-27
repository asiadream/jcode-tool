package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.generator.converter.ProjectItemConverter;
import com.asiadream.jcode.tool.generator.converter.ProjectItemType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.data.Pair;
import com.asiadream.jcode.tool.share.util.file.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class JavaDuplicationChecker extends ProjectItemConverter {
    //
    private static Logger logger = LoggerFactory.getLogger("dup-checker-java");
    private Map<String, List<String>> nameGroup = new HashMap<>();

    public JavaDuplicationChecker(ProjectConfiguration sourceConfiguration) {
        super(sourceConfiguration, null, ProjectItemType.Java, null);
    }

    @Override
    public void convert(String sourceFileName) throws IOException {
        //
        String className = PathUtil.toClassName(sourceFileName);
        Pair<String, String> packageClassName = PathUtil.devideClassName(className);
        String shortName = packageClassName.y;
        
        List<String> group = findOrCreateGroup(shortName);
        group.add(className);
    }

    private List<String> findOrCreateGroup(String shortName) {
        //
        List<String> group = nameGroup.get(shortName);
        if (group != null) return group;
        
        group = new ArrayList<>();
        nameGroup.put(shortName, group);
        return nameGroup.get(shortName);
    }

    public void show() {
        for (Iterator<String> iter = nameGroup.keySet().iterator(); iter.hasNext(); ) {
            String shortName = iter.next();
            List<String> group = nameGroup.get(shortName);
            if (group.size() > 1) {
                showGroup(group);
            }
        }
    }

    private void showGroup(List<String> group) {
        for (String ele : group) {
            logger.warn(ele);
        }
    }

}
