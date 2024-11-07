package store.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public static <T> List<T> fileReadLine(String fileName, LineParser<T> parser) throws IOException {
        List<T> items = new ArrayList<>();
        BufferedReader bufferedReader = loadFile(fileName);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (checkLine(line)) {
                items.add(parser.parse(line));
            }
        }
        return items;
    }

    private static boolean checkLine(String line) {
        return !line.startsWith("name");
    }

    private static BufferedReader loadFile(String fileName) {
        InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("[ERROR] 파일이 존재하지 않습니다");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    @FunctionalInterface
    public interface LineParser<T> {
        T parse(String line);
    }
}
