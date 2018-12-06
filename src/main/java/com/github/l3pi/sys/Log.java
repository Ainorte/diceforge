package com.github.l3pi.sys;

public class Log {
    public static class Colour {
        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";

        public static String fmt(String colour, String msg) {
            return colour + msg + RESET;
        }
    }

    public enum State { INFO, ACTION, STATUS, LOG }

    private static boolean IS_ENABLED = false;
    public static void enableLog() { IS_ENABLED = true; }
    public static void log(State s, String m) {
        String colour = Colour.WHITE;

        switch (s) {
            case LOG:
                colour = Colour.RED;
                break;
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
