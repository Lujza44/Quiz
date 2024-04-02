# Quiz Application

## Overview

The Quiz Application is a Java-based tool designed to help users prepare for exams by practicing questions on various topics. It features two modes of operation: a practice mode for an untimed session and a test simulation mode for a timed experience, offering flexibility to suit different study needs. Questions are dynamically loaded from JSON files, enabling easy updates and customization of the content.

## Features

- **Two Modes of Operation**: Choose between practice mode for an untimed session and test simulation for a timed experience.
- **Dynamic Question Loading**: Questions and themes are loaded from JSON files, facilitating easy updates and customization.
- **Scoring and Feedback**: Points are awarded for correct answers with partial points for nearly correct answers. Users receive immediate feedback and a final grade at the end of the session.

## Project Structure

The project is organized into several Java classes, each with a specific role:

- `Quiz`: Manages the quiz flow, including mode selection, theme selection, and question sessions.
- `JsonReader`: Handles loading and parsing of question data from JSON files.
- `PointsCounter`: Tracks and calculates the user's points and final grade based on their answers.
- `Question`: Represents a single question, including its text, answer choices, and correct answer(s).
- `Theme`: Represents a theme or category of questions.
- `Stopwatch`: Manages the timing for test simulation mode, counting down the available time or tracking the elapsed time in practice mode.

## Prerequisites

- Java Development Kit (JDK) 21
- Gradle (for building and running the application)

## Setup and Running

### Building the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory in your terminal.
3. Run the following command to build the application:

```shell
gradle build
```

Uživatelská dokumentace by také měla obsahovat návod, jak program spustit (jaké knihovny je potřeba doinstalovat, atd.)



### Annotation
Několik výstižných vět o tom, jakou úlohu Váš program řeší (a případně jak). Mělo by být čtivé, krátké a výstižné, nemusí být přesné.


### Program
what does it do, how does it do?
Zde by měly být popsány hlavní datové struktury používané v programu a jeho struktura - hlavní podprogramy a způsob jejich komunikace. Není nutné psát úplný výčet všech podprogramů (to často může dokumentaci velmi znepřehlednit).

### Input data
Je potřeba explicitně do nejmenších detailů popsat i formát vstupních dat - např. vstupuje-li posloupnost čísel, zda musí být každé na nové řádce nebo na tom nezáleží.

### Output data

Výstupy dobře navrženého programu by měly být snadno pochopitelné - např. proto, že jsou do nich "opsána" vstupní data a "program odpovídá celou větou". Někdy to však (zejména kvůli rozsahu dat) nejde a pak je důležité, aby uživatel uměl (bez neúměrného úsilí) správně interpretovat obdržené výsledky

### co by mohlo byt pridane

### Conclusion
