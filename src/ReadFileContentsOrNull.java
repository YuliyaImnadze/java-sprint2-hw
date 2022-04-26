import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadFileContentsOrNull {
    public String readFileContentsOrNull(String path) { // "1 - Считать все месячные отчёты"
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

}
