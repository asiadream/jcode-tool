package com.asiadream.jcode.tool.project.creator;

import com.asiadream.jcode.tool.project.model.Dependency;
import com.asiadream.jcode.tool.project.model.DependencyType;
import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.project.model.ProjectProperty;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.util.xml.DomUtil;
import com.asiadream.jcode.tool.xml.source.XmlSource;
import com.asiadream.jcode.tool.xml.writer.XmlWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class PomCreator {
    //
    private static final String POM_FILE = "pom.xml";
    private XmlWriter xmlWriter;

    public PomCreator(ProjectConfiguration configuration) {
        //
        this.xmlWriter = new XmlWriter(configuration);
    }

    public void create(ProjectModel model) throws IOException {
        //
        Document document = createDocument(model);
        XmlSource xmlSource = new XmlSource(document, POM_FILE);
        xmlSource.setResourceFile(false);
        xmlWriter.write(xmlSource);
    }

    private Document createDocument(ProjectModel model) {
        //
        DocumentBuilder builder = null;
        try {
            builder = DomUtil.newBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Can't create DocumentBuilder.", e);
        }

        Document document = builder.newDocument();
        document.setXmlStandalone(true);

        Element project = createProjectElement(document, model);
        document.appendChild(project);

        return document;
    }

    private Element createProjectElement(Document document, ProjectModel model) {
        //
        Element project = document.createElement("project");
        project.setAttribute("xmlns", "http://maven.apache.org/POM/4.0.0");
        project.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        project.setAttribute("xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");

        project.appendChild(DomUtil.createTextElement(document, "modelVersion", "4.0.0"));

        // create parent element
        if (model.hasParent()) {
            ProjectModel parentModel = model.getParent();
            Element parent = createParentElement(document, parentModel);
            project.appendChild(parent);
        }

        // project information elements
        project.appendChild(DomUtil.createTextElement(document, "artifactId", model.getName()));

        if (model.getPackaging() != null) {
            project.appendChild(DomUtil.createTextElement(document, "packaging", model.getPackaging()));
        }

        if (!model.hasParent()) {
            project.appendChild(DomUtil.createTextElement(document, "groupId", model.getGroup()));
            project.appendChild(DomUtil.createTextElement(document, "version", model.getVersion()));
        }

        // properties element
        if (model.isRoot() && model.hasProperties()) {
            Element properties = createPropertiesElement(document, model.getProperties());
            project.appendChild(properties);
        }

        // modules element
        if (model.hasChildren()) {
            Element modules = createModulesElement(document, model.getChildren());
            project.appendChild(modules);
        }

        // dependencies element
        if (model.hasDependencies()) {
            Element dependencies = createDependenciesElement(document, model.getDependencies());
            project.appendChild(dependencies);
        }

        // dependencyManagement element
        if (model.isRoot()) {
            Element dependencyManagement = createDependencyManagement(document);
            project.appendChild(dependencyManagement);
        }

        // build element
        if (model.isRoot()) {
            Element build = createBuildElement(document);
            project.appendChild(build);
        }

        return project;
    }

    private Element createParentElement(Document document, ProjectModel model) {
        //
        Element parent = document.createElement("parent");

        parent.appendChild(DomUtil.createTextElement(document, "artifactId", model.getName()));
        parent.appendChild(DomUtil.createTextElement(document, "groupId", model.getGroup()));
        parent.appendChild(DomUtil.createTextElement(document, "version", model.getVersion()));

        return parent;
    }

    private Element createPropertiesElement(Document document, List<ProjectProperty> projectProperties) {
        //
        Element properties = document.createElement("properties");
        for (ProjectProperty property : projectProperties) {
            properties.appendChild(DomUtil.createTextElement(document, property.getKey(), property.getValue()));
        }
        return properties;
    }

    private Element createModulesElement(Document document, List<ProjectModel> models) {
        //
        Element modules = document.createElement("modules");
        for (ProjectModel model : models) {
            modules.appendChild(DomUtil.createTextElement(document, "module", model.getName()));
        }
        return modules;
    }

    private Element createDependenciesElement(Document document, List<Dependency> dependencyModelList) {
        //
        Element dependencies = document.createElement("dependencies");
        for (Dependency dependency : dependencyModelList) {
            dependencies.appendChild(createDependencyElement(document, dependency));
        }
        return dependencies;
    }

    private Element createDependencyElement(Document document, Dependency dependencyModel) {
        //
        Element dependency = document.createElement("dependency");
        dependency.appendChild(DomUtil.createTextElement(document, "groupId", dependencyModel.getGroupId()));
        dependency.appendChild(DomUtil.createTextElement(document, "artifactId", dependencyModel.getName()));
        if (dependencyModel.getVersion() != null)
            dependency.appendChild(DomUtil.createTextElement(document, "version", dependencyModel.getVersion()));
        if (dependencyModel.getType() == DependencyType.MavenBom)
            dependency.appendChild(DomUtil.createTextElement(document, "type", "pom"));
        if (dependencyModel.getScope() != null)
            dependency.appendChild(DomUtil.createTextElement(document, "scope", dependencyModel.getScope()));

        return dependency;
    }

    private Element createDependencyManagement(Document document) {
        //
        Element dependencyManagement = document.createElement("dependencyManagement");

        Element dependencies = document.createElement("dependencies");
        dependencies.appendChild(createDependencyElement(document,
                new Dependency("org.springframework.boot", "spring-boot-dependencies",
                        "${spring.boot.version}", DependencyType.MavenBom, "import")));
        dependencies.appendChild(createDependencyElement(document,
                new Dependency("org.springframework.cloud", "spring-cloud-dependencies",
                        "${spring.cloud.version}", DependencyType.MavenBom, "import")));
        dependencies.appendChild(createDependencyElement(document,
                new Dependency("org.springframework.cloud", "spring-cloud-stream-dependencies",
                        "${spring.cloud.stream.version}", DependencyType.MavenBom, "import")));
        dependencyManagement.appendChild(dependencies);

        return dependencyManagement;
    }

    private Element createBuildElement(Document document) {
        //
        Element build = document.createElement("build");

        Element plugins = document.createElement("plugins");
        build.appendChild(plugins);

        Element plugin = document.createElement("plugin");
        plugin.appendChild(DomUtil.createTextElement(document, "artifactId", "maven-compiler-plugin"));
        plugin.appendChild(DomUtil.createTextElement(document, "version", "3.1"));
        Element configuration = document.createElement("configuration");
        configuration.appendChild(DomUtil.createTextElement(document, "source", "1.8"));
        configuration.appendChild(DomUtil.createTextElement(document, "target", "1.8"));
        configuration.appendChild(DomUtil.createTextElement(document, "encoding", "UTF-8"));
        plugin.appendChild(configuration);

        plugins.appendChild(plugin);

        return build;
    }
}
