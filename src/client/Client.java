package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  //der soll nun auch nachrichten Empfangen können



  private static final int PORT = 12345;

  private static final String IP = "localhost";

  public static Socket socket;

  static void main() {

    try {
      socket = new Socket(IP, PORT);
      IO.println("Verbindung mit Server hergestellt auf Port:" + PORT);


      var scanner = new Scanner(System.in);
      var writer = new PrintWriter(
          new BufferedWriter(
              new OutputStreamWriter(
                  socket.getOutputStream())), true);
      while (true) {
        var message = scanner.nextLine();
        IO.println("Wir schicken an den Server: " + message);
        writer.println(message);
      }


    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }
}