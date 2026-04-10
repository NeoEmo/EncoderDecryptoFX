package org.fxstudy.encoderdecryptofx;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SecondController {
    public TextArea message;
    public TextArea key;
    public Label messageTxt;
    public Label keyTxt;
    public Button EncodeDecrypt;
    public Button backButton;
    public Label EncodeDecryptMessage;
    public Label EncodeDecryptKey;


    protected String language;
    protected int select;
    protected boolean isDecrypt;


    protected void switchScene(boolean isDecrypt, String language, int select) {
        this.language = language;
        this.select = select;
        this.isDecrypt = isDecrypt;

        if(!isDecrypt) {
            key.setVisible(false);
            key.setManaged(false);
            keyTxt.setVisible(false);
            keyTxt.setManaged(false);
            EncodeDecrypt.setText("Зашифровать");
        } else {
            EncodeDecrypt.setText("Расшифровать");
        }
    }

    @FXML
    private void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EncoderDecryptMain.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.sizeToScene();
    }

    @FXML
    private void copyToClipboard(MouseEvent event) {
        Label sourceLabel = (Label) event.getSource();
        String text = sourceLabel.getText();
        if (text != null && !text.isEmpty()) {
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            Clipboard.getSystemClipboard().setContent(content);
        }

        sourceLabel.setStyle("-fx-background-color: lightgray;");
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(200));
        pauseTransition.setOnFinished(e -> sourceLabel.setStyle(""));
        pauseTransition.play();
    }

    @FXML
    protected void startEncodeDecrypt() {
        String messageText = message.getText();
        if (messageText == null || messageText.trim().isEmpty()) {
            EncodeDecryptMessage.setText("Сообщение пустое!");
        }

        if (!isDecrypt) {
            switch (select) {
                case 1 -> {
                    SimpleEncode simpleEncode = new SimpleEncode();
                    simpleEncode.start(language, message.getText());
                    EncodeDecryptMessage.setText("Ваше сообщение: " + simpleEncode.encodeMessage);
                    EncodeDecryptKey.setText("Ключ для расшифровки: " + simpleEncode.key);
                }
                case 2 -> {
                    AdvancedEncode advancedEncode = new AdvancedEncode();
                    advancedEncode.start(language, message.getText());
                    EncodeDecryptMessage.setText("Ваше сообщение: " + advancedEncode.encodeMessage);
                    EncodeDecryptKey.setText("Ключ для расшифровки: " + advancedEncode.key);
                }
                case 3 -> {
                    AdvancedEncode advancedEncode = new AdvancedEncode();
                    advancedEncode.fullStart(language, message.getText());
                    EncodeDecryptMessage.setText("Ваше сообщение: " + advancedEncode.encodeMessage);
                    EncodeDecryptKey.setText("Ключ для расшифровки: " + advancedEncode.key);
                }
            }
        } else {
            switch (select) {
                case 1 -> {
                    SimpleDecrypt simpleDecrypt = new SimpleDecrypt();
                    simpleDecrypt.start(language, message.getText(), key.getText());
                    EncodeDecryptMessage.setText("Расшифровка данного сообщения: " + simpleDecrypt.getDecryptMessage());
                }
                case 2 -> {
                    AdvancedDecrypt advancedDecrypt = new AdvancedDecrypt();
                    advancedDecrypt.start(language, select, message.getText(), key.getText());
                    EncodeDecryptMessage.setText("Расшифровка данного сообщения: " + advancedDecrypt.getDecryptMessage());
                }
                case 3 -> {
                    AdvancedDecrypt advancedDecrypt = new AdvancedDecrypt();
                    advancedDecrypt.start(language, select, message.getText(), key.getText());
                    EncodeDecryptMessage.setText("Расшифровка данного сообщения: " + advancedDecrypt.getDecryptMessage());
                }
            }
        }
    }
}
