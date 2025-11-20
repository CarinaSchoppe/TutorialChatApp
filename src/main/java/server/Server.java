package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Server {

  //der soll nun auch Nachrichten senden zu können
  public static final HashMap<Integer, ClientHandler> CLIENT_IDS = new HashMap<>();

  /*
  Client -> IP:Port
  * "Verbindungselement" -> Zum verbinden

  Server: Port
  "Verbindungselement" -> Zum Verbindungen empfangen

  Allgemein:
  - Nachrichten von der Tastatur / Userinput einzulesen und zu senden

  * */


  private static final int PORT = 12345;

  private static ServerSocket serverSocket;

  public static void main(String[] args) {

    try {
      new Thread(() -> {
        try {
          var scanner = new Scanner(System.in);
          String message;
          while (!(message = scanner.nextLine()).equalsIgnoreCase("Quit")) {
            var split = message.split(":", 2);
            if (split.length >= 2) {
              message = split[1];
              var receiver = Integer.parseInt(split[0]);
              var client = CLIENT_IDS.get(receiver);
              new PrintWriter(
                  new BufferedWriter(new OutputStreamWriter(client.getClient().getOutputStream())),
                  true).println(message);
              System.out.println("Sende an Client(" + client.getID() + ") " + message);
            } else {
              System.out.println("Sende an alle: " + message);
              ((ClientHandler) CLIENT_IDS.values().toArray()[0]).broadcast(message);
            }
          }

        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      }).start();

      serverSocket = new ServerSocket(PORT);

    } catch (
        IOException exception) {

      throw new RuntimeException(exception);
    }

    System.out.println("ChatSystem Server erstellt!");

    var id = 0;


    while (true) {
      try {
        Socket socket = serverSocket.accept();


        System.out.println(
            "Client-Verbindung empfangen!" +
                " IP vom Client: "
                + socket.getRemoteSocketAddress()
        );

        var client = new ClientHandler(socket, id);


        new Thread(client).start();

        CLIENT_IDS.put(id, client);
        id++;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }
}