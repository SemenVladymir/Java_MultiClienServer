import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                // адрес - локальный хост, порт - 8008, такой же как у сервера
                clientSocket = new Socket("localhost", 8080); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String word = "";
                String serverWord = "";
                do {

                    if(!in.readLine().isEmpty()) {
                        serverWord = in.readLine(); // ждём, что скажет сервер
                        System.out.println(serverWord); // получив - выводим на экран
                    }
                    if(!reader.readLine().isEmpty())
                    {
                        word = reader.readLine(); // ждём пока клиент что-нибудь
                        // не напишет в консоль
                        out.write(word + "\n"); // отправляем сообщение на сервер
                        out.flush();
                    }
                }while(true);
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
