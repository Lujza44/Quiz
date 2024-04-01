package org.lujza;

import java.util.List;

/**
 * The {@code Theme} class represents a theme or topic for the quiz, containing a collection of questions related to the theme.
 * It includes properties such as the theme's name, description, and scoring criteria, along with a list of associated questions.
 * This class implements the {@link Comparable} interface to allow themes to be sorted by their names.
 */
public class Theme implements Comparable<Theme> {
    private String name;
    private String description;
    private String scoringText;
    private int maxPointsPerQuestion;
    private int partialPointsPerQuestion;
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
    public int compareTo(Theme that) { // na zoradenie tem
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