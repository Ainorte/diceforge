package com.github.l3pi.sys.log;

public class StringLogger implements Logger {
    @Override
    public void log(String m) {
        System.out.println(m);
    }
}
