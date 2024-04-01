# Quiz Application

## Overview

The Quiz Application is a Java-based tool designed to help users prepare for exams by practicing questions on various topics. It features two modes of operation: a practice mode for an untimed session and a test simulation mode for a timed experience, offering flexibility to suit different study needs. Questions are dynamically loaded from JSON files, enabling easy updates and customization of the content.

## Features

- **Two Modes of Operation**: Choose between practice mode for an untimed session and test simulation for a timed experience.
- **Dynamic Question Loading**: Questions and themes are loaded from JSON files, facilitating easy updates and customization.
- **Scoring and Feedback**: Points are awarded for correct answers with partial points for nearly correct answers. Users receive immediate feedback and a final grade at the end of the session.

## Project Structure

The project is organized into several Java classes, each with a specific role:

- `Main`: The entry point of the application.
- `Quiz`: Manages the quiz flow, including mode selection, theme selection, and question sessions.
- `JsonReader`: Handles loading and parsing of question data from JSON files.
- `PointsCounter`: Tracks and calculates the user's points and final grade based on their answers.
- `Question`: Represents a single question, including its text, answer choices, and correct answer(s).
- `Theme`: Represents a theme or category of questions.
- `Stopwatch`: Manages the timing for test simulation mode, counting down the available time or tracking the elapsed time in practice mode.

## Prerequisites

- Java Development Kit (JDK) 11 or newer
- Gradle (for building and running the application)

## Setup and Running

### Building the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory in your terminal.
3. Run the following command to build the application:

```shell
gradle build
