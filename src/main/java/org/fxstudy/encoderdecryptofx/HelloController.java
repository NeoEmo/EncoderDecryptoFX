package org.fxstudy.encoderdecryptofx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class HelloController {
    public CheckBox DecryptBox;
    public Label welcomeText;
    public Label intro;
    public Label first;
    public Label second;
    public Label third;
    public Label labelCheckBox;
    public ChoiceBox chooseLang;
    public Button one;
    public Button two;
    public Button three;
    Timeline currentTimeLine;
    List<String> welcomeLines;

    public void initialize() {
        try (InputStream is = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/WelcomeText")) {
            welcomeLines = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is))).lines().toList();
        } catch (NullPointerException | IOException e) {
            welcomeLines = List.of("Ошибка загрузки текста.");
        }

        welcome();
    }

    private void animateText(Label target, String text, Runnable onFinished) {
        if (currentTimeLine != null) {
            currentTimeLine.stop();
        }
        target.setText("");
        StringBuilder current = new StringBuilder();
        int[] index = {0};

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), event ->{
            if (index[0] < text.length()) {
                current.append(text.charAt(index[0]++));
                target.setText(current.toString());
            } else {
                timeline.stop();
                if(onFinished != null) {
                    onFinished.run();
                }
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        currentTimeLine = timeline;
        timeline.play();
    }

    @FXML
    private void switchToSecondScene(Button myButton, boolean isDecrypt, String language, int select) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fxstudy/encoderdecryptofx/EncoderDecryptSecond.fxml"));
        Parent root = loader.load();
        SecondController controller = loader.getController();
        controller.switchScene(isDecrypt, language, select);
        Stage stage = (Stage) myButton.getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    @FXML
    protected void welcome() {
        String welcomeTxt = welcomeLines.getFirst();
        String introTxt = welcomeLines.get(1);
        String firstTxt = welcomeLines.get(2);
        String secondTxt = welcomeLines.get(3);
        String thirdTxt = welcomeLines.get(4);
        String labelCheckBoxTxt = welcomeLines.getLast();
        animateText(welcomeText, welcomeTxt, () -> {
            animateText(intro, introTxt, () -> {
                animateText(first, firstTxt, () -> {
                    animateText(second, secondTxt, () -> {
                        animateText(third, thirdTxt, () -> {
                            animateText(labelCheckBox, labelCheckBoxTxt, null);
                        });
                    });
                });
            });
        });
    }

    @FXML
    protected void pressOne(){
        if (DecryptBox.isSelected()) {
            String language = chooseLang.getValue().toString();
            try {
                switchToSecondScene(one, DecryptBox.isSelected(), language, 1);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось переключить сцену");
            }
        } else {
            String language = chooseLang.getValue().toString();
            try {
                switchToSecondScene(one, DecryptBox.isSelected(), language, 1);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось переключить сцену");
            }
        }
    }

    @FXML
    protected void pressTwo() {
        if (DecryptBox.isSelected()) {
            String language = chooseLang.getValue().toString();
            try {
                switchToSecondScene(two, DecryptBox.isSelected(), language, 2);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось переключить сцену");
            }
        } else {
            String language = chooseLang.getValue().toString();
            try {
                switchToSecondScene(two, DecryptBox.isSelected(), language, 2);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось переключить сцену");
            }
        }
    }

    @FXML
    protected void pressThree(){
        if (DecryptBox.isSelected()) {
            String language = chooseLang.getValue().toString();
            try {
                switchToSecondScene(three, DecryptBox.isSelected(), language, 3);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось переключить сцену");
            }
        } else {
            String language = chooseLang.getValue().toString();
            try {
                switchToSecondScene(three, DecryptBox.isSelected(), language, 2);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Не удалось переключить сцену");
            }
        }
    }

}
