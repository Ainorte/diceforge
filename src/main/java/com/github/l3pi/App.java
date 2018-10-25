package com.github.l3pi;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        if (args.length != 2) {
            System.err.printf("Usage: %s <run-count> <player-count(>=2)>", args[0]);
        }
    }
}
