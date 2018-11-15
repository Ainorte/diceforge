package com.github.l3pi.sys;

import com.github.l3pi.sys.log.Logger;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The goal for this class is to have an unified way to log every output instead of directly passing strings to println.
 * <p>
 * It'll be useful when, for example, we'll run a batch of 500 games:
 * We'll store every game log into a separate file (all stored inside the same folder), which will allow
 * for quick comparison and parsing.
 */
public class LogDAO {
    /**
     * ON_LOG = trigger the output loggers every time something is logged
     * ON_REQUEST = only trigger the output on manual handleIncomingLogs() call
     */
    public enum LogOutputPolicy {
        ON_LOG,
        ON_REQUEST
    }

    private static LogDAO instance;

    public static LogDAO initialize(List<Logger> loggers, LogOutputPolicy policy) {
        return (instance = new LogDAO(loggers, policy));
    }

    public static LogDAO initialize(List<Logger> loggers) {
        return initialize(loggers, LogOutputPolicy.ON_REQUEST);
    }

    public static void log(String m) {
        if (instance == null) {
            System.err.println("Logger not initialized, but was requested to log: " + m);
        } else instance._log(m);
    }

    private final Queue<String> logQueue = new ConcurrentLinkedQueue<>();

    private final List<Logger> loggers;

    private final LogOutputPolicy policy;

    private LogDAO(List<Logger> loggers, LogOutputPolicy policy) {
        this.loggers = loggers;
        this.policy = policy;
    }

    private void _log(String m) {
        logQueue.add(m);
        if (policy == LogOutputPolicy.ON_LOG) {
            handleIncomingLogs();
        }
    }

    private void handleIncomingLogs() {
        while (!logQueue.isEmpty()) {
            final String m = logQueue.poll();
            loggers.forEach(l -> l.log(m));
        }
    }
}
