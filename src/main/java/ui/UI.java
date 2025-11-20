package ui;

import client.Client;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import utility.Utility;

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
    //convert string to byte array
    var message = textfield.getText();
    System.out.println("Wir senden an alle: " + message);
    try {
      writer.println(Utility.encrypt(message));
      textfield.clear();
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