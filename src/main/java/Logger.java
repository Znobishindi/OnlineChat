import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    static volatile FileWriter writer;

    static {
        try {
            if (Files.notExists(Path.of("Log.txt"))) {
                File file = new File("Log.txt");
            }
            writer = new FileWriter("Log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void log(String msg) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        writer.append(timeStamp).append(" ").append(msg).append("\n");
        writer.flush();


    }
}

