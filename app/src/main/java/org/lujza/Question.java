package org.lujza;

import java.util.*;

/**
 * The {@code Question} class represents a single question in the quiz, including its text,
 * type (text input, single choice, or multiple choice), and a list of correct and wrong answers.
 * It provides functionality to access question details and to format answers for display.
 */
public class Question {
    private transient Theme theme; // ignored by gson
    private transient boolean answered;
    private String text;
    private String type;
    private List<String> wrongAnswers = new ArrayList<>();
    private List<String> correctAnswers;
    private List<String> allAnswers;

    /**
     * Gets the theme associated with this question.
     *
     * @return The {@link Theme} to which this question belongs.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Sets the theme associated with this question.
     *
     * @param theme The {@link Theme} to which this question will be associated.
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    /**
     * Gets the text of this question.
     *
     * @return The question text as a {@link String}.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the list of correct answers for this question.
     *
     * @return A {@link List} of {@link String} objects representing the correct answers.
     */
    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Converts the list of all answers (correct and wrong) to a map for easier access.
     * This internal method is used to format answers when presenting them to the user.
     *
     * @return A {@link Map} with answer options as keys and answer texts as values.
     */
    private Map<String, String> answersToMap() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < allAnswers.size(); i++) {
            map.put(String.valueOf((char) ('a' + i)), allAnswers.get(i));
        }
        return map;
    }

    /**
     * Provides a map of all answer options (correct and wrong) mixed together and shuffled.
     * This method is used to display answer options to the user in a randomized order.
     *
     * @return A {@link Map} with letters as keys (e.g., 'a', 'b', 'c',...) and the shuffled answer texts as values.
     */
    public Map<String, String> getAllAnswersMap() {
        if (allAnswers == null) {
            allAnswers = new ArrayList<>(correctAnswers);
            allAnswers.addAll(wrongAnswers);
            Collections.shuffle(allAnswers);
        }
        return answersToMap();
    }

    /**
     * Checks if the question type requires a text input answer.
     *
     * @return {@code true} if the question type is "text"; {@code false} otherwise.
     */
    public boolean isTextInput() {
        return this.type.equals("text");
    }

    /**
     * Checks if the question is of single answer type.
     *
     * @return {@code true} if the question type is "single"; {@code false} otherwise.
     */
    public boolean isSingleAnswer() {
        return this.type.equals("single");
    }

    /**
     * Checks if the question allows for multiple answers to be selected.
     *
     * @return {@code true} if the question type is "multi"; {@code false} otherwise.
     */
    public boolean isMultipleAnswer() {
        return this.type.equals("multi");
    }
}