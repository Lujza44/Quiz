package org.lujza;

import java.util.*;

public class Question {
    private transient Theme theme; // ignorovane gsonom
    private String text;
    private String type;
    private List<String> wrongAnswers = new ArrayList<>();
    private List<String> correctAnswers;

    private List<String> allAnswers;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    public String getText() {
        return text;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    private Map<String, String> answersToMap() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < allAnswers.size(); i++) {
            map.put(String.valueOf((char) ('a' + i)), allAnswers.get(i));
        }
        return map;
    }

    public Map<String, String> getAllAnswersMap() {
        if (allAnswers == null) {
            allAnswers = new ArrayList<>(correctAnswers);
            allAnswers.addAll(wrongAnswers);
            Collections.shuffle(allAnswers);
        }
        return answersToMap();
    }

    public boolean isTextInput() {
        return this.type.equals("text");
    }

    public boolean isSingleAnswer() {
        return this.type.equals("single");
    }

    public boolean isMultipleAnswer() {
        return this.type.equals("multi");
    }
}