package com.asiadream.jcode.tool.project.complex;

public class AmisConstants {
    //
    public static final String[][] projectNames = {
            {"bp" ,"bp" ,"bp"},  //경영기획, bizplan
            {"ca" ,"am" ,"am"},  //자산관리, asset
            {"ca" ,"cm" ,"cm"},  //공사관리, construct
            {"ca" ,"eh" ,"eh"},  //EHS,   ehs
            {"ca" ,"fa" ,"fa"},  //재무회계, financial
            {"ca" ,"fm" ,"fm"},  //설비관리, facility
            {"ca" ,"ga" ,"ga"},  //일반행정, generaladmin
            {"ca" ,"hr" ,"hr"},  //인사관리, humanresource
            {"ca" ,"id" ,"id"},  //물류관리, logistics
            {"ca" ,"ja" ,"ja"},  //법무관리, legal
            {"ca" ,"sm" ,"sm"},  //공간관리, space
            {"ca" ,"wl" ,"wl"},  //의료외사업, nonmedical
            {"cr" ,"cr" ,"cr"},  //고객관리, customer
            {"em" ,"em" ,"em"},  //교육,    education
            {"et" ,"el" ,"el"},  //전자동의서, electronics
            {"et" ,"fm" ,"fm"},  //서식생성기, formgen
            {"et" ,"ml" ,"ml"},  //모바일,   mobile
            {"et" ,"sy" ,"sy"},  //시스템연계, system
            {"hm" ,"gc" ,"gc"},  //건진상담, counseling
            {"hm" ,"gm" ,"gm"},  //건진고객, gcustomer
            {"hm" ,"gr" ,"gr"},  //건진예약, reservation
            {"hm" ,"gs" ,"gs"},  //건진수납, gpayment
            {"hm" ,"gx" ,"gx"},  //건진검사, inspection
            {"hm" ,"gz" ,"gz"},  //건진공통, gcommon
            {"mc" ,"mr" ,"mr"},  //기록, record
            {"mc" ,"ms" ,"ms"},  //의무기록, medirecord
            {"mc" ,"nr" ,"nr"},  //간호, nurse
            {"mc" ,"nu" ,"nu"},  //영양, nutrition
            {"mc" ,"oo" ,"oo"},  //환자진료, order
            {"mc" ,"op" ,"op"},  //수술마취회복, recovery
            {"mc" ,"ph" ,"ph"},  //약국, pharmacy
            {"rd" ,"rd" ,"rd"},  //연구, research
            {"sc" ,"sc" ,"sc"},  //사회공헌, contribution
            {"sp" ,"ps" ,"ps"},  //PACS, pacs
            {"sp" ,"sa" ,"sa"},  //CIS, cis
            {"sp" ,"sb" ,"sb"},  //혈액은행, blood
            {"sp" ,"sd" ,"sd"},  //헌혈실, donation
            {"sp" ,"se" ,"se"},  //환자검사, examination
            {"sp" ,"si" ,"si"},  //감염관리, infaction
            {"sp" ,"ss" ,"ss"},  //검체검사, specimens
            {"sp" ,"sz" ,"sz"},  //검사공통, scommon
            {"wa" ,"ev" ,"ev"},  //적정성평가, appropriateness
            {"wa" ,"wb" ,"wb"},  //보험, insurance
            {"wa" ,"wj" ,"wj"},  //접수, receipt
            {"wa" ,"wm" ,"wm"},  //수납, payment
            {"wa" ,"wp" ,"wp"},  //환자, patient
            {"wa" ,"wr" ,"wr"},  //진료협력, cooperation
            {"zz" ,"an" ,"an"},  //ADMIN, admin
            {"zz" ,"ck" ,"ck"},  //공통도구, tools
            {"zz" ,"cp" ,"cp"},  //공통환자정보, patientinfo
            {"zz" ,"ep" ,"ep"},  //행정공통, acommon
            {"zz" ,"ft" ,"ft"},  //기반기술, infra
            {"zz" ,"gw" ,"gw"},  //그룹웨어, groupware
            {"zz" ,"sr" ,"sr"}   //서비스요청, serreq
    };
    
    public static final String[][] projectNamesMdm = {
            {"im", "ac", "ac"},
            {"im", "ad", "ad"},
            {"im", "ei", "ei"},
            {"im", "mi", "mi"},
            {"im", "pi", "pi"},
            {"im", "uc", "uc"}
    };
    
