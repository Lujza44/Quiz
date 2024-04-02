package org.lujza;

import java.util.List;

/**
 * The {@code Theme} class represents a theme or topic for the quiz, containing a collection of questions related to the theme.
 * It includes properties such as the theme's name, description, and scoring criteria, along with a list of associated questions.
 * This class implements the {@link Comparable} interface to allow themes to be sorted by their names.
 */
public class Theme implements Comparable<Theme> {

    /**
     * The name of the theme. This field represents the title or main idea of the theme,
     * which is used to categorize and identify the group of questions under this theme.
     */
    private String name;

    /**
     * A brief description of the theme. This text provides additional context or information
     * about the theme, helping users understand what kind of questions or topics the theme covers.
     */
    private String description;

    /**
     * Text detailing how scoring works for this particular theme. This may include information
     * about the distribution of points, the importance of specific questions, or how partial points are awarded.
     */
    private String scoringText;

    /**
     * The maximum number of points that can be awarded for a single question within this theme.
     * This value is used to calculate the total and final score of a user, emphasizing the theme's weighting on the overall quiz.
     */
    private int maxPointsPerQuestion;

    /**
     * The number of points awarded for partially correct answers to questions within this theme.
     * This allows for a more nuanced scoring system, where users can still receive some points for near-correct answers.
     */
    private int partialPointsPerQuestion;

    /**
     * A list of questions associated with this theme. Each question is an instance of the {@link Question} class,
     * containing its own set of properties, such as the question text, correct and incorrect answers, and its type.
     */
    private List<Question> questions;

    /**
     * Gets the name of the theme.
     *
     * @return The theme's name as a {@link String}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the theme.
     *
     * @return The theme's description as a {@link String}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the list of questions associated with this theme.
     *
     * @return A {@link List} of {@link Question} objects related to the theme.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Gets the text about scoring. This text typically contains information about how the scoring works for questions in this theme.
     *
     * @return The text about scoring as a {@link String}.
     */
    public String getScoringText() {
        return scoringText;
    }

    /**
     * Gets the maximum number of points that can be awarded for correctly answering a question in this theme.
     *
     * @return The maximum points per question as an integer.
     */
    public int getMaxPointsPerQuestion() {
        return maxPointsPerQuestion;
    }

    /**
     * Gets the number of points awarded for partially correct answers to questions in this theme.
     *
     * @return The points awarded for partial correctness as an integer.
     */
    public int getPartialPointsPerQuestion() {
        return partialPointsPerQuestion;
    }

    /**
     * Compares this theme with another theme for order, based on the alphabetical ordering of their names.
     *
     * @param that The {@link Theme} to be compared.
     * @return A negative integer, zero, or a positive integer as this theme is less than, equal to, or greater than the specified theme.
     */
    @Override
    public int compareTo(Theme that) {
        if (this.name == null && that.name == null) {
            return 0;
        } else if (this.name == null) {
            return -1;
        } else if (that.name == null) {
            return 1;
        } else {
            return this.name.compareTo(that.name);
        }
    }
}