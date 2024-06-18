package number.guess.guessnumber;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Date;

public class Controler {

    @FXML
    private Button guessButton;
    @FXML
    private TextField userGuess;
    @FXML
    private Label resultDisplay;
    @FXML
    private Label timer;
    int startTime = 60;

    @FXML
    public void initialize() {
        guessButton.setOnAction(event -> {
            timer();
        });
    }

    private void timer() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (startTime > 0) {
                System.out.println(startTime);
                startTime--;
            } else {
                ((Timeline) event.getSource()).stop();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
