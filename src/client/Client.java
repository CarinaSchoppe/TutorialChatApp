package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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


      //lese Funktion
      new Thread(() -> {

        try {
          var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          String message;
          while ((message = reader.readLine()) != null) {
            if (message.equalsIgnoreCase("Quit")) {
              break;
            }
            IO.println("Server sendet: " + message);
          }

        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }).start();
      var scanner = new Scanner(System.in);
      var writer = new PrintWriter(
          new BufferedWriter(
              new OutputStreamWriter(
                  socket.getOutputStream())), true);


      while (true) {
        var message = scanner.nextLine();
        if (message.equals("Quitter")) {
          break;
        }

        IO.println("Wir schicken an den Server: " + message);
        writer.println(message);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }
}