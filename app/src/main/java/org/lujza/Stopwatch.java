package org.lujza;

public class Stopwatch implements Runnable {
    private int timeInSeconds;
    private final Object lock = new Object();
    private final int mode;
    private final Theme theme;
    private final PointsCounter pc;

    public Stopwatch(int timeInSeconds, int mode, Theme theme, PointsCounter pc) {
        this.timeInSeconds = timeInSeconds;
        this.mode = mode;
        this.theme = theme;
        this.pc = pc;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

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
            pc.printGrade(theme); // toto je take zapeklite :D
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