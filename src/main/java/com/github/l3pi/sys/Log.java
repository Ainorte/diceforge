package com.github.l3pi.sys;

public class Log {
    public static void log(String s, String m) {
        if (s.equals(State.SYS) || IS_ENABLED) {
            System.out.println(State.fmt(s, m));
        }
    }

    private static boolean IS_ENABLED = false;

    public static void enableLog() {
        IS_ENABLED = true;
    }

    public static void log(String m) {
        log(State.WHITE, m);
    }

    public static void log() {
        log("");
    }

    public static void describeColourCodes() {
        final boolean was_enabled = IS_ENABLED;
        enableLog();
        log(State.WHITE, "Charte de couleurs");
        log("\t- Jaune: Informations système, état d'exécution des parties");
        log("\t- Rouge: Informations de détail d'avancement d'une partie");
        log("\t- Vert: Informations concernant une action effectuée par un joueur");
        log("\t- Bleu: Informations d'état du jeu");
        IS_ENABLED = was_enabled;
    }

    public static class State {
        static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String SYS = "\u001B[33m[SYS] ";
        public static final String BLUE = "\u001B[34m";
        static final String WHITE = "\u001B[37m";

        public static String fmt(String State, String msg) {
            return State + msg + RESET;
        }
    }
}
