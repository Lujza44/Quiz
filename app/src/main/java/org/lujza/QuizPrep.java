package org.lujza;

import java.io.IOException;
import java.util.*;

public class QuizPrep {
    private final Scanner scanner = new Scanner(System.in);
    private String mode;
    private final JsonReader jsonReader = new JsonReader("data");

    private final PointsCounter pointsCounter = new PointsCounter();

    public void startQuiz() throws IOException {

        mode = prompt();

        //TODO vypisat temy indexovane od 1, nie od 0
        List<Theme> themes = jsonReader.load();

        String topic = prompt(themes);

        Theme theme = themes.get(Integer.parseInt(topic));

        System.out.println();
        System.out.println(theme.getDescription());

        List<Question> questions = theme.getQuestions();
        Collections.shuffle(questions);
        for (Question question : questions) {
            List<String> answers = prompt(question); // vypisem uzivatelovi otazku a vratim jeho odpoved(e)
            evaluate(question, answers); // skontrolovat ci je odpoved uzivatela spravna
        }
        if (mode.equals("1")) {
            System.out.println("You have answered all of the questions from this topic.");
        } else {
            //TODO vypisat vysledny pocet bodov

        }

    }

    public void evaluate(Question question, List<String> answers) {
        List<String> correctAnswers = question.getCorrectAnswers();
        Set<String> set1 = new HashSet<>(correctAnswers);
        Set<String> set2 = new HashSet<>(answers);

        if (question.isTextInput()) {
            set1 = Set.of(correctAnswers.getFirst().toLowerCase());
        }

        if (mode.equals("1")) {
            if (set1.equals(set2)) {
                System.out.println("Correct!");
                return;
            }

            if (question.isTextInput()) {
                System.out.printf("Wrong. Correct answer: %s%n", correctAnswers.getFirst());
            } else {
                StringBuilder msg = new StringBuilder("Wrong. Correct answer(s):\n");
                for (Map.Entry<String, String> element : question.getAllAnswersMap().entrySet()) {
                    if (correctAnswers.contains(element.getValue())) {
                        msg.append(String.format("%s) %s%n", element.getKey(), element.getValue()));
                    } //TODO prerobit nech to nepise newline po poslednej moznosti
                }
                System.out.println(msg);
            }
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

    private String prompt(List<Theme> themes) {
        System.out.println();
        for (int i = 0; i < themes.size(); i++) {
            System.out.printf("%s. %s%n", i, themes.get(i).getName());
        }
        return getUserChoice(themes.size() - 1);
    }

    private String prompt() {
        System.out.println("""
                Welcome to the application, that will help you prepare for your exams!
                Application has two modes: practice mode and a test simulation.
                Do you want to practice or simulate a test? Enter 1 for practice mode or 2 for a test simulation.""");
        return getUserChoice(2);
    }

    private String getUserChoice(int maxOption) {
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
                    return next;
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