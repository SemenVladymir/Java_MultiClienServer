import java.io.*;
import java.net.Socket;
import java.util.UUID;

class ServerSomthing extends Thread {

    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    public ServerSomthing(Socket inpsocket) throws IOException {
        socket = inpsocket;
        //System.out.println(UUID.randomUUID());
        System.out.println(socket.getPort());
        // если потоку ввода/вывода приведут к генерированию исключения, оно пробросится дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        send(UUID.randomUUID().toString());
        start(); // вызываем run()

    }
    @Override
    public void run() {
        String word;
        try {

            while (true) {
                word = in.readLine();
                if(word.equals("stop")) {
                    break;                }
                for (ServerSomthing vr : Server.serverList) {
                    vr.send(word); // отослать принятое сообщение с
                    // привязанного клиента всем остальным включая его
                }
            }

        } catch (IOException e) {
        }
    }

    public void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
