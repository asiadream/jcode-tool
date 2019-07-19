package com.asiadream.jcode.tool.generator.sdo;

import com.asiadream.jcode.tool.generator.meta.BizNameLocation;
import com.asiadream.jcode.tool.share.util.string.PackageNameUtil;

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

    public String getPreBizName(BizNameLocation location, String groupId, String appName) {
        // kr.ac.kookmin.ktis.code.domain.entity.SubjectCode --> preBizName: 'code', postBizName: ''
        // io.naradrama.talk.domain.entity.town.TalkTown     --> preBizName: '',     postBizName: 'town'
        if (location == BizNameLocation.PRE) {
            return getBizName(location, groupId, appName);
        }

        return null;
    }

    public String getPostBizName(BizNameLocation location, String groupId, String appName) {
        //
        if (location == BizNameLocation.POST) {
            return getBizName(location, groupId, appName);
        }

        return null;
    }

    private String getBizName(BizNameLocation location, String groupId, String appName) {
        //
        if (references == null || references.size() <= 0) {
            return null;
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

    public List<ClassReference> getReferences() {
        return references;
    }

    public void setReferences(List<ClassReference> references) {
        this.references = references;
    }
}
