package org.lujza.quiz;

import org.lujza.quiz.model.Question;
import org.lujza.quiz.model.Theme;
import org.lujza.quiz.util.JsonReader;
import org.lujza.quiz.util.PointsCounter;
import org.lujza.quiz.util.Stopwatch;

import java.io.IOException;
import java.util.*;


/**
 * The {@code Quiz} class facilitates a quiz application designed to help users prepare for exams by practicing various topics.
 * This application allows users to select from different themes for revision, choose between practice or test simulation modes,
 * and answers questions accordingly. User performance is tracked and scored based on the accuracy of their answers.
 * <p>
 * The quiz flow includes mode selection, theme selection based on loaded data, and timed questions with scoring.
 * </p>
 *
 * @author Lujza Milotova
 */
public class Quiz {
    /**
     * A scanner to read user input from the console. It's used throughout the application to capture user responses
     * to various prompts, including mode selection, theme selection, and answers to questions.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * An instance of {@link JsonReader} used to load quiz themes and questions from JSON files located in the specified directory.
     * This allows the quiz application to dynamically load its content from a structured JSON format.
     */
    private final JsonReader jsonReader = new JsonReader("data");

    /**
     * A {@link PointsCounter} instance for tracking and calculating the user's score based on their answers to quiz questions.
     * It supports adding points for correct answers, partial points for nearly correct answers, and calculating the final grade.
     */
    private final PointsCounter pointsCounter = new PointsCounter();

    /**
     * The current mode of the quiz. It determines how the quiz behaves and interacts with the user.
     * Mode 1 corresponds to the practice mode, while mode 2 corresponds to the test simulation mode.
     */
    private int mode;

    /**
     * The current theme selected for the quiz. The theme encompasses a collection of questions related to a specific topic
     * and is chosen by the user at the beginning of the quiz session.
     */
    private Theme theme;

    /**
     * A {@link Stopwatch} instance used to manage the timing aspect of the quiz in test simulation mode.
     * It tracks the remaining time for the quiz and can also track the elapsed time in practice mode.
     */
    private Stopwatch stopwatch;

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

    /**
     * Runs the main quiz flow, including mode selection, theme selection, and the question-answer session.
     * The method orchestrates the quiz by loading themes, presenting questions, and scoring answers,
     * concluding with a summary of the user's performance.
     *
     * @throws IOException if there is an error loading the quiz data.
     */
    public void runQuiz() throws IOException {

        mode = prompt();
        List<Theme> themes = jsonReader.load();
        int topic = prompt(themes);
        theme = themes.get(topic);

        System.out.println();
        System.out.println(theme.getDescription());

        List<Question> questions = theme.getQuestions();
        Collections.shuffle(questions);

        if (mode == 2) {
            System.out.println(theme.getScoringText());
            stopwatch = new Stopwatch(questions.size() * 30 + 60); // 30 sec for each question + 1 min for reading the instructions
        } else {
            stopwatch = new Stopwatch();
        }

        stopwatch.start();

        for (Question question : questions) {
            System.out.println();
            List<String> answers = prompt(question);
            if (answers == null) {
                break;
            }
            question.setAnswered(true);
            evaluate(question, answers);
        }
        exit();
    }

    /**
     * Prompts the user to select the quiz mode, offering a choice between practice mode and test simulation.
     * <p>
     * This method guides the user through selecting the operational mode of the quiz,
     * providing an intuitive interface for this initial choice.
     * </p>
     *
     * @return the selected mode as an integer, where 1 represents practice mode and 2 represents test simulation.
     */
    private int prompt() {
        System.out.println("""
                Welcome to the application, that will help you prepare for your exams!
                Application has two modes: practice mode and a test simulation.
                Do you want to practice or simulate a test? Enter 1 for practice mode or 2 for a test simulation.""");
        return getUserChoice(2);
    }

    /**
     * Prompts the user to select a theme for the quiz from a list of available themes.
     * Themes are presented based on the loaded data, and the user selects a theme to focus on for the quiz session.
     *
     * @param themes the list of themes available for selection.
     * @return the index of the selected theme, adjusted for 0-based indexing.
     */
    private int prompt(List<Theme> themes) {
        System.out.println();
        for (int i = 0; i < themes.size(); i++) {
            System.out.printf("%s. %s%n", i + 1, themes.get(i).getName());
        }
        return getUserChoice(themes.size()) - 1;
    }

