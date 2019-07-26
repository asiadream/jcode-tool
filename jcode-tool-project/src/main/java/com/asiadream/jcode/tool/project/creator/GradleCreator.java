package com.asiadream.jcode.tool.project.creator;

import com.asiadream.jcode.tool.generator.source.GradleSource;
import com.asiadream.jcode.tool.generator.source.gradle.*;
import com.asiadream.jcode.tool.generator.writer.GradleWriter;
import com.asiadream.jcode.tool.project.model.Dependency;
import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GradleCreator {
    //
    private static final String BUILD_GRADLE_FILE = "build.gradle";
    private static final String SETTINS_GRADLE_FILE = "settings.gradle";
    private GradleWriter gradleWriter;

    public GradleCreator(ProjectConfiguration configuration) {
        //
        this.gradleWriter = new GradleWriter(configuration);
    }

    public void create(ProjectModel model) throws IOException {
        //
        if (model.isRoot()) {
            GradleScript settingsGradle = createSettingsGradle(model);
            GradleSource settingsSource = new GradleSource(settingsGradle, SETTINS_GRADLE_FILE);
            gradleWriter.write(settingsSource);
        }

        GradleScript buildGradle = createBuildGradle(model);
        GradleSource gradleSource = new GradleSource(buildGradle, BUILD_GRADLE_FILE);
        gradleWriter.write(gradleSource);
    }

    private GradleScript createSettingsGradle(ProjectModel model) {
        //
        GradleScript gradleScript = new GradleScript(SETTINS_GRADLE_FILE);
        gradleScript.addElement(new AssignmentStatement("rootProject.name", model.getName()));
        model.getChildren().forEach(subModel -> gradleScript.addElement(new MethodCall("include", subModel.getName())));
        return gradleScript;
    }

    private GradleScript createBuildGradle(ProjectModel model) {
        //
        if (model.isRoot()) {
            return createMainBuildGradle(model);
        } else {
            return createSubBuildGradle(model);
        }
    }

    private GradleScript createSubBuildGradle(ProjectModel model) {
        //
        return new GradleScript(BUILD_GRADLE_FILE)
                .addElement(toGradleDependencies(model.getDependencies()));
    }

    private GroovyDSL toGradleDependencies(List<Dependency> dependencies) {
        //
        return new GroovyDSL("dependencies")
                .addAllElement(dependencies.stream()
                        .map(this::toGradleDependency)
                        .collect(Collectors.toList()));
    }

    private MethodCall toGradleDependency(Dependency dependency) {
        //
        MethodCall methodCall;
        switch (dependency.getType()) {
            case Project:
                methodCall = new MethodCall("api", new MethodCall("project", dependency.toSimpleString()).setPrintBracket(true));
                break;
            case Implementation:
                methodCall = new MethodCall("implementation", dependency.toColonSeparatedString()).setPrintBracket(true);
                break;
            case CompileOnly:
                methodCall = new MethodCall("compileOnly", dependency.toColonSeparatedString()).setPrintBracket(true);
                break;
            case AnnotationProcessor:
                methodCall = new MethodCall("annotationProcessor", dependency.toColonSeparatedString()).setPrintBracket(true);
                break;
            case TestCompile:
                methodCall = new MethodCall("testCompile", dependency.toColonSeparatedString()).setPrintBracket(true);
                break;
            case Runtime:
                methodCall = new MethodCall("runtime", dependency.toColonSeparatedString()).setPrintBracket(true);
                break;
            default:
                methodCall = new MethodCall("compile", dependency.toColonSeparatedString()).setPrintBracket(true);
        }

        return methodCall;
    }

    private GradleScript createMainBuildGradle(ProjectModel model) {
        // TODO : Remove hardcode
        AssignmentStatement talkVersion = new AssignmentStatement("talkVersion", "0.2.1-SNAPSHOT");
        AssignmentStatement nexususer = new AssignmentStatement("nexususer", "admin");
        AssignmentStatement nexuspassword = new AssignmentStatement("nexuspassword", "password");

        GradleScript gradleScript = new GradleScript(BUILD_GRADLE_FILE);
        // plugins
        gradleScript.addElement(createPlugins(model));
        gradleScript.addElement(createExt(model, talkVersion));
        gradleScript.addElement(createAllProjects(model, talkVersion));
        gradleScript.addElement(createConfigurationsAll(model));
        gradleScript.addElement(createSubprojects(model, nexususer, nexuspassword));

        return gradleScript;
    }

    private GroovyDSL createPlugins(ProjectModel model) {
        // TODO : Remove hardcode
        GroovyDSL groovyDSL = new GroovyDSL("plugins");
        groovyDSL.addElement(new MethodCall("id", "io.spring.dependency-management", new MethodCall("version", "1.0.7.RELEASE")));
        groovyDSL.addElement(new MethodCall("id", "maven"));
        return groovyDSL;
    }

    private GroovyDSL createExt(ProjectModel model, AssignmentStatement talkVersion) {
        //
        GroovyDSL groovyDSL = new GroovyDSL("ext");
        groovyDSL.addElement(talkVersion);
        return groovyDSL;
    }

    private GroovyDSL createAllProjects(ProjectModel model, AssignmentStatement talkVersion) {
        //
        GroovyDSL groovyDSL = new GroovyDSL("allprojects");
        groovyDSL.addElement(new MethodCall("group", model.getGroup()));
        groovyDSL.addElement(new MethodCall("version", talkVersion.getVariable()));
        return groovyDSL;
    }

    private GroovyDSL createConfigurationsAll(ProjectModel model) {
        //
        GroovyDSL groovyDSL = new GroovyDSL("configurations.all");
        groovyDSL.addElement(new MethodCall("resolutionStrategy.cacheChangingModulesFor")
                .addArgument(0)
                .addArgument("seconds")
                .setPrintBracket(false));
        return groovyDSL;
    }

    private GroovyDSL createSubprojects(ProjectModel model, AssignmentStatement nexususer, AssignmentStatement nexuspassword) {
        // TODO : Remove hardcode
        GroovyDSL groovyDSL = new GroovyDSL("subprojects");
        groovyDSL.addElement(new MethodCall("apply", new GroovyMap("plugin", "java-library")));
        groovyDSL.addElement(new MethodCall("apply", new GroovyMap("plugin", "io.spring.dependency-management")));
        groovyDSL.addElement(new AssignmentStatement("sourceCompatibility", "1.8"));
        groovyDSL.addElement(new AssignmentStatement("targetCompatibility", "1.8"));
        groovyDSL.addElement(
                new GroovyDSL("repositories")
                        .addElement(
                                new GroovyDSL("maven")
                                        .addElement(
                                                new GroovyDSL("credentials")
                                                        .addElement(new AssignmentStatement("username", nexususer.getVariable()))
                                                        .addElement(new AssignmentStatement("password", nexuspassword.getVariable())))
                                        .addElement(new MethodCall("url", "${nexusbaseurl}/nara-public/")))
                        .addElement(new MethodCall("mavenLocal"))
                        .addElement(new MethodCall("jcenter")));
        groovyDSL.addElement(
                new GroovyDSL("dependencyManagement")
                        .addElement(new GroovyDSL("imports")
                                .addElement(new MethodCall("mavenBom", "org.springframework.boot:spring-boot-dependencies:2.1.5.RELEASE"))));
        groovyDSL.addElement(toGradleDependencies(model.getDependencies()));
        return groovyDSL;
    }
}
