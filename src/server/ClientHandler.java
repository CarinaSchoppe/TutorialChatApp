package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private final Socket client;
  private final int id;

  public ClientHandler(Socket client, int id) {
    this.client = client;
    this.id = id;
  }

  @Override
  public void run() {
    IO.println("Für den Client (" + id + "): " + client.getRemoteSocketAddress() + " wurde ein " +
        "paralleler " +
        "Prozess gestartet");

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
        IO.println("Client(" + id + ") sagt: " + input);


      }

    } catch (IOException e) {
      IO.println("Verbindung mit " + id + " verloren!");
      Server.CLIENT_IDS.remove(id);
    }

  }

  public Socket getClient() {
    return client;
  }

  public int getID() {
    return id;
  }
}