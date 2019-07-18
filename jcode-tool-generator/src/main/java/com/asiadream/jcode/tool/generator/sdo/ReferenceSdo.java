package com.asiadream.jcode.tool.generator.sdo;

import java.util.ArrayList;
import java.util.List;

public class ReferenceSdo {
    //
    private List<ClassReference> references;

    public ReferenceSdo() {
        this.references = new ArrayList<>();
    }

    public ReferenceSdo(List<ClassReference> references) {
        this.references = references;
    }

    public ReferenceSdo(String referenceName, String className, String projectPath) {
        this();
        this.references.add(new ClassReference(referenceName, className, projectPath));
    }

    public ReferenceSdo addClassReference(ClassReference reference) {
        this.references.add(reference);
        return this;
    }

    public ReferenceSdo addClassReference(String referenceName, String className, String projectPath) {
        this.references.add(new ClassReference(referenceName, className, projectPath));
        return this;
    }

    public List<ClassReference> getReferences() {
        return references;
    }

    public void setReferences(List<ClassReference> references) {
        this.references = references;
    }
}
