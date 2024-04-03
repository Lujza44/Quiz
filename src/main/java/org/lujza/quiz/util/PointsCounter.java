package org.lujza.quiz.util;

import org.lujza.quiz.model.Question;
import org.lujza.quiz.model.Theme;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code PointsCounter} class manages the scoring system for the quiz application.
 * It keeps track of the user's total points, calculates points for correct and partially correct answers,
 * and determines the user's grade based on the total points earned in relation to the maximum possible points.
 */
public class PointsCounter {

    /**
     * The current total of points accumulated by the user. This field tracks the user's score throughout the quiz,
     * increasing as the user provides correct answers to questions. Points may be awarded based on the correctness
     * of the answers, with possible adjustments for partial correctness or penalties.
     */
    private int points = 0;

    /**
     * Returns the total points earned by the user so far.
     *
     * @return the total points as an integer.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds points for a correctly answered question to the user's total points.
     * The number of points added is determined by the maximum points that can be gained for one question
     * based on a specific theme.
     *
     * @param question the {@link Question} that was answered correctly.
     */
    public void addPoints(Question question) {
        points += question.getTheme().getMaxPointsPerQuestion();
    }

    /**
     * Adds partial points for a multiple choice question that was answered partially correct.
     * The calculation considers the number of correct options chosen and the number of incorrect options chosen.
     * Partial points are awarded if the number of mistakes made is exactly one.
     *
     * @param set1     a {@link Set} of strings representing the correct answers.
     * @param set2     a {@link Set} of strings representing the user's answers.
     * @param question the {@link Question} being evaluated.
     */
    public void addPartialPoints(Set<String> set1, Set<String> set2, Question question) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        int intersectionSize = intersection.size();
        int numberOfMistakes = set1.size() - intersectionSize +
                set2.size() - intersectionSize;

        if (numberOfMistakes == 1) {
            points += question.getTheme().getPartialPointsPerQuestion();
        }
    }

    /**
     * Prints the user's grade based on the points earned in relation to the total possible points for the chosen theme.
     * The grade is determined by the percentage of points earned: 90% and above yields grade 1, 75% to 89% yields grade 2,
     * 50% to 74% yields grade 3, and below 50% yields grade 4.
     *
     * @param theme the {@link Theme} for which the grade is being calculated.
     */
    public void printGrade(Theme theme) {
        int gainedPoints = getPoints();
        int maxPoints = theme.getQuestions().size() * theme.getMaxPointsPerQuestion();

        double percentage = ((double) points / maxPoints) * 100;
        percentage = Math.round(percentage * 100.0) / 100.0;

        System.out.print("You scored " +
                gainedPoints + " points, which is " + percentage + "% from the maximum of " + maxPoints + "points." +
                " \nTherefore your grade is ");

        if (points >= 0.9 * maxPoints) {
            System.out.println("1. Excellent!");
        } else if (points >= 0.75 * maxPoints && points < 0.9 * maxPoints) {
            System.out.println("2. Good job!");
        } else if (points >= 0.5 * maxPoints && points < 0.75 * maxPoints) {
            System.out.println("3. Good!");
        } else { // points < 0.5 * maxPoints
            System.out.println("4. You should try again.");
        }
    }
}