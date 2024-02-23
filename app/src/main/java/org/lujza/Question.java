package org.lujza;
import java.util.ArrayList;
import java.util.List;
public class Question {
    private String text;
    private List<String> choices = new ArrayList<>();
    private List<String> correctAnswers;
    // Constructors, getters, and setters
    // toto mozno pre tvoju osobnu kontrolu, aby si nemusela kontrolovat manualne tvoj JSON,
    // ci mas spravne zadefinovanu otazku
    public boolean isValid() {
        // kontrola ze ci napr. sedia odpovede v correctAnswers s tym co mas definovane v choices...
        // ci nahodou nie je viac correctAnswers ako choices, atd...
        // to mi len tak napadlo
        return true;
    }
    public boolean isTextInput() {
        return choices.isEmpty() && correctAnswers.size() == 1;
    }
    public boolean isSingleAnswer() {
        return !choices.isEmpty() && correctAnswers.size() == 1;
    }
    public boolean isMultipleAnswer() {
        return !choices.isEmpty() && correctAnswers.size() > 1;
    }
}