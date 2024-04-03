# Quiz Application

## Overview

The Quiz Application is a Java-based tool designed to help users prepare for exams by practicing questions on various topics. It features:

- **Two Modes of Operation**: Choose between practice mode for an untimed session and test simulation for a timed experience.
- **Dynamic Question Loading**: Questions and themes are loaded from JSON files, facilitating easy updates and customization.
- **Scoring and Feedback**: Points are awarded for correct answers with partial points for nearly correct answers. Users receive immediate feedback and a final grade at the end of the session.

## Program
When the quiz application starts, it first prompts the user to select their preferred mode of revision: practice mode for an untimed, relaxed learning experience, or test simulation mode for a more exam-like, timed setting. Once the mode is selected, the user is then asked to choose a theme, which determines the set of questions they will be revising from.

In practice mode, questions are presented one after the other without any time constraints, allowing the user to answer at their own pace. In test simulation mode, a stopwatch is initiated, counting down the time available to complete the quiz, adding a sense of urgency to simulate an exam environment.

For each question, users input their answers, and immediate feedback may be provided depending on the mode. In the test simulation mode, the application tracks the remaining time, and once the time is up or all questions are answered, the quiz ends.

At the conclusion of the session, the application calculates the user's score based on the correctness of their answers, awards points, and presents a final grade. This grade reflects the user's performance across all questions, offering insight into their knowledge and areas that may require further revision.

### Project Structure

The project is organized into several Java classes, each with a specific role:

- `Quiz`: Manages the quiz flow, including mode selection, theme selection, providing questions and reading user's answers.
- `JsonReader`: Handles loading and parsing of question data from JSON files.
- `Theme`: Represents a theme - one set of questions.
- `Question`: Represents a single question, including its text, answer choices, and correct answer(s).
- `PointsCounter`: Tracks and calculates the user's points and final grade based on their answers.
- `Stopwatch`: Manages the timing for test simulation mode, counting down the available time or tracking the elapsed time in practice mode.

### Prerequisites

- For building: Gradle (tested with version 8.6)
- For running: Open JDK 21

### Building the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory in your terminal.
3. Run the following command to build the application:

```shell
./gradlew build
```
### Running the Application

data na urovni bin a lib

```
Quiz
├── bin
│   ├── Quiz
│   └── Quiz.bat
├── data
│   └── ...
└── lib
    ├── gson-2.8.9.jar
    └── Quiz.jar

```

Run the following commands to run the application:
```
unzip ../build/distributions/Quiz.zip -d .
cd Quiz/bin
./Quiz
```
## Input Data
The input data for the quiz application primarily consists of JSON files containing the quiz questions, themes, and their respective details. Each theme's file includes a collection of questions, each question detailing its text, type (e.g., multiple-choice, text input), correct answers, and incorrect options. Themes are categorized by name and include a description and scoring guidelines, providing a structured format for easily updating or adding new content to the application.

## Output Data
The output data of the quiz application is presented to the user in the form of text displayed on the console. It includes prompts for user decisions (like mode and theme selection), questions with possible answers for the user to respond to, immediate feedback on answers (if applicable), and a final grade upon completion. The final grade output includes the total points scored, a percentage of correct answers, and a qualitative assessment based on the user's performance.

## Future Enhancements
To further enrich the quiz application and enhance user interaction, several features could be implemented in future updates. Introducing commands like "next" and "previous" would allow users to navigate through questions more flexibly, providing them the opportunity to review and change their answers before final submission. Expanding the variety of question types to include ordering tasks, where users arrange options in a chronological or logical sequence, could offer a more diverse testing experience. Additionally, enabling users to specify the number of questions they wish to answer in a session would allow for customizable quiz lengths, catering to different time availabilities and study needs. These enhancements would significantly improve the application's usability and educational value, making it a more versatile tool for exam preparation.

