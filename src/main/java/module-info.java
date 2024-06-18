module number.guess.guessnumber {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens number.guess.guessnumber to javafx.fxml;
    exports number.guess.guessnumber;
}