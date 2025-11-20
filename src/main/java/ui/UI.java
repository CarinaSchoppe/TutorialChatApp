package ui;

import client.Client;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UI {


  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private TextArea chatContent;

  @FXML
  private Button sendButton;

  @FXML
  private TextField textfield;

  @FXML
  void onSend(ActionEvent event) {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(
          new BufferedWriter(
              new OutputStreamWriter(
                  Client.socket.getOutputStream())), true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Wir schicken an den Server: " + textfield.getText());
    //convert string to byte array
    var message = textfield.getText();
    var bytes = message.getBytes();
    var encode = Base64.getEncoder().encode(bytes);
    var encoded = new String(encode);
    System.out.println("Wir senden an alle: " + message);
    writer.println(encoded);
    textfield.clear();

  }

  @FXML
  void initialize() {
    assert chatContent != null :
        "fx:id=\"chatContent\" was not injected: check your FXML file 'Untitled'.";
    assert sendButton != null :
        "fx:id=\"sendButton\" was not injected: check your FXML file 'Untitled'.";
    assert textfield != null :
        "fx:id=\"textfield\" was not injected: check your FXML file 'Untitled'.";

  }


  public void addMessage(String message) {
    Platform.runLater(() -> {

      if (!chatContent.getText().isEmpty()) {
        chatContent.appendText("\n");
      }
      chatContent.appendText(message);

    });
  }
}