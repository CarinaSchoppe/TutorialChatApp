package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Base64;
import ui.UI;

public class Reciever implements Runnable {

  private final UI ui;
  private final Socket socket;


  public Reciever(UI ui, Socket socket) {
    this.ui = ui;
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      String message;
      while ((message = reader.readLine()) != null) {
        System.out.println("send" + message);
        var decoded = Base64.getDecoder().decode(message);
        message = new String(decoded);
        if (message.equalsIgnoreCase("Quit")) {
          break;
        }
        System.out.println(message);
        ui.addMessage(message);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}