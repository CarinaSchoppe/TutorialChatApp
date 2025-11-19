package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {


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

  static void main() {

    try {

      serverSocket = new ServerSocket(PORT);

    } catch (IOException exception) {

      throw new RuntimeException(exception);
    }

    IO.println("ChatSystem Server erstellt!");

    var id = 0;


    while (true) {
      try {
        Socket socket = serverSocket.accept();


        IO.println(
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