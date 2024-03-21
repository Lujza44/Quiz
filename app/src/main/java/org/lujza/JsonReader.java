package org.lujza;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;

public class JsonReader {

    private final File directory;
    public JsonReader(String filePath) {
        this.directory = new File(filePath);
        //System.out.println("JsonReader initialized with directory: " + directory.getAbsolutePath());
    }

    public List<Theme> load() throws IOException {
        List<Theme> list = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            Gson gson = new Gson();
            FileReader reader = new FileReader(file);
            Theme theme = gson.fromJson(reader, Theme.class); // vsetko do objektu theme
            theme.getQuestions().forEach(question -> question.setTheme(theme)); // z objektu theme otazky -> do kazdej theme
            list.add(theme);
        }
        Collections.sort(list);
        return list;
    }
}