    public static final String[][] projectNamesEtc = {
            {"zz", "ck", "ck"},
            {"zz", "ft", "ft"}
    };
    
    public static final Object[][] projectDependencies = {
            {"bp", "bp", new String[]{
                    "zz.gw"
                    }},
            {"ca", "am", new String[]{
                    "bp.bp",
                    "ca.fm",
                    "ca.id",
                    "et.sy",
                    "zz.gw"
                    }},
            {"ca", "cm", new String[]{
                    "ca.fm",
                    "et.sy",
                    "zz.gw"
                    }},
            {"ca", "eh", new String[]{
                    "ca.fa",
                    "ca.fm",
                    "et.sy",
                    "mc.nr",
                    "rd.rd",
                    "zz.ck",
                    "zz.gw"
                    }},
            {"ca", "fa", new String[]{
                    "bp.bp",
                    "ca.hr",
                    "ca.id",
                    "sp.ss",
                    "zz.an",
                    "zz.gw"
                    }},
            {"ca", "fm", new String[]{
                    "ca.id",
                    "mc.oo",
                    "zz.gw"
                    }},
            {"ca", "ga", new String[]{
                    "bp.bp",
                    "ca.fa",
                    "ca.hr",
                    "ca.ja",
                    "em.em",
                    "et.sy",
                    "zz.ep",
                    "zz.gw"
                    }},
            {"ca", "hr", new String[]{
                    "bp.bp",
                    "ca.fa",
                    "ca.sm",
                    "em.em",
                    "et.sy",
                    "wa.wj",
                    "zz.an",
                    "zz.ck",
                    "zz.ep",
                    "zz.gw"
                    }},
            {"ca", "id", new String[]{
                    "ca.am",
                    "ca.fa",
                    "et.sy",
                    "mc.oo",
                    "mc.op",
                    "mc.ph",
                    "zz.an",
                    "zz.ck",
                    "zz.gw"
                    }},
             {"ca", "ja", new String[]{
                    "zz.gw"
                    }},
             {"ca", "sm", new String[]{
                    "bp.bp",
                    "et.sy",
                    "zz.gw"
                    }},
             {"ca", "wl", new String[]{
                    "ca.fa",
                    "ca.hr",
                    "rd.rd",
                    "wa.wp",
                    "zz.ep",
                    "zz.gw",
                    }},
             {"cr", "cr", new String[]{
                    "ca.fa",
                    "ca.id",
                    "et.sy",
                    "wa.wm",
                    "wa.wp",
                    "zz.gw"
                    }},
             {"em", "em", new String[]{
                    "ca.am",
                    "ca.fa",
                    "ca.hr",
                    "et.sy",
                    "zz.ep",
                    "zz.gw"
                    }},
             {"et", "el", new String[]{
                    "zz.ck",
                    "zz.cp"
                    }},
             {"et", "fm", new String[]{
                    "mc.mr",
                    "wa.wp",
                    }},
             {"et", "sy", new String[]{
                    "zz.ck"
                    }},
             {"hm", "gc", new String[]{
                    "et.sy",
                    "hm.gm",
                    "hm.gr",
                    "hm.gx",
                    "hm.gz",
                    "sp.se",
                    "sp.ss",
                    "wa.wj",
                    "wa.wp",
                    "zz.ck"
                    }},
             {"hm", "gm", new String[]{
                    "et.sy",
                    "hm.gc",
                    "hm.gr",
                    "hm.gs",
                    "hm.gx",
                    "hm.gz",
                    "sp.se",
                    "sp.ss",
                    "wa.wj",
                    "wa.wp"
                    }},
             {"hm", "gr", new String[]{
                    "cr.cr",
                    "et.sy",
                    "hm.gc",
                    "hm.gm",
                    "hm.gs",
                    "hm.gx",
                    "hm.gz",
                    "mc.oo",
                    "sp.se",
                    "wa.wj",
                    "wa.wp"
                    }},
             {"hm", "gs", new String[]{
                    "ca.fa",
                    "et.sy",
                    "hm.gm",
                    "hm.gr",
                    "hm.gx",
                    "hm.gz",
                    "sp.se",
                    "sp.ss",
                    "wa.wm",
                    "wa.wp",
                    "zz.ck"
                    }},
             {"hm", "gx", new String[]{
                    "hm.gc",
                    "hm.gr",
                    "hm.gs",
                    "hm.gz",
                    "mc.mr",
                    "mc.nr",
                    "mc.oo",
                    "sp.se",
                    "sp.ss",
                    "zz.ck"
                    }},
             {"hm", "gz", new String[]{
                    "hm.gc",
                    "hm.gs",
                    "mc.oo",
                    "sp.ss",
                    "wa.wm"
                    }},
             {"mc", "mr", new String[]{
                    "et.fm",
                    "et.sy",
                    "mc.ms",
                    "mc.nr",
                    "mc.nu",
                    "mc.oo",
                    "mc.op",
                    "mc.ph",
                    "sp.sa",
                    "sp.se",
                    "wa.wj",
                    "zz.ck",
                    "zz.gw"
                    }},
             {"mc", "ms", new String[]{
                    "ca.fa",
                    "et.sy",
                    "mc.mr",
                    "wa.wm",
                    "zz.ck",
                    "zz.gw"
                    }},
             {"mc", "nr", new String[]{
                    "ca.eh",
                    "ca.id",
                    "et.el",
                    "et.fm",
                    "et.sy",
                    "mc.mr",
                    "mc.nu",
                    "mc.oo",
                    "mc.ph",
                    "sp.sb",
                    "sp.se",
                    "sp.ss",
                    "wa.wj",
                    "wa.wm",
                    "wa.wp",
                    "wa.wr",
                    "zz.an",
                    "zz.ck"
                    }},
             {"mc", "nu", new String[]{
                    "et.fm",
                    "et.sy",
                    "mc.mr",
                    "mc.oo",
                    "sp.se",
                    "wa.wj"
                    }},
             {"mc", "oo", new String[]{
                    "bp.bp",
                    "ca.am",
                    "ca.id",
                    "et.el",
                    "et.fm",
                    "et.sy",
                    "hm.gc",
                    "hm.gr",
                    "mc.mr",
                    "mc.nr",
                    "mc.nu",
                    "mc.op",
                    "mc.ph",
                    "sc.sc",
                    "sp.sa",
                    "sp.sb",
                    "sp.se",
                    "sp.si",
                    "sp.ss",
                    "wa.wb",
                    "wa.wj",
                    "wa.wm",
                    "wa.wp",
                    "wa.wr",
                    "zz.an",
                    "zz.ck"
                    }},
                    {"mc", "op", new String[]{
                    "ca.id",
                    "et.sy",
                    "mc.oo",
                    "mc.ph",
                    "sp.se",
                    "wa.wj",
                    "wa.wm"
                    }},
                    {"mc", "ph", new String[]{
                    "ca.id",
                    "et.fm",
                    "mc.nr",
                    "mc.oo",
                    "mc.op",
                    "sp.ss",
                    "wa.wm",
                    "wa.wp",
                    "zz.ck"
                    }},
                    {"rd", "rd", new String[]{
                    "ca.fa",
                    "ca.hr",
                    "et.sy",
                    "sp.sa",
                    "wa.wm",
                    "zz.gw"
                    }},
                    {"sc", "sc", new String[]{
                    "ca.hr",
                    "wa.wp",
                    "zz.gw"
                    }},
                    {"sp", "ps", new String[]{
                    "et.sy",
                    "mc.mr",
                    "mc.oo",
                    "sp.se",
                    "sp.ss",
                    "wa.wj",
                    "zz.an",
                    "zz.sr"
                    }},
                    {"sp", "sa", new String[]{
                    "et.sy",
                    "mc.nr",
                    "mc.oo",
                    "rd.rd",
                    "sp.ss"
                    }},
                    {"sp", "sb", new String[]{
                    "et.sy",
                    "mc.nr",
                    "mc.oo",
                    "sp.sd",
                    "sp.ss",
                    "wa.wm",
                    "zz.an",
                    "zz.ck"
                    }},
                    {"sp", "sd", new String[]{
                    "et.sy",
                    "sp.sb",
                    "sp.ss",
                    "wa.wp"
                    }},
                    {"sp", "se", new String[]{
                    "ca.fa",
                    "et.fm",
                    "et.sy",
                    "hm.gr",
                    "hm.gx",
                    "mc.mr",
                    "mc.nr",
                    "mc.oo",
                    "mc.ph",
                    "sp.ps",
                    "sp.si",
                    "sp.ss",
                    "wa.wj",
                    "wa.wm",
                    "wa.wp",
                    "wa.wr",
                    "zz.an",
                    "zz.ck",
                    "zz.cp"
                    }},
                    {"sp", "si", new String[]{
                    "ca.id",
                    "mc.oo",
                    "sp.ss",
                    "wa.wj"
                    }},
                    {"sp", "ss", new String[]{
                    "et.el",
                    "et.fm",
                    "et.sy",
                    "hm.gr",
                    "mc.mr",
                    "mc.oo",
                    "sp.ps",
                    "sp.se",
                    "sp.si",
                    "wa.wm",
                    "wa.wp",
                    "zz.an",
                    "zz.ck"
                    }},
                    {"sp", "sz", new String[]{
                    "et.sy",
                    "sp.ss",
                    "zz.ck"
                    }},
                    {"wa", "ev", new String[]{
                    "mc.oo",
                    "wa.wb",
                    "wa.wp"
                    }},
                    {"wa", "wb", new String[]{
                    "ca.fa",
                    "ca.ga",
                    "ca.sm",
                    "et.sy",
                    "mc.oo",
                    "sp.se",
                    "sp.ss",
                    "wa.wj",
                    "wa.wm",
                    "wa.wp",
                    "wa.wr",
                    "zz.an",
                    "zz.gw"
                    }},
                    {"wa", "wj", new String[]{
                    "ca.hr",
                    "et.sy",
                    "hm.gc",
                    "hm.gm",
                    "mc.mr",
                    "mc.nr",
                    "mc.nu",
                    "mc.oo",
                    "mc.ph",
                    "sp.sa",
                    "sp.se",
                    "wa.wb",
                    "wa.wm",
                    "wa.wp",
                    "wa.wr",
                    "zz.an",
                    "zz.ck"
                    }},
                    {"wa", "wm", new String[]{
                    "ca.fa",
                    "ca.id",
                    "et.sy",
                    "hm.gs",
                    "mc.mr",
                    "mc.nr",
                    "mc.nu",
                    "mc.oo",
                    "mc.ph",
                    "sc.sc",
                    "sp.sa",
                    "sp.sb",
                    "sp.se",
                    "sp.ss",
                    "wa.wb",
                    "wa.wj",
                    "wa.wp",
                    "zz.an",
                    "zz.ck",
                    "zz.gw"
                    }},
                    {"wa", "wp", new String[]{
                    "ca.eh",
                    "ca.id",
                    "et.fm",
                    "et.sy",
                    "mc.mr",
                    "mc.ms",
                    "mc.nr",
                    "mc.nu",
                    "mc.oo",
                    "mc.op",
                    "mc.ph",
                    "sp.se",
                    "sp.si",
                    "sp.ss",
                    "wa.wj",
                    "wa.wm",
                    "wa.wr",
                    "zz.an",
                    "zz.ck"
                    }},
                    {"wa", "wr", new String[]{
                    "cr.cr",
                    "et.fm",
                    "et.sy",
                    "mc.oo",
                    "wa.wj",
                    "wa.wp",
                    "zz.ck"
                    }},
                    {"zz", "an", new String[]{
                    }},
                    {"zz", "ck", new String[]{
                    "mc.ms",
                    "mc.oo",
                    "zz.an"
                    }},
                    {"zz", "cp", new String[]{
                    "mc.mr",
                    "mc.nr",
                    "mc.nu",
                    "mc.oo",
                    "sp.si",
                    "wa.wj",
                    "wa.wm",
                    "wa.wp",
                    "wa.wr",
                    "zz.an"
                    }},
                    {"zz", "ep", new String[]{
                    "bp.bp",
                    "ca.hr"
                    }},
                    {"zz", "ft", new String[]{
                    "et.sy"
                    }},
                    {"zz", "gw", new String[]{
                    "zz.ck"
                    }},
                    {"zz", "sr", new String[]{
                    "et.sy",
                    "zz.gw"
                    }},
                    
                    {"im", "ac", new String[]{
                            "im.mi",
                            "im.uc"
                            }},
                    {"im", "ad", new String[]{
                            "im.mi",
                            "im.uc"
                            }},
                    {"im", "ei", new String[]{
                            "im.ad",
                            "im.mi",
                            "im.uc"
                            }},
                    {"im", "mi", new String[]{
                            "im.uc"
                            }},
                    {"im", "pi", new String[]{
                            "im.ad",
                            "im.mi",
                            "im.uc"
                            }},
                    {"im", "uc", new String[]{
                            "im.ac",
                            "im.ad",
                            "im.ei",
                            "im.mi",
                            "im.pi"
                            }}
    };
}
