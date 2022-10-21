import java.io.*;
import java.net.Socket;

public class ServerHandler extends Thread {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;


    public ServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        for (ServerHandler vr : Server.serverList) {
            vr.send("Новый клиент присоединился к чату!, теперь нас " + Server.serverList.size());
        }
        String word;
        try {
            while (true) {
                word = in.readLine();
                if (word.equals("/stop")) {
                    this.deleteService();
                    break;
                }
                System.out.println(word);
                for (ServerHandler vr : Server.serverList) {
                    vr.send(word);
                }
            }
        } catch (NullPointerException | IOException ignored) {
        }
    }


    protected void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {
        }

    }

    private void deleteService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerHandler vr : Server.serverList) {
                    if (vr.equals(this)) vr.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }
}
