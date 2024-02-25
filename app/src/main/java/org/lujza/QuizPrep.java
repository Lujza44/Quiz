package org.lujza;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuizPrep {
    private Scanner scanner = new Scanner(System.in);
    private String mode;
    private String topic;
    private JsonReader jsonReader = new JsonReader("data");

    public void startQuiz() throws IOException {

        mode = prompt("""
                Welcome to the application, that will help you prepare for your exams!
                Application has two modes: practice mode and a test simulation.
                Do you want to practice or simulate a test? Enter 1 for practice mode or 2 for a test simulation.""", 2);

        List<Theme> themes = jsonReader.load();

        topic = prompt(themes);

        Theme theme = themes.get(Integer.parseInt(topic));

        List<Question> questions = theme.getQuestions();
        Collections.shuffle(questions);
        for (Question question : questions) {
            String answer = prompt(question); // vypisem uzivatelovi otazku
            String result = evaluate(question, answer); // skontrolovat ci je odpoved uzivatela spravna
            if (mode.equals("1")) { // po kazdej otazke vypisujem spravne/nespravne iba v precvicovacom mode
                System.out.println(result);
            }
        }
        if (mode.equals("2")) {
            //TODO vypisat vysledny pocet bodov
        }
    }

    public String evaluate(Question question, String answer) {
        if (question.isTextInput()) {
            return evalText(question, answer);
        } else if (question.isSingleAnswer()) {
            return evalSingle(question,answer);
        } else {
            return evalMulti(question,answer);
        }
    }

    private String evalText(Question question, String answer) { //TODO pocitanie bodov
        String correct = question.getCorrectAnswers().getLast();
        if (correct.equalsIgnoreCase(answer)) {
            return "Correct!";
        } else {
            return "Wrong. Correct was: " + correct;
        }
    }

    private String evalSingle(Question question, String answer) { //TODO pocitanie bodov
        String correct = question.getCorrectAnswers().getLast();
        List<String> allChoices = question.getAllChoices();
        char maxLetter = (char) ('a' + allChoices.size() - 1);

        if(answer.length() == 1 && answer.charAt(0) >= 'a' && answer.charAt(0) <= maxLetter) { //spravny format
            int index = answer.charAt(0) - 'a';
            String chosenOption = allChoices.get(index);
            if (correct.equalsIgnoreCase(chosenOption)) {
                return "Correct!";
            }
        }
        int correctIndex = allChoices.indexOf(correct);
        char letterIndex = (char) ('a' + correctIndex);

        return "Wrong. Correct was: " + letterIndex + ") " + correct;
    }

    private String evalMulti(Question question, String answer) {
        List<String> correct = question.getCorrectAnswers();
        List<String> allChoices = question.getAllChoices();
        char maxLetter = (char) ('a' + allChoices.size() - 1);

        //TODO skontrolovat format (a, b, c)
        if (validMultiFormat(answer, maxLetter)) {

        }
        //TODO pre kazde zadane pismenko skontrolvat ci je v correctAnswers
        return "";
    }

    private boolean validMultiFormat(String answer, char maxLetter) {
        String[] selectedOptions = answer.split(", ");
        for (String option : selectedOptions) {
            if (option.length() != 1 || option.charAt(0) < 'a' || option.charAt(0) > maxLetter) {
                return false;
            }
        }
        return true;
    }


    private String prompt(Question question) {
        System.out.println();
        System.out.println(question.getText());
        if (question.isSingleAnswer() || question.isMultipleAnswer()) { // ak je otazka aj s moznostami, vypisem ich
            List<String> allChoices = question.createAllChoices();
            for (int i = 0; i < allChoices.size(); i++) {
                char letter = (char) ('a' + i);
                System.out.println(String.format("%s) %s", letter, allChoices.get(i)));
            }
        }
        return getUserChoice(); // uzivatel moze zadat akykolvek string
    }

    private String prompt(List<Theme> themes) {
        System.out.println();
        for (int i = 0; i < themes.size(); i++) {
            System.out.println(String.format("%s. %s", i, themes.get(i).getName()));
        }
        return getUserChoice(themes.size() - 1);
    }

    private String prompt(String text, int maxOption) {
        System.out.println();
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

    private String getUserChoice() { //TODO hadze do prvej otazky prazdnu odpoved, zatial to neviem odstranit
        while (true) {
            String next = scanner.nextLine();
            if (next.equalsIgnoreCase("EXIT")) {
                System.exit(0);
            } else {
                return next;
            }
        }
    }
}