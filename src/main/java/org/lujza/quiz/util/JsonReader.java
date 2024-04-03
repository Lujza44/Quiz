package org.lujza.quiz.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import org.lujza.quiz.model.Theme;

/**
 * The {@code JsonReader} class is responsible for loading and parsing JSON files containing theme data for quizzes.
 * It reads files from a specified directory, parsing each file into a {@link Theme} object, and then returns a list of these themes.
 * <p>
 * This class uses the Gson library for parsing, making it a straightforward solution for converting JSON data into Java objects.
 * </p>
 */
public class JsonReader {

    /**
     * The directory from which JSON files containing quiz data are read. This field represents
     * the path to the folder containing all the necessary JSON files that are loaded into the application
     * to construct quiz themes and questions. It is initialized with the path provided at the creation
     * of the {@code JsonReader} instance.
     */
    private final File directory;

    /**
     * Constructs a {@code JsonReader} with a specified directory path.
     * This directory is expected to contain JSON files that can be parsed into {@link Theme} objects.
     *
     * @param dirPath the path to the directory containing JSON files.
     */
    public JsonReader(String dirPath) {
        this.directory = new File(dirPath);
    }

    /**
     * Loads and parses all JSON files found in the specified directory, converting them into a list of {@link Theme} objects.
     * Each theme's questions are also processed, ensuring that they are properly associated with their parent theme.
     * <p>
     * This method relies on the Gson library for JSON parsing. The resulting list of themes is sorted before being returned.
     * </p>
     *
     * @return a sorted list of {@link Theme} objects loaded from the JSON files.
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read.
     */
    public List<Theme> load() throws IOException {
        List<Theme> list = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            Gson gson = new Gson();
            FileReader reader = new FileReader(file);
            Theme theme = gson.fromJson(reader, Theme.class);
            theme.getQuestions().forEach(question -> question.setTheme(theme));
            list.add(theme);
        }
        Collections.sort(list);
        return list;
    }
}