package org.lujza.quiz.model;

/**
 * A {@code Stopwatch} class that implements a simple stopwatch functionality which can either count up from 0 or count down
 * from a specified limit of seconds. This class supports two modes: practicing mode (mode 1), where it counts up indefinitely,
 * and test simulation mode (mode 2), where it counts down from a specified limit.
 * <p>
 * This class is designed to run in its own thread, allowing the stopwatch to operate asynchronously from the main application flow.
 * </p>
 */
public class Stopwatch implements Runnable {

    /**
     * The limit in seconds for the stopwatch. In test simulation mode, this is the countdown start point.
     * In practicing mode, this field is not used.
     */
    private final int LIMIT_SECONDS;

    /**
     * The number of seconds that have elapsed since the stopwatch was started.
     */
    int elapsedSeconds = 0;

    /**
     * An object used as a lock for synchronizing access to shared resources within the thread.
     */
    private final Object lock = new Object();

    /**
     * The mode of the stopwatch: 1 for practicing mode, 2 for test simulation mode.
     * The mode is determined by the {@code limitSeconds} passed to the constructor: a positive number sets the mode to 2,
     * otherwise, it defaults to 1.
     */
    private final int mode;

    /**
     * Constructs a {@code Stopwatch} instance with a specific time limit and determines the mode based on the limit.
     * A positive {@code limitSeconds} sets the mode to test simulation (2), otherwise it defaults to practicing mode (1).
     *
     * @param limitSeconds the time limit in seconds for the countdown. A positive value initiates test simulation mode,
     *                     zero or negative value initiates practicing mode.
     */
    public Stopwatch(int limitSeconds) {
        LIMIT_SECONDS = limitSeconds;
        this.mode = limitSeconds > 0 ? 2 : 1;
    }

    /**
     * Default constructor for {@code Stopwatch}. Initializes the stopwatch in practicing mode with no time limit.
     */
    public Stopwatch() {
        this(0);
    }

    /**
     * Starts the stopwatch in a new thread. Depending on the mode, it will either count up from 0 or count down from the
     * specified {@code LIMIT_SECONDS}.
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * The main run method for the stopwatch thread. It handles the counting logic, incrementing or decrementing the elapsed time
     * and stopping the countdown when the limit is reached in test simulation mode.
     */
    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                elapsedSeconds++;
                if (mode == 2 && elapsedSeconds == LIMIT_SECONDS) {
                    break;
                }
                try {
                    lock.wait(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Stopwatch was interrupted");
                    return;
                }
            }
        }
    }

    /**
     * Prints the current time of the stopwatch in HH:MM:SS format. The displayed time depends on the mode:
     * elapsed time in practicing mode or remaining time in test simulation mode.
     */
    public void printTime() {
        int totalSeconds;
        if (mode == 1) {
            totalSeconds = elapsedSeconds;
        } else {
            totalSeconds = LIMIT_SECONDS - elapsedSeconds;
        }
        int hours = totalSeconds / 3600; // Calculate hours
        int minutes = (totalSeconds % 3600) / 60; // Calculate minutes
        int seconds = totalSeconds % 60; // Calculate seconds

        // Adjust the printf format to include hours
        System.out.printf("[%02d:%02d:%02d]", hours, minutes, seconds);
    }
    /**
     * Checks if the time limit has been reached in test simulation mode.
     *
     * @return {@code true} if in test simulation mode and the limit has been reached; {@code false} otherwise.
     */
    public boolean isTimeUp() {
        return mode == 2 && LIMIT_SECONDS == elapsedSeconds;
    }
}