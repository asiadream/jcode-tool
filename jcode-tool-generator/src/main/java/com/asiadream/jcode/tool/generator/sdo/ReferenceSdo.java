package com.asiadream.jcode.tool.generator.sdo;

import com.asiadream.jcode.tool.generator.meta.BizNameLocation;
import com.asiadream.jcode.tool.share.data.Pair;
import com.asiadream.jcode.tool.share.util.string.PackageNameUtil;

import java.util.ArrayList;
import java.util.List;

public class ReferenceSdo {
    //
    private List<ClassReference> references;
    private List<Pair<String, String>> customContext;
    private String bizName;

    public static ReferenceSdo create() {
        //
        return new ReferenceSdo();
    }

    public ReferenceSdo() {
        //
        this.references = new ArrayList<>();
        this.customContext = new ArrayList<>();
    }

    public ReferenceSdo(List<ClassReference> references) {
        //
        this.references = references;
        this.customContext = new ArrayList<>();
    }

    public ReferenceSdo(String referenceName, String className, String projectPath) {
        this();
        this.references.add(new ClassReference(referenceName, className, projectPath));
    }

    public String getPreBizNameFromReferenceClass(BizNameLocation location, int middleNameSeq, String groupId, String appName) {
        // kr.ac.kookmin.ktis.code.domain.entity.SubjectCode --> preBizName: 'code', postBizName: ''
        // io.naradrama.talk.domain.entity.town.TalkTown     --> preBizName: '',     postBizName: 'town'
        if (location == BizNameLocation.PRE) {
            return getBizNameFromReferenceClass(location, middleNameSeq, groupId, appName);
        }
        return null;
    }

    public String getPostBizNameFromReferenceClass(BizNameLocation location, int middleNameSeq, String groupId, String appName) {
        //
        if (location == BizNameLocation.POST) {
            return getBizNameFromReferenceClass(location, middleNameSeq, groupId, appName);
        }
        return null;
    }

    public String getMiddleBizNameFromReferenceClass(BizNameLocation location, int middleNameSeq, String groupId, String appName) {
        //
        if (location == BizNameLocation.MIDDLE) {
            return getBizNameFromReferenceClass(location, middleNameSeq, groupId, appName);
        }
        return null;
    }

    private String getBizNameFromReferenceClass(BizNameLocation location, int middleNameSeq, String groupId, String appName) {
        // className: io.naradrama.talk.domain.entity.town.TalkTown -> bizName: town
        if (references == null || references.size() <= 0) {
            return this.bizName;
        }
        String className = references.get(0).getClassName();
        // The groupId and appName must be in class name.
        if (!className.contains(groupId) || !className.contains(appName)) {
            return null;
        }

        String partName = PackageNameUtil.removeLastElement(className);
        partName = PackageNameUtil.removePart(partName, groupId);
        partName = PackageNameUtil.removePart(partName, appName);
        if (location == BizNameLocation.PRE) {
            return PackageNameUtil.getFirstName(partName);
        } else if (location == BizNameLocation.POST) {
            return PackageNameUtil.getLastName(partName);
        } else if (location == BizNameLocation.MIDDLE) {
            return PackageNameUtil.getName(partName, middleNameSeq);
        }
        return null;
    }

    public ReferenceSdo addClassReference(ClassReference reference) {
        this.references.add(reference);
        return this;
    }

    public ReferenceSdo addClassReference(String referenceName, String className, String projectPath) {
        this.references.add(new ClassReference(referenceName, className, projectPath));
        return this;
    }

    public ReferenceSdo addCustomContext(String key, String value) {
        //
        this.customContext.add(new Pair<>(key, value));
        return this;
    }

    public ReferenceSdo setBizName(String bizName) {
        //
        this.bizName = bizName;
        return this;
    }

    public List<ClassReference> getReferences() {
        return references;
    }

    public void setReferences(List<ClassReference> references) {
        this.references = references;
    }

    public List<Pair<String, String>> getCustomContext() {
        return customContext;
    }

    public void setCustomContext(List<Pair<String, String>> customContext) {
        this.customContext = customContext;
    }
}
