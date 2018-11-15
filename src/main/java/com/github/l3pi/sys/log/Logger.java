package com.github.l3pi.sys.log;

/**
 * This interface describes what's required for a custom logger to work.
 *
 * @see StringLogger as an implementation example.
 */
public interface Logger {
    void log(String m);
}
