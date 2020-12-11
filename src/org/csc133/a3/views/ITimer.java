package org.csc133.a3.views;

/**
 * The interface Timer.
 */
public interface ITimer {
    /**
     * Reset elapsed time.
     */
    public void resetElapsedTime();

    /**
     * Start elapsed time.
     */
    public void startElapsedTime();

    /**
     * Stop elapsed time.
     */
    public void stopElapsedTime();

    /**
     * Gets elapsed time.
     *
     * @return the elapsed time
     */
    public long getElapsedTime();
}
