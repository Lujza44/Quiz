package org.lujza;

import java.util.Scanner;

public class TestPrep {

    private int mode;
    private int topic;

    public void startTest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the application, that will help you prepare for your exams!");
        System.out.println("Application has two modes: practice mode and a test simulation.");
        System.out.println("Do you want to practice or simulate a test? Enter 1 for practice mode or 2 for a test simulation:");
        mode = getUserChoice(scanner, 2);

        System.out.println("Now choose a topic you would like to study.");
        System.out.println("Enter 1 for Networking Acronyms");
        System.out.println("Enter 2 for Bioinformatic Acronyms");
        System.out.println("Enter 3 for Populations of European Countries");
        topic = getUserChoice(scanner, 3);

    }

    private int getUserChoice(Scanner scanner, int maxOption) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= maxOption) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
        return choice;
    }
}