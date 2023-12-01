package clientserver;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            MyObject myObject = new MyObject("Hello, Server!");

            outputStream.writeObject(myObject);
            outputStream.flush();

            String response = (String) inputStream.readObject();
            System.out.println("Server response: " + response);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
