package studentrecords;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    // the only account, hardcoded for now
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;

    @FXML
    private void handleLogin() throws Exception {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText();

        // check if the fields are empty
        if (user.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("Please enter your username and password.");
            return;
        }

        // check if the account is right
        if (!user.equals(USERNAME) || !pass.equals(PASSWORD)) {
            errorLabel.setText("Incorrect USERNAME or PASSWORD!!!");
            passwordField.clear();
            return;
        }

        // login is correct so go to the dashboard
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(true);
        stage.centerOnScreen();
    }
}