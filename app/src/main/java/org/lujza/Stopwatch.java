package org.lujza;

public class Stopwatch implements Runnable {
    private final int LIMIT_SECONDS;
    int elapsedSeconds = 0;

    private final Object lock = new Object();
    private final int mode; // 1 for practicing mode, 2 for test simulation mode

    // Constructor to initialize the stopwatch with a given number of seconds and mode
    public Stopwatch(int limitSeconds) {
        LIMIT_SECONDS = limitSeconds;
        this.mode = limitSeconds > 0 ? 2 : 1;
    }

    public Stopwatch() {
        this(0);
    }

    // Method to start the countdown or count up based on the mode
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

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

    public void printTime() {
        int totalSeconds;
        if (mode == 1) {
            System.out.print("[Elapsed time: ");
            totalSeconds = elapsedSeconds;
        } else {
            System.out.print("[Remaining time: ");
            totalSeconds = LIMIT_SECONDS - elapsedSeconds;
        }
        int minutes = Math.abs(totalSeconds) / 60;
        int seconds = Math.abs(totalSeconds) % 60;
        System.out.printf("%d min %d s]", minutes, seconds);
    }

    public boolean isTimeUp() {
        return mode == 2 && LIMIT_SECONDS == elapsedSeconds;
    }
}