package org.lujza;

import java.util.Comparator;
import java.util.List;

public class Theme implements Comparable<Theme> {
    private String name;
    private String description;
    private String scoringText;
    private int maxPointsPerQuestion;
    private int partialPointsPerQuestion;

    private List<Question> questions;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getScoringText() {
        return scoringText;
    }

    public int getMaxPointsPerQuestion() {
        return maxPointsPerQuestion;
    }

    public int getPartialPointsPerQuestion() {
        return partialPointsPerQuestion;
    }

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