    /**
     * Presents a question to the user and prompts for an answer. Supports multiple answers.
     *
     * @param question the question to be answered by the user.
     * @return a list of strings representing the user's answer(s).
     */
    private List<String> prompt(Question question) {
        System.out.println(question.getText());
        Map<String, String> shuffledAnswers = new HashMap<>();

        if (question.isSingleAnswer() || question.isMultipleAnswer()) { // print options if there are some
            shuffledAnswers = question.getAllAnswersMap();
            for (Map.Entry<String, String> element : shuffledAnswers.entrySet()) {
                System.out.printf("%s) %s%n", element.getKey(), element.getValue());
            }
        }
        stopwatch.printTime();
        System.out.print(" Your answer: ");
        String input = getUserAnswer().toLowerCase(); // the user can write any string
        if (stopwatch.isTimeUp()) {
            return null;
        } else {
            return getAnswerList(input, shuffledAnswers, question);
        }
    }

    /**
     * Processes the user's choice, ensuring it is within the valid range for the current context (e.g., mode selection, theme selection).
     * This method includes validation to ensure the user's input is a valid selection and prompts for re-entry if necessary.
     *
     * @param maxOption the maximum valid option number.
     * @return the user's choice as an integer.
     */
    private int getUserChoice(int maxOption) {
        System.out.print("Your choice: ");
        int choice;

        while (true) {
            try {
                String next = scanner.nextLine();
                if (next.equalsIgnoreCase("EXIT")) {
                    System.exit(0);
                }

                choice = Integer.parseInt(next);
                if (choice > 0 && choice <= maxOption) {
                    return choice;
                } else {
                    System.out.print("Invalid input. Please enter a valid number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    /**
     * Retrieves a user's answer as a string. This method is used for retrieving user's answer in any format.
     *
     * @return the user's answer as a string.
     */
    private String getUserAnswer() {
        String next = scanner.nextLine();
        if (next.equalsIgnoreCase("EXIT")) {
            System.exit(0);
        }
        return next;
    }

    /**
     * Converts the user's input into a list of answers, validating against the available options for questions with predefined answers.
     *
     * @param input           the raw input from the user.
     * @param shuffledAnswers a map of the available answers with their identifiers.
     * @param question        the question being answered, for context.
     * @return a list of answers selected by the user.
     */
    private List<String> getAnswerList(String input, Map<String, String> shuffledAnswers, Question question) {

        if (question.isTextInput()) {
            return List.of(input); // list of one element
        } else {
            String[] splitInput = input.split("[,\\s;]+");
            List<String> answers = new ArrayList<>();
            for (String ans : splitInput) {
                if (ans.isEmpty()) {
                    continue;
                }
                if (!shuffledAnswers.containsKey(ans)) {
                    return new ArrayList<>();
                }
                answers.add(shuffledAnswers.get(ans));
            }
            return answers;
        }
    }

    /**
     * Evaluates the user's answers against the correct answers, updates scores, and provides feedback based on the evaluation.
     *
     * @param question the question that was answered by the user.
     * @param answers  the user's answers for evaluation.
     */
    private void evaluate(Question question, List<String> answers) {
        List<String> correctAnswers = question.getCorrectAnswers();
        Set<String> set1 = new HashSet<>(correctAnswers);
        Set<String> set2 = new HashSet<>(answers);

        if (question.isTextInput()) {
            set1 = Set.of(correctAnswers.getFirst().toLowerCase());
        }

        if (set1.equals(set2)) {
            printIfMode1("Correct!");
            pointsCounter.addPoints(question);
            return;
        }

        if (question.isTextInput()) {
            printIfMode1("Wrong. Correct answer: %s", correctAnswers.getFirst());
        } else {
            printIfMode1("Wrong. Correct answer(s):");
            for (Map.Entry<String, String> element : question.getAllAnswersMap().entrySet()) {
                if (correctAnswers.contains(element.getValue())) {
                    printIfMode1("%s) %s", element.getKey(), element.getValue());
                }
            }
            pointsCounter.addPartialPoints(set1, set2, question);
        }
    }

    /**
     * Prints feedback if the quiz is in mode 1 (practice mode). This method allows for selective feedback
     * based on the operational mode of the quiz.
     *
     * @param format a {@link CharSequence} that contains the format string.
     * @param args   the arguments referenced by the format specifiers in the format string.
     */
    private void printIfMode1(CharSequence format, Object... args) {
        if (mode == 1) {
            System.out.printf(format + "%n", args);
        }
    }

    /**
     * Concludes the quiz session, displaying final messages and scores. This method also handles any cleanup or final state changes required.
     */
    public void exit() {
        System.out.println();
        if (mode == 1) {
            System.out.println("You have answered all of the questions from this topic.");
        } else {
            long count = theme.getQuestions().stream().filter(Question::isAnswered).count();
            System.out.printf("You have answered %s/%s questions in this test.%n", count, theme.getQuestions().size());
            pointsCounter.printGrade(theme);
        }
        System.exit(0); // to also stop the stopwatch
    }
}