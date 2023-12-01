package clientserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888);
             Socket socket = serverSocket.accept();
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            MyObject myObject = (MyObject) inputStream.readObject();
            System.out.println("Received from client: " + myObject.getMessage());

            outputStream.writeObject("Object received successfully!");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
