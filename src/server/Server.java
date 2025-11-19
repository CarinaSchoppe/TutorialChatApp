package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


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

    while (true) {
      try {
        Socket socket = serverSocket.accept();
        IO.println(
            "Client-Verbindung empfangen!" +
                " IP vom Client: "
                + socket.getRemoteSocketAddress()
        );

        new Thread(new ClientHandler(socket)).start();

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }
}