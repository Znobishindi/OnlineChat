import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.nio.file.NoSuchFileException;

class ClientTest {
//Проверяем на наличие исключения при указании другого имени файла
    @Test
    void readUsingFilesTest() {
        String fileName = "kkkddkk.txt";
        var expected = NoSuchFileException.class;
        Assertions.assertThrows(expected,
                () -> Client.readUsingFiles(fileName));
    }
    }
