package client;

import java.io.IOException;
import java.net.Socket;

public class Client {
  
  private static final int PORT = 12345;

  private static final String IP = "localhost";

  public static Socket socket;

  static void main() {

    try {
      socket = new Socket(IP, PORT);
      IO.println("Verbindung mit Server hergestellt auf Port:" + PORT);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }
}