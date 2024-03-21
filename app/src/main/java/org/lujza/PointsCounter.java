package org.lujza;

import java.util.HashSet;
import java.util.Set;

public class PointsCounter {

    private int points = 0;

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

    public int getPoints() {
        return points;
    }
}
