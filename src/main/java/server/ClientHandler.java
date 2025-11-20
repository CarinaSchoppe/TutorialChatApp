package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

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
        var decoded = Base64.getDecoder().decode(input.getBytes());
        var msg = "Client[" + id + "] " + new String(decoded);
        System.out.println(msg);
        broadcast(msg);
      }

    } catch (IOException e) {
      var msg = "Verbindung mit " + id + " verloren!";
      Server.CLIENT_IDS.remove(id);
      broadcast(msg);
    }

  }

  private void broadcast(String message) {
    System.out.println(message);
    var encoded = Base64.getEncoder().encode(message.getBytes());
    message = new String(encoded);
    for (var user : Server.CLIENT_IDS.values()) {
      user.writer.println(message);
    }
  }

  public Socket getClient() {
    return client;
  }

  public int getID() {
    return id;
  }
}