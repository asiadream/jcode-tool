package com.asiadream.jcode.tool.share.config;

import java.io.File;

public class SourceFolders {
    //
    private static final String PATH_DELIM = File.separator;
    public static final SourceFolders JavaFolders = new SourceFolders(
            String.format("src%smain%sjava", PATH_DELIM, PATH_DELIM),
            String.format("src%smain%sresources", PATH_DELIM, PATH_DELIM),
            String.format("src%stest%sjava", PATH_DELIM, PATH_DELIM),
            String.format("src%stest%sresources", PATH_DELIM, PATH_DELIM)
    );
    public static final SourceFolders JsFolders = new SourceFolders(
            "src", "resources", "test", "test"
    );

    public final String source;
    public final String resources;
    public final String testSource;
    public final String testResources;

    public SourceFolders(String source, String resources, String testSource, String testResources) {
        this.source = source;
        this.resources = resources;
        this.testSource = testSource;
        this.testResources = testResources;
    }

    public static SourceFolders newSourceFolders(String srcMainJava, String srcMainResources) {
        //
        return new SourceFolders(
                srcMainJava != null ? srcMainJava : JavaFolders.source,
                srcMainResources != null ? srcMainResources : JavaFolders.resources,
                JavaFolders.testSource,
                JavaFolders.testResources
        );
    }
}
