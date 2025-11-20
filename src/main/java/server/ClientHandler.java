package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import utility.Utility;

public class ClientHandler implements Runnable {

  private final Socket client;
  private final int id;
  private final PrintWriter writer;

  public ClientHandler(Socket client, int id) {
    this.client = client;
    this.id = id;
    try {
      writer = new PrintWriter(
          new BufferedWriter(new OutputStreamWriter(getClient().getOutputStream())),
          true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    System.out.println(
        "Für den Client (" + id + "): " + client.getRemoteSocketAddress() + " wurde ein " +
            "paralleler " +
            "Prozess gestartet");
    broadcast("Verbindung mit Client[" + id + "] hergestellt!");

    //schreib funktion


    //Streamreader / Bufferedstreamreader  -> Ein Eimer wo ich den Datenwasserfluss einsammeln kann
    // und warte bis ich
    // "genug" Daten habe.

    try {
      var reader = new BufferedReader(
          new InputStreamReader(
              client.getInputStream()
          ));


      //Die reinkommende Zeile vom Sender
      String input;
      while ((input = reader.readLine()) != null) {
        try {
          var msg = "Client[" + id + "] " + Utility.decrypt(input);
          System.out.println(msg);
          broadcast(msg);
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
      var msg = "Verbindung mit " + id + " verloren!";
      Server.CLIENT_IDS.remove(id);
      broadcast(msg);
    }

  }

  public void broadcast(String message) {
    System.out.println(message);
    for (var user : Server.CLIENT_IDS.values()) {
      try {
        user.writer.println(Utility.encrypt(message));
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
  }

  public Socket getClient() {
    return client;
  }

  public int getID() {
    return id;
  }
}