package client;

import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.UI;

public class Client extends Application {

  //der soll nun auch nachrichten Empfangen können


  private static final int PORT = 12345;

  private static final String IP = "localhost";

  private UI ui;

  public static Socket socket;

  public static void main(String[] args) {
    launch();


  }


  @Override
  public void start(Stage stage) throws Exception {
    try {
      socket = new Socket(IP, PORT);
      System.out.println("Verbindung mit Server hergestellt auf Port:" + PORT);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    var fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chatappui.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);

    ui = fxmlLoader.getController();
    var receiver = new Reciever(ui, socket);
    new Thread(receiver).start();

    stage.setTitle("ChatApp");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.setOnCloseRequest(event -> {
      System.exit(0);
    });
    stage.show();

  }
}