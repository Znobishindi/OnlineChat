import java.io.*;
import java.net.Socket;

public class Person {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String name;


    public Person(String host, int port, String name) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("Сокет не передан");
        }
        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = name;
            new ReadMsg().start();
            new WriteMsg().start();
        } catch (IOException e) {
            Person.this.closeConnection();
        }
    }


    private void closeConnection() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String msg;
            try {
                while (true) {
                    msg = in.readLine();
                    if (msg.equals("/stop")) {
                        Person.this.closeConnection();
                        break;
                    }
                    System.out.println(msg);
                }
            } catch (IOException e) {
                Person.this.closeConnection();
            }
        }
    }

    private class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                String msg;
                try {
                    msg = inputUser.readLine();
                    if (msg.equals("/stop")) {
                        out.write("/stop" + "\n");
                        Logger.log("Клиент " + name + " вышел из чата");
                        Person.this.closeConnection();
                        break;
                    } else {
                        msg = name + ": " + msg + "\n";
                        out.write(msg);
                        Logger.log(msg);
                    }
                    out.flush();
                } catch (IOException e) {
                    Person.this.closeConnection();
                }

            }
        }
    }
}
