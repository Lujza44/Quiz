package org.lujza;

/**
 * The {@code Stopwatch} class provides a timing mechanism for the quiz application, supporting both countdown and count-up functionalities
 * depending on the mode of the quiz. In test simulation mode, it counts down from a specified time, while in practice mode, it counts up indefinitely.
 * <p>
 * This class also interacts with the {@link PointsCounter} to display the user's grade at the end of a timed session in test simulation mode.
 * </p>
 */
public class Stopwatch implements Runnable {
    private int timeInSeconds;
    private final Object lock = new Object();
    private final int mode;
    private final Theme theme;
    private final PointsCounter pc;

    /**
     * Constructs a new {@code Stopwatch} with the specified time, mode, theme, and points counter.
     *
     * @param timeInSeconds The total time in seconds for the countdown timer, or the starting time for the count-up timer.
     * @param mode The quiz mode, where 2 indicates a timed test simulation and 1 indicates an untimed practice mode.
     * @param theme The current {@link Theme} of the quiz, used for grading purposes in test simulation mode.
     * @param pc The {@link PointsCounter} instance used to calculate and display the user's grade at the end of the timed session.
     */
    public Stopwatch(int timeInSeconds, int mode, Theme theme, PointsCounter pc) {
        this.timeInSeconds = timeInSeconds;
        this.mode = mode;
        this.theme = theme;
        this.pc = pc;
    }

    /**
     * Starts the stopwatch in a new thread, initiating the countdown or count-up behavior based on the mode.
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * The main run method that defines the behavior of the stopwatch when the thread starts.
     * In test simulation mode (mode 2), it counts down from the initial time and displays the user's grade when time runs out.
     * In practice mode (mode 1), it counts up, tracking the elapsed time without a predefined end point.
     */
    @Override
    public void run() {
        if (mode == 2) {
            while (timeInSeconds > 0) {
                synchronized (lock) {
                    timeInSeconds--;
                    try {
                        lock.wait(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Stopwatch was interrupted");
                        return;
                    }
                }
            }
            System.out.println();
            System.out.println("Time's up!");
            pc.printGrade(theme);
            System.exit(0);
        } else if (mode == 1) {
            timeInSeconds = 0;
            while (true) {
                synchronized (lock) {
                    timeInSeconds++;
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
    }

    /**
     * Displays the current time status. In practice mode, it shows the elapsed time since the start.
     * In test simulation mode, it shows the remaining time until the quiz ends.
     *
     * @param mode The quiz mode, determining how the time is displayed (elapsed or remaining).
     */
    public void getTime(int mode) {
        int minutes = Math.abs(timeInSeconds) / 60;
        int seconds = Math.abs(timeInSeconds) % 60;
        if (mode == 1) {
            System.out.print("Elapsed time: ");
        } else {
            System.out.print("Remaining time: ");
        }
        System.out.printf("%d min %d s%n", minutes, seconds);
    }
}