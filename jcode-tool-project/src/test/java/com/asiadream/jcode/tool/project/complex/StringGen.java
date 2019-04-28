package com.asiadream.jcode.tool.project.complex;

public class StringGen {
    public static final String[][] projectNames = {
                {"bp" ,"bp"} ,
                {"ca" ,"am"} ,
                {"ca" ,"cm"} ,
                {"ca" ,"eh"} ,
                {"ca" ,"fa"} ,
                {"ca" ,"fm"} ,
                {"ca" ,"ga"} ,
                {"ca" ,"hr"} ,
                {"ca" ,"id"} ,
                {"ca" ,"ja"} ,
                {"ca" ,"sm"} ,
                {"ca" ,"wl"} ,
                {"cr" ,"cr"} ,
                {"em" ,"em"} ,
                {"et" ,"el"} ,
                {"et" ,"fm"} ,
                {"et" ,"ml"} ,
                {"et" ,"sy"} ,
                {"hm" ,"gc"} ,
                {"hm" ,"gm"} ,
                {"hm" ,"gr"} ,
                {"hm" ,"gs"} ,
                {"hm" ,"gx"} ,
                {"hm" ,"gz"} ,
                {"mc" ,"mr"} ,
                {"mc" ,"ms"} ,
                {"mc" ,"nr"} ,
                {"mc" ,"nu"} ,
                {"mc" ,"oo"} ,
                {"mc" ,"op"} ,
                {"mc" ,"ph"} ,
                {"rd" ,"rd"} ,
                {"sc" ,"sc"} ,
                {"sp" ,"ps"} ,
                {"sp" ,"sa"} ,
                {"sp" ,"sb"} ,
                {"sp" ,"sd"} ,
                {"sp" ,"se"} ,
                {"sp" ,"si"} ,
                {"sp" ,"ss"} ,
                {"sp" ,"sz"} ,
                {"wa" ,"ev"} ,
                {"wa" ,"wb"} ,
                {"wa" ,"wj"} ,
                {"wa" ,"wm"} ,
                {"wa" ,"wp"} ,
                {"wa" ,"wr"} ,
                {"zz" ,"an"} ,
                {"zz" ,"ck"} ,
                {"zz" ,"cp"} ,
                {"zz" ,"ep"} ,
                {"zz" ,"ft"} ,
                {"zz" ,"gw"} ,
                {"zz" ,"sr"} 
    };
    
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < projectNames.length; i++) {
            String name1 = projectNames[i][0];
            String name2 = projectNames[i][1];
            String name = name1 + "-" + name2;
            sb.append("<module>../").append(name).append("/").append(name).append("-share</module>").append("\n");
            sb.append("<module>../").append(name).append("/").append(name).append("-ext-stub</module>").append("\n");
        }
        
        System.out.println(sb.toString());
    }
}
