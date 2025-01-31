package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class StyleLoader {
    public static List<style> loadStyles(String filePath) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(new File(filePath), new TypeReference<List<style>>() {});
    }
}
