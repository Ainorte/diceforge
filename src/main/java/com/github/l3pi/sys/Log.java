package com.github.l3pi.sys;

public class Log {
    private static boolean IS_ENABLED = false;
    public static void enableLog() { IS_ENABLED = true; }
    public static void log(String m) { if (IS_ENABLED) { System.out.println(m); } }
    public static void log() { log(""); }
}
