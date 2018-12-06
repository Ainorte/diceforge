package com.github.l3pi.sys;

public class Log {
    private static class Colour {
        static final String RESET = "\u001B[0m";
        static final String BLACK = "\u001B[30m";
        static final String RED = "\u001B[31m";
        static final String GREEN = "\u001B[32m";
        static final String YELLOW = "\u001B[33m";
        static final String BLUE = "\u001B[34m";
        static final String PURPLE = "\u001B[35m";
        static final String CYAN = "\u001B[36m";
        static final String WHITE = "\u001B[37m";

        static String fmt(String colour, String msg) {
            return colour + msg + RESET;
        }
    }

    public enum State { INFO, ACTION, STATUS }

    private static boolean IS_ENABLED = false;
    public static void enableLog() { IS_ENABLED = true; }
    public static void log(State s, String m) {
        String colour = Colour.WHITE;

        switch (s) {
            case ACTION:
                colour = Colour.GREEN;
                break;
            case STATUS:
                colour = Colour.BLUE;
        }

        if (IS_ENABLED) {
            System.out.println(Colour.fmt(colour, m));
        }
    }
    public static void log(String m) { log(State.INFO, m); }
    public static void log() { log(""); }
}
