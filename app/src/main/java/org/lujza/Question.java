package org.lujza;

import java.util.*;

public class Question {
    private String text;
    private List<String> wrongAnswers = new ArrayList<>();
    private List<String> correctAnswers;

    private List<String> allAnswers;
    public String getText() {
        return text;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    private Map<String,String> answersToMap() {
        Map<String,String> map = new HashMap<>();
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

    // toto mozno pre tvoju osobnu kontrolu, aby si nemusela kontrolovat manualne tvoj JSON,
    // ci mas spravne zadefinovanu otazku
    public boolean isValid() {
        // kontrola ze ci napr. sedia odpovede v correctAnswers s tym co mas definovane v choices...
        // ci nahodou nie je viac correctAnswers ako choices, atd...
        // to mi len tak napadlo
        return true;
    }

    public boolean isTextInput() {
        return wrongAnswers.isEmpty() && correctAnswers.size() == 1;
    }

    public boolean isSingleAnswer() {
        return !wrongAnswers.isEmpty() && correctAnswers.size() == 1;
    }

    public boolean isMultipleAnswer() {
        //return !wrongChoices.isEmpty() && correctAnswers.size() > 1;
        return correctAnswers.size() != 1;
    }
}