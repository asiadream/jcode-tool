package com.asiadream.jcode.tool.generator.converter;

import com.asiadream.jcode.tool.share.config.ProjectSources;
import com.asiadream.jcode.tool.share.config.SourceFolders;
import com.asiadream.jcode.tool.share.util.file.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MultiItemPackageConverter {
    //
	private static final Logger logger = LoggerFactory.getLogger(MultiItemPackageConverter.class);
	private static final Logger incompleteLogger = LoggerFactory.getLogger("incomplete");
	
    private List<ProjectItemConverter> converters;

    public MultiItemPackageConverter() {
        //
        this.converters = new ArrayList<>();
    }

    public MultiItemPackageConverter add(ProjectItemConverter converter) {
        //
        this.converters.add(converter);
        return this;
    }

    public void convert(String packageName) throws IOException {
        // packageName : com.foo.bar -> path : com/foo/bar
        if (converters == null || converters.isEmpty()) {
            System.err.println("Please add converter.");
            return;
        }

        ProjectItemConverter firstConverter = getFirstConverter();

        String packagePath = PathUtil.toPath(packageName);
        String physicalSourcePath = getPhysicalSourcePath(firstConverter, packagePath);

        try (Stream<Path> paths = Files.walk(Paths.get(physicalSourcePath))) {
            paths
                    .filter(p -> p.toString().endsWith("." + firstConverter.getItemExtension()))
                    .forEach(this::process);
        }
    }

    private void process(Path path) {
        String sourceFileName = null;
        try {
            SourceFolders sourceFolders = getFirstConverter().sourceConfiguration.getSourceFolders();
            String physicalPathName = path.toString();
            sourceFileName = ProjectSources.extractSourceFilePath(physicalPathName, sourceFolders);

            boolean converted = false;
            for(ProjectItemConverter converter : converters) {
                //
                if (!converted) {
                    converted = convert(converter, sourceFileName);
                }
            }

            if (!converted) {
                System.err.println("Couldn't convert --> " + sourceFileName);
                incompleteLogger.warn("[No converter]"+sourceFileName);
            }

        } catch (Throwable e) {
            incompleteLogger.warn(sourceFileName);
            logger.error("Couldn't convert --> {}, {}", sourceFileName, e.getMessage());
            logger.error("Throwable : ", e);
        }
    }

    private boolean convert(ProjectItemConverter converter, String sourceFileName) throws IOException {
        //
        if (converter.hasItemNamePostfix()) {
            String postfix = converter.getItemNamePostfix();
            if (!sourceFileName.endsWith(postfix)) {
                return false;
            }
        }

        converter.convert(sourceFileName);
        return true;
    }

    private ProjectItemConverter getFirstConverter() {
        //
        return converters.get(0);
    }


    private String getPhysicalSourcePath(ProjectItemConverter converter, String sourcePath) {
        //
        if (converter.projectItemType == ProjectItemType.Java) {
            return converter.sourceConfiguration.makePhysicalJavaSourceFilePath(sourcePath);
        } else {
            return converter.sourceConfiguration.makePhysicalResourceFilePath(sourcePath);
        }
    }
}
