package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private Socket client;

  public ClientHandler(Socket client) {
    this.client = client;
  }

  @Override
  public void run() {
    IO.println("Für den Client: " + client.getRemoteSocketAddress() + " wurde ein paralleler " +
        "Prozess gestartet");

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
        IO.println("Client sagt: " + input);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}