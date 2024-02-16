import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class SereverTalk extends Thread{

    private ArrayList<ServerSomthing> data;
    public SereverTalk(ArrayList<ServerSomthing> data){ //Создаем клиента для ввода сообщений со стороны сервера
        this.data = data;
    }

    @Override
    public void run (){ //Запуск клиента со стороны сервера для широковещательных сообщений
        String text;
        System.out.println("You can talk with other clients:");
        Scanner scanner = new Scanner(System.in);
        do {
            text = scanner.next();
            if(!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).send(text);
                }
            }
        }while(!Objects.equals(text, "exit"));
    }

    public void Datachange(ArrayList<ServerSomthing> data){
        this.data = data;
    }
}
