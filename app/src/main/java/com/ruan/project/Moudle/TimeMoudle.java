package com.ruan.project.Moudle;

/**
 * Created by Administrator on 2016/8/4.
 */
public class TimeMoudle {

//    private static TimeMoudle timeMoudle = null;


    public static String[] switch1;
    public static String[] switch2;
    public static String[] switch3;
    public static String[] switch4;

//    public synchronized static TimeMoudle getInstance() {
//        if (timeMoudle == null)
//            timeMoudle = new TimeMoudle();
//        return timeMoudle;
//    }


    public static String[] getSwitch1() {
        if (switch1 == null)
            switch1 = new String[]{"--", "--"};
        return switch1;
    }

    public static void setSwitch1(String[] switch1) {
        TimeMoudle.switch1 = switch1;
    }

    public static String[] getSwitch2() {
        if (switch2 == null)
            switch2 = new String[]{"--", "--"};
        return switch2;
    }

    public static void setSwitch2(String[] switch2) {
        TimeMoudle.switch2 = switch2;
    }

    public static String[] getSwitch3() {
        if (switch3 == null)
            switch3 = new String[]{"--", "--"};
        return switch3;
    }

    public static void setSwitch3(String[] switch3) {
        TimeMoudle.switch3 = switch3;
    }

    public static String[] getSwitch4() {
        if (switch4 == null)
            switch4 = new String[]{"--", "--"};
        return switch4;
    }

    public static void setSwitch4(String[] switch4) {
        TimeMoudle.switch4 = switch4;
    }
}
