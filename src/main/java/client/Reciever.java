package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import ui.UI;
import utility.Utility;

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
        if (message.equalsIgnoreCase("Quit")) {
          break;
        }
        try {
          System.out.println(Utility.decrypt(message));
          ui.addMessage(Utility.decrypt(message));
        } catch (NoSuchPaddingException e) {
          throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
          throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
          throw new RuntimeException(e);
        } catch (BadPaddingException e) {
          throw new RuntimeException(e);
        }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}