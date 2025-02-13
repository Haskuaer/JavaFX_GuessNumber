package number.guess.guessnumber;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Date;
import java.util.Random;

public class Controler {

    int numberToGuess;
    int guess;
    int numOfAttempts = 1;
    int startTime = 59;
    Timeline timeline;
    boolean gameStarted = false;
    @FXML
    private Button guessButton;
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button stopButton;
    @FXML
    private TextField userGuessField;
    @FXML
    private Label resultDisplay;
    @FXML
    private Label timer;

    @FXML
    public void initialize() {

        startButton.setOnAction(event -> {

            if(!gameStarted) {

                numberToGuess = generateNumber();

                startButton.setDisable(true);
                resetButton.setDisable(false);
                stopButton.setDisable(false);
                guessButton.setDisable(false);
                userGuessField.setDisable(false);

                timer();
                gameStarted = true;
                resultDisplay.getStyleClass().remove("correct-guess");
                resultDisplay.getStyleClass().remove("times-up");
                resultDisplay.getStyleClass().add("result-display");
                resultDisplay.setText("Make a guess");
                numOfAttempts = 1;

                guessButton.setOnAction(eve -> {
                    makeGuess();
                    userGuessField.setText("");
                });

                userGuessField.setOnKeyPressed(eve -> {
                    if (eve.getCode() == KeyCode.ENTER) {
                        makeGuess();
                        userGuessField.setText("");
                    }
                });

            } else {

                startButton.setDisable(true);
                stopButton.setDisable(false);
                guessButton.setDisable(false);
                userGuessField.setDisable(false);

                startButton.setText("Start");

                timeline.play();

            }
        });

        resetButton.setOnAction(event -> reset());

        stopButton.setOnAction(event -> stopTimer());
    }

    private void timer() {

        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (startTime > 0) {
                if(startTime < 10){
                    timer.setText("00:0"+startTime);
                } else {
                    timer.setText("00:" + startTime);
                }
                startTime--;
            } else {

                timer.setText("00:00");
                resultDisplay.setText("Time's over!");

                resultDisplay.getStyleClass().remove("result-display");
                resultDisplay.getStyleClass().add("times-up");

                guessButton.setDisable(true);
                stopButton.setDisable(true);
                userGuessField.setDisable(true);

                gameStarted = false;
                numOfAttempts = 1;
                startTime = 59;

                stopTimer();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopTimer() {
        if (timeline != null) {

            guessButton.setDisable(true);
            stopButton.setDisable(true);
            userGuessField.setDisable(true);

            startButton.setDisable(false);

            timeline.stop();
        }
    }

    private void reset(){

        if(timeline != null && gameStarted){

            timeline.stop();

            resultDisplay.setText("Correct number: " + numberToGuess);

            startButton.setDisable(false);
            resetButton.setDisable(true);
            stopButton.setDisable(true);
            guessButton.setDisable(true);
            userGuessField.setDisable(true);

            gameStarted = false;
            numOfAttempts = 1;
            startTime = 59;
        }
    }

    private int generateNumber(){

        Random rand = new Random();
        //System.out.println(number);
        return rand.nextInt(100)+1;
    }


    private boolean isNumeric(String str){

        if(str == null || str.isEmpty()){
            return false;
        }
        try{

            Integer.parseInt(str);
            return true;

        } catch(NumberFormatException e){
            return false;
        }
    }

    private void makeGuess(){

        String input = userGuessField.getText();

        if(isNumeric(input)) {

            guess = Integer.parseInt(input);
            if (guess == numberToGuess) {

                resultDisplay.setText("You guessed correctly!\nAttempts: " + numOfAttempts);

                resultDisplay.getStyleClass().remove("result-display");
                resultDisplay.getStyleClass().add("correct-guess");

                resetButton.setDisable(true);
                stopButton.setDisable(true);
                guessButton.setDisable(true);
                userGuessField.setDisable(true);

                gameStarted = false;
                numOfAttempts = 1;
                startTime = 59;

                stopTimer();

            } else if (guess > numberToGuess && guess <= 100) {
                resultDisplay.setText(guess +" is too high");
                numOfAttempts++;
            } else if (guess < numberToGuess && guess > 0) {
                resultDisplay.setText(guess + " is too low");
                numOfAttempts++;
            } else {
                resultDisplay.setText(guess + " is out of range");
            }
        } else {
            resultDisplay.setText("Inserted value is not a number");
        }
    }
}