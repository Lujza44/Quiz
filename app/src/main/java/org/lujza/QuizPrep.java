package org.lujza;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuizPrep {
    private Scanner scanner = new Scanner(System.in);
    private int mode;
    private int topic;
    private JsonReader jsonReader = new JsonReader("data");

    public void startQuiz() throws IOException {

        mode = prompt("""
                Welcome to the application, that will help you prepare for your exams!
                Application has two modes: practice mode and a test simulation.
                Do you want to practice or simulate a test? Enter 1 for practice mode or 2 for a test simulation.""", 2);

        List<Theme> themes = jsonReader.load();

        topic = prompt(themes);

        Theme theme = themes.get(topic);

        List<Question> questions = theme.getQuestions();
        Collections.shuffle(questions);
        for (Question question : questions) {
            prompt(question);
            // skontrolovat ci je odpoved uzivatela spravna
        }
    }

    private int prompt(Question question) {
        System.out.println(question.getText());
        List<String> allChoices = question.getAllChoices();
        Collections.shuffle(allChoices);
        for (int i = 0; i < allChoices.size(); i++) {
            System.out.println(String.format("%s. %s", i, allChoices.get(i)));
        }
        return getUserChoice(allChoices.size()-1);
    }

    private int prompt(List<Theme> themes) {
        for (int i = 0; i < themes.size(); i++) {
            System.out.println(String.format("%s. %s", i, themes.get(i).getName()));
        }
        return getUserChoice(themes.size()-1);
    }

    private int prompt(String text, int maxOption) {
        System.out.println(text);
        return getUserChoice(maxOption);
    }

    private String getUserChoice(int maxOption) {
        System.out.print("Your choice: ");
        int choice;
        while (true) {
            try {

                String next = scanner.next();

                if (next.equalsIgnoreCase("EXIT")) {
                    System.exit(0);
                }

                choice = Integer.parseInt(next);

                if (choice >= 0 && choice <= maxOption) {
                    return next;
                } else {
                    System.out.print("Invalid input. Please enter a valid number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}