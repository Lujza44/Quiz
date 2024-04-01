package org.lujza;

import java.io.IOException;

/**
 * The {@code Main} class serves as the entry point for the quiz application.
 * It initializes the {@link Quiz} class and starts the quiz process.
 * <p>
 * This class is responsible for bootstrapping the application, setting the stage for user interaction and quiz functionality.
 * </p>
 */
public class Main {

    /**
     * The main method that starts the execution of the quiz application.
     * It creates an instance of the {@link Quiz} class and invokes its {@code runQuiz} method to begin the quiz process.
     *
     * @param args command-line arguments passed to the application (not used).
     * @throws IOException if an I/O error occurs during the execution of the quiz, such as loading questions from files.
     */
    public static void main(String[] args) throws IOException {
        Quiz quiz = new Quiz();
        quiz.runQuiz();
    }
}