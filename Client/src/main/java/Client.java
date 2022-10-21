import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;



class Client {

    private final static String HOST = "localhost";
    private static final String fileName = "Settings.txt";
    private static int PORT;

    static {
        try {
            PORT = Integer.parseInt(readUsingFiles(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Введите Ваше имя: ");
        String name = scanner.nextLine();
        new Person(HOST, PORT, name);
        Logger.log("К чату добавился новый клиент " + name);
    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));

    }
}