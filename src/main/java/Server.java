import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


public class Server {
    private static final String fileName = "Settings.txt";
    public static int PORT;
    public static List<ServerHandler> serverList = new LinkedList<>();

    static {
        try {
            PORT = Integer.parseInt(readUsingFiles(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");

            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerHandler(socket));
                    System.out.println("Количество клиентов на сервере " + serverList.size());

                } catch (IOException e) {
                    socket.close();
                }
            }
        }
    }

    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}