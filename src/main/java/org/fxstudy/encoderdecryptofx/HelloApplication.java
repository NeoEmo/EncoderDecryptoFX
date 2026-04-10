package org.fxstudy.encoderdecryptofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EncoderDecryptMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/fxstudy/encoderdecryptofx/Theme.css")).toExternalForm());
        stage.setTitle("EncoderDecryptoFX 0.3");
        stage.setScene(scene);
        stage.show();
    }
}
