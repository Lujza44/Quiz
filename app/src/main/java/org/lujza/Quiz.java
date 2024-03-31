package org.lujza;

import java.io.IOException;
import java.util.*;

public class Quiz {
    private final Scanner scanner = new Scanner(System.in);
    private final JsonReader jsonReader = new JsonReader("data");
    private final PointsCounter pointsCounter = new PointsCounter();
    private int mode;
    private Theme theme;

    public void runQuiz() throws IOException {

        mode = prompt();
        List<Theme> themes = jsonReader.load(); //TODO vypisat temy indexovane od 1, nie od 0
        int topic = prompt(themes);
        theme = themes.get(topic);

        System.out.println();
        System.out.println(theme.getDescription());
        if (mode == 2) {
            System.out.println(theme.getScoringText());
        }

        List<Question> questions = theme.getQuestions();
        Collections.shuffle(questions);

        Stopwatch stopwatch = new Stopwatch(questions.size() * 30 + 60, mode, theme); // 30 sekund na kazdu otazku + minuta na spracovanie zadania
        stopwatch.start();

        for (Question question : questions) {
            System.out.println();
            stopwatch.getTime(mode);
            List<String> answers = prompt(question); // vypisem uzivatelovi otazku a vratim jeho odpoved(e)
            evaluate(question, answers); // skontrolovat ci je odpoved uzivatela spravna
        }
        exit();
    }

    public void exit() {
        if (mode == 1) {
            System.out.println("\nYou have answered all of the questions from this topic.");
        } else {
            PointsCounter.printGrade(theme);
        }
        System.exit(0); // aby stopwatch nepocital dalej cas
    }
    private void evaluate(Question question, List<String> answers) { // na vstupe dostavam pole odpovedi (cele ich znenie), ktore zadal user
        List<String> correctAnswers = question.getCorrectAnswers();
        Set<String> set1 = new HashSet<>(correctAnswers);
        Set<String> set2 = new HashSet<>(answers);

        if (question.isTextInput()) { // ak textova odpoved, len prerobim odpoved na lowercase
            set1 = Set.of(correctAnswers.getFirst().toLowerCase());
        }

        if (set1.equals(set2)) { // ak su mnozina odpovedi usera a mnozina spravnych odpovedi rovnake
            printIfMode1("Correct!");
            pointsCounter.addPoints(question);
            return;
        }

        if (question.isTextInput()) { // ak nespravna textova odpoved, vypisem spravnu
            printIfMode1("Wrong. Correct answer: %s", correctAnswers.getFirst());
        } else { // ak nespravny choice, vypisem spravne moznosti
            printIfMode1("Wrong. Correct answer(s):");
            for (Map.Entry<String, String> element : question.getAllAnswersMap().entrySet()) {
                if (correctAnswers.contains(element.getValue())) {
                    printIfMode1("%s) %s", element.getKey(), element.getValue());
                }
            }
            pointsCounter.addPartialPoints(set1, set2, question);
        }
    }

    private void printIfMode1(CharSequence format, Object... args) {
        if (mode == 1) {
            System.out.printf(format + "%n", args);
        }
    }

    private List<String> prompt(Question question) {
        System.out.println();
        System.out.println(question.getText());
        Map<String, String> shuffledAnswers = new HashMap<>();

        if (question.isSingleAnswer() || question.isMultipleAnswer()) { // ak je otazka aj s moznostami, vypisem ich
            shuffledAnswers = question.getAllAnswersMap();
            for (Map.Entry<String, String> element : shuffledAnswers.entrySet()) {
                System.out.printf("%s) %s%n", element.getKey(), element.getValue());
            }
        }
        String input = getUserAnswer().toLowerCase(); // uzivatel moze zadat akykolvek string
        return getAnswerList(input, shuffledAnswers, question); // vratim odpoved(e) uzivatela ako zoznam
    }

    private List<String> getAnswerList(String input, Map<String, String> shuffledAnswers, Question question) {

        if (question.isTextInput()) {
            return List.of(input); // vratim zoznam s jednym prvkom, textovou odpovedou uzivatela
        } else {
            String[] splitInput = input.split("[,\\s;]+");
            List<String> answers = new ArrayList<>();
            for (String ans : splitInput) {
                if (ans.isEmpty()) {
                    continue;
                }
                if (!shuffledAnswers.containsKey(ans)) {
                    return new ArrayList<>(); // // ak je format zly, neudeje sa nic, proste sa vypise, ze zle (pripadne co mala byt spravna odpoved)
                }
                answers.add(shuffledAnswers.get(ans));
            }
            return answers; // vratim zoznam so znenim moznosti ktore uzivatel vybal
        }
    }

    private int prompt(List<Theme> themes) {
        System.out.println();
        for (int i = 0; i < themes.size(); i++) {
            System.out.printf("%s. %s%n", i, themes.get(i).getName());
        }
        return getUserChoice(themes.size() - 1);
    }

    private int prompt() {
        System.out.println("""
                Welcome to the application, that will help you prepare for your exams!
                Application has two modes: practice mode and a test simulation.
                Do you want to practice or simulate a test? Enter 1 for practice mode or 2 for a test simulation.""");
        return getUserChoice(2);
    }

    private int getUserChoice(int maxOption) {
        System.out.print("Your choice: ");
        int choice;

        while (true) {
            try {
                String next = scanner.nextLine();
                if (next.equalsIgnoreCase("EXIT")) {
                    System.exit(0);
                }

                choice = Integer.parseInt(next);
                if (choice >= 0 && choice <= maxOption) {
                    return choice;
                } else {
                    System.out.print("Invalid input. Please enter a valid number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    private String getUserAnswer() {
        String next = scanner.nextLine();
        if (next.equalsIgnoreCase("EXIT")) {
            System.exit(0);
        }
        return next;
    }
}