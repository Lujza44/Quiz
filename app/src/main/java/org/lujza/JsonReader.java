package org.lujza;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

public class JsonReader {

    private String filePath;
    public JsonReader(String filePath) {
        this.filePath = filePath;
    }

    public void load() throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filePath);
        Theme theme = gson.fromJson(reader, Theme.class);
    }
}