package org.lujza;

import java.io.IOException;
import java.util.*;

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
            List<String> answers = prompt(question); // vypisem uzivatelovi otazku a vratim jeho odpoved(e)
            System.out.println(evaluate(question, answers)); // skontrolovat ci je odpoved uzivatela spravna
        }
        //TODO vypisat ze sa vycerpali vsetky otazky

        if (mode.equals("2")) {
            //TODO vypisat vysledny pocet bodov
        }
    }

    public String evaluate(Question question, List<String> answers) {
        List<String> correctAnswers = question.getCorrectAnswers();
        // toto asi neni optimalne :D ale nemozem sortovat answers, lebo List.of dava immutable list
        // TODO prerobit to + pre textovu odpoved to nefunguje, lebo este treba ignorecase
        Set<String> set1 = new HashSet<>(correctAnswers);
        Set<String> set2 = new HashSet<>(answers);

        if (set1.equals(set2)) {
            return "Correct!";
        }
        return "Wrong."; //TODO vypisat co bola spravna odpoved ak mode = 1
    }


    private List<String> prompt(Question question) {
        System.out.println();
        System.out.println(question.getText());
        List<String> shuffledAnswers = new ArrayList<>();

        if (question.isSingleAnswer() || question.isMultipleAnswer()) { // ak je otazka aj s moznostami, vypisem ich
            shuffledAnswers = question.shuffleAnswers();
            for (int i = 0; i < shuffledAnswers.size(); i++) {
                char letter = (char) ('a' + i);
                System.out.println(String.format("%s) %s", letter, shuffledAnswers.get(i)));
            }
        }
        String input = getUserAnswer(); // uzivatel moze zadat akykolvek string
        return getAnswerList(input, shuffledAnswers, question); // vratim odpoved(e) uzivatela ako zoznam
    }

    private List<String> getAnswerList(String input, List<String> shuffledAnswers, Question question) {
        char maxLetter = (char) ('a' + shuffledAnswers.size() - 1);

        if (question.isTextInput()) {
            return List.of(input); // vratim zoznam s jednym prvkom, textovou odpovedou uzivatela
        } else if (question.isSingleAnswer()) {
            if (input.length() == 1 && input.charAt(0) >= 'a' && input.charAt(0) <= maxLetter) { // spravny format single odpovede
                int index = input.charAt(0) - 'a';
                String chosenOption = shuffledAnswers.get(index);
                return List.of(chosenOption); // vratim zoznam so znenim moznosti ktoru uzivatel vybral
            } else {
                return new ArrayList<>(); // ak je format zly, neudeje sa nic, proste sa vypise, ze zle (pripadne co mala byt spravna odpoved)
            }
        } else {
            List<String> splitInput = List.of(input.split(", "));
            List<String> answers = new ArrayList<>();
            for (String ans : splitInput) {
                if (ans.length() != 1 || ans.charAt(0) < 'a' || ans.charAt(0) > maxLetter) {
                    return new ArrayList<>(); // // ak je format zly, neudeje sa nic, proste sa vypise, ze zle (pripadne co mala byt spravna odpoved)
                }
                int index = ans.charAt(0) - 'a';
                String chosenOption = shuffledAnswers.get(index);
                answers.add(chosenOption);
            }
            return answers; // vratim zoznam so znenim moznosti ktore uzivatel vybal
        }
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

    private String getUserAnswer() { //TODO hadze to do prvej otazky prazdnu odpoved, zatial to neviem odstranit
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