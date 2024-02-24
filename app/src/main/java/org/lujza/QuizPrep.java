package org.lujza;

import java.util.Scanner;

public class QuizPrep {

    private int mode;
    private int topic;

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the application, that will help you prepare for your exams!");
        System.out.println("Application has two modes: practice mode and a test simulation.");
        System.out.println("Do you want to practice or simulate a test? Enter 1 for practice mode or 2 for a test simulation:");
        mode = getUserChoice(scanner, 2);

        System.out.println("Now choose a topic you would like to study.");
        System.out.println("Enter 1 for Bioinformatic Acronyms");
        System.out.println("Enter 2 for Morse Code");
        System.out.println("Enter 3 for Networking Acronyms");
        System.out.println("Enter 4 for Bioinformatic Algorithms");
        System.out.println("Enter 5 for Latin Phrases");
        System.out.println("Enter 6 for TODO");
        System.out.println("Enter 7 for TODO");
        System.out.println("Enter 8 for TODO");
        System.out.println("Enter 9 for TODO");

        topic = getUserChoice(scanner, 3);

        // triedy TextAnswer, SingleChoice a Multiple choice by obsluhovali urcity typ testu,
        // volali by sa s konkretnym suborom (cestou k nemu) a modom studia (precvicovanie / testovanie)

        switch (topic) {
            case 1 -> TextAnswer(data/text/bioinf.json, mode);
            case 2 -> TextAnswer(data/text/morse.json, mode);
            case 3 -> TextAnswer(data/text/networking.json, mode);
            case 4 -> SingleChoice(data/single-choice/bioinf_alg.json, mode);
            case 5 -> SingleChoice(data/single-choice/latin.json, mode);
            case 6 -> SingleChoice(data/single-choice/TODO.json, mode);
            case 7 -> MultipleChoice(data/multiple-choice/TODO.json, mode);
            case 8 -> MultipleChoice(data/multiple-choice/TODO.json, mode);
            case 9 -> MultipleChoice(data/multiple-choice/TODO.json, mode);
        }
    }

    private int getUserChoice(Scanner scanner, int maxOption) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= maxOption) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number:");
            }
        }
        return choice;
    }
}