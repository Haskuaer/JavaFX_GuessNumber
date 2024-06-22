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
    int startTime = 60;
    @FXML
    private Button guessButton;
    @FXML
    private TextField userGuessField;
    @FXML
    private Label resultDisplay;
    @FXML
    private Label timer;

    @FXML
    public void initialize() {

        numberToGuess = generateNumber();
        System.out.println(numberToGuess);
        timer();

        guessButton.setOnAction(event -> makeGuess());

        userGuessField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                makeGuess();
            }
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
                resultDisplay.getStyleClass().add("correct-guess");
            } else if (guess > numberToGuess) {
                resultDisplay.setText("Too high");
                numOfAttempts++;
            } else {
                resultDisplay.setText("Too low");
                numOfAttempts++;
            }

        } else if(guess < 0) {
            resultDisplay.setText("Only numbers from 1 to 100");
        } else {
            resultDisplay.setText("Inserted number is not a number");
        }
    }
}