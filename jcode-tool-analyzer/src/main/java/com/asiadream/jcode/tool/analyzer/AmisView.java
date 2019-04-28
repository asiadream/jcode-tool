package com.asiadream.jcode.tool.analyzer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;
import com.asiadream.jcode.tool.analyzer.store.JavaDependencyStore;
import com.asiadream.jcode.tool.analyzer.store.StoreConfig;
import com.asiadream.jcode.tool.analyzer.viewer.DependencyFlatViewer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

public class AmisView {
    //
    private static final String[] AMIS_MODULES = {
            "amis3.bp.bp",

            "amis3.ca.am",
            "amis3.ca.cm",
            "amis3.ca.eh",
            "amis3.ca.fa",
            "amis3.ca.fm",
            "amis3.ca.ga",
            "amis3.ca.hr",
            "amis3.ca.id",
            "amis3.ca.ja",
            "amis3.ca.sm",
            "amis3.ca.wl",

            "amis3.cr.cr",

            "amis3.em.em",

            "amis3.et.el",
            "amis3.et.fm",
            "amis3.et.ml",
            "amis3.et.sy",

            "amis3.hm.gc",
            "amis3.hm.gm",
            "amis3.hm.gr",
            "amis3.hm.gs",
            "amis3.hm.gx",
            "amis3.hm.gz",

            "amis3.mc.mr",
            "amis3.mc.ms",
            "amis3.mc.nr",
            "amis3.mc.nu",
            "amis3.mc.oo",
            "amis3.mc.op",
            "amis3.mc.ph",

            "amis3.rd.rd",

            "amis3.sc.sc",

            "amis3.sp.ps",
            "amis3.sp.sa",
            "amis3.sp.sb",
            "amis3.sp.sd",
            "amis3.sp.se",
            "amis3.sp.si",
            "amis3.sp.ss",
            "amis3.sp.sz",

            "amis3.wa.ev",
            "amis3.wa.wb",
            "amis3.wa.wj",
            "amis3.wa.wm",
            "amis3.wa.wp",
            "amis3.wa.wr",

            "amis3.zz.an",
            "amis3.zz.ck",
            "amis3.zz.cp",
            "amis3.zz.ep",
            "amis3.zz.ft",
            "amis3.zz.gw",
            "amis3.zz.sr",
            
            "amis3.im.ac",
            "amis3.im.ad",
            "amis3.im.ei",
            "amis3.im.mi",
            "amis3.im.pi",
            "amis3.im.uc"            
    };
    private final JavaDependencyStore store;
    private static ApplicationContext ctx;

    public AmisView(JavaDependencyStore store) {
        this.store = store;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        for (String fromModule : AMIS_MODULES) {
            List<JavaDependency> list = store.findByFromModuleAndStartWithToModule(fromModule, "amis3", 3);
            list = list.stream()
                    .filter(jd -> !jd.getToModule().equals(fromModule) && !jd.getToModule().startsWith("amis3.fw") && !jd.getToModule().startsWith("amis3.vo"))
                    .collect(Collectors.toList());
            sb.append("#").append(fromModule.substring(6).replace(".", ",")).append("$").append("\n");
            sb.append(new DependencyFlatViewer(list).show()).append("\n");
            //sb.append("total : " + list.size()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //
        ctx = new AnnotationConfigApplicationContext(StoreConfig.class);
        JavaDependencyStore store = ctx.getBean(JavaDependencyStore.class);
        AmisView amisView = new AmisView(store);

        System.out.println(amisView.show());
    }
}
