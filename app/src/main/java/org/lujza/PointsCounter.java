package org.lujza;

import java.util.HashSet;
import java.util.Set;

public class PointsCounter {

    private int points = 0;

    public int getPoints() {
        return points;
    }

    public void addPoints(Question question) {
        points += question.getTheme().getMaxPointsPerQuestion();
    }

    public void addPartialPoints(Set<String> set1, Set<String> set2, Question question) { // set1 = correctAnswers, set2 = userAnswers
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        int intersectionSize = intersection.size();
        int numberOfMistakes = set1.size() - intersectionSize +
                set2.size() - intersectionSize; // chyby su moznosti mimo intersection

        if (numberOfMistakes == 1) {
            points += question.getTheme().getPartialPointsPerQuestion();
        }
    }

    public void printGrade(Theme theme) {
        int gainedPoints = getPoints();
        int maxPoints = theme.getQuestions().size() * theme.getMaxPointsPerQuestion();

        double percentage = ((double) points / maxPoints) * 100;
        percentage = Math.round(percentage * 100.0) / 100.0;

        System.out.print("\nYou scored " +
                gainedPoints + " points, which is " + percentage + "% from the maximum of " + maxPoints +
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