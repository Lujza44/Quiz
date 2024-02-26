package org.lujza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    private String text;
    private List<String> wrongAnswers = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    public String getText() {
        return text;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public List<String> shuffleAnswers() {
        List<String> all = getAllAnswers();
        Collections.shuffle(all);
        return all;
    }

    public List<String> getAllAnswers() {
        List<String> allAnswers = new ArrayList<>(correctAnswers);
        allAnswers.addAll(wrongAnswers);
        return allAnswers;
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
        return correctAnswers.size() != 1; // moze byt aj 0 correct answers, moze byt aj 0 wrong choices

    }
}