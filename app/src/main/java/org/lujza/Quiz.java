package org.lujza;

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
    private final Scanner scanner = new Scanner(System.in);
    private final JsonReader jsonReader = new JsonReader("data");
    private final PointsCounter pointsCounter = new PointsCounter();
    private int mode;
    private Theme theme;

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
        if (mode == 2) {
            System.out.println(theme.getScoringText());
        }

        List<Question> questions = theme.getQuestions();
        Collections.shuffle(questions);

        Stopwatch stopwatch = new Stopwatch(questions.size() * 30 + 60, mode, theme, pointsCounter); // 30 sec for each question + 1 min for reading the instructions
        stopwatch.start();

        for (Question question : questions) {
            System.out.println();
            stopwatch.getTime(mode);
            List<String> answers = prompt(question);
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
        System.out.println();
        System.out.println(question.getText());
        Map<String, String> shuffledAnswers = new HashMap<>();

        if (question.isSingleAnswer() || question.isMultipleAnswer()) { // print options if there are some
            shuffledAnswers = question.getAllAnswersMap();
            for (Map.Entry<String, String> element : shuffledAnswers.entrySet()) {
                System.out.printf("%s) %s%n", element.getKey(), element.getValue());
            }
        }
        String input = getUserAnswer().toLowerCase(); // the user can write any string
        return getAnswerList(input, shuffledAnswers, question);
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
     * @param input            the raw input from the user.
     * @param shuffledAnswers  a map of the available answers with their identifiers.
     * @param question         the question being answered, for context.
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
        if (mode == 1) {
            System.out.println("\nYou have answered all of the questions from this topic.");
        } else {
            System.out.println("\nYou have answered all of the questions in this test.");
            pointsCounter.printGrade(theme);
        }
        System.exit(0); // to also stop the stopwatch
    }
}