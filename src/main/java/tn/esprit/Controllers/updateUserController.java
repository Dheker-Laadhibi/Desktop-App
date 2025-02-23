package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.Interfaces.UpdateListener;
import tn.esprit.Models.User;
import tn.esprit.Services.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class updateUserController {
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField username;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField numTelField;

    @FXML
    private TextField emailField;
    // Add other fields for the remaining user properties...

    @FXML
    private Button updateButton;

    private UserService userService;

    private User currentUser; // To store the selected user for updating
    private profileUser profileController;

    public void setProfileController(profileUser profileController) {
        this.profileController = profileController;
    }
    private UpdateListener updateListener;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }
    // Method to initialize data when the update page is opened
    public void initData(User user) {
        currentUser = user;
        populateFields(); // Populate the form fields with the user data
    }
    profileUser r=new profileUser();

    // Method to populate the form fields with user data
    private void populateFields() {
        lastNameField.setText(currentUser.getLastName());
        firstNameField.setText(currentUser.getFirstName());
        numTelField.setText((String.valueOf(currentUser.getNumTel())));
        emailField.setText(currentUser.getAddress());
        username.setText((currentUser.getUsername()));

        // Populate other fields...
    }

    @FXML
    private void initialize() {
        userService = new UserService(); // Instantiate your UserService

        // Set action for the update button
        updateButton.setOnAction(event -> handleUpdateUser1());

        // You can add validation or other setup logic here...
    }
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to handle the update logic
    private void handleUpdateUser() {
        // Get the updated values from the form fields
        String updatedLastName = lastNameField.getText();
        String updatedFirstName = firstNameField.getText();
        // Get other updated values...
        String updatedNumTel = numTelField.getText();
        String updatedEmail = emailField.getText();
        if (!validateEmail(emailField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Adresse e-mail invalide.");
            return;
        }

        // Perform the update logic using your UserService
        currentUser.setLastName(updatedLastName);
        currentUser.setFirstName(updatedFirstName);
        // Set other updated values...
        currentUser.setNumTel(Integer.parseInt(updatedNumTel));
        currentUser.setEmail(updatedEmail);

        // Update the user using your UserService
        userService.modifier(currentUser);
        if (updateListener != null) {
            updateListener.onUpdate(currentUser);
        }

        // Show a confirmation alert
        showConfirmationAlert("User updated successfully!");

        // Close the update stage
        closeUpdateStage();
    }

    // Method to show a confirmation alert
    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to close the update stage
    private void closeUpdateStage() {
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    private void handleUpdateUser1() {
        // Get the updated values from the form fields
        String updatedLastName = lastNameField.getText();
        String updatedFirstName = firstNameField.getText();
        // Get other updated values...
        String updatedNumTel = numTelField.getText();
        String updatedEmail = emailField.getText();
        if (lastNameField.getText().isEmpty() || firstNameField.getText().isEmpty() || numTelField.getText().isEmpty() || updatedEmail.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Remplissez tous les champs.");
            return;
        }
        else if (!isNumeric( numTelField.getText()) ||  numTelField.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le numéro de téléphone doit être numérique et contenir 8 chiffres.");
            return;
        }


        // Perform the update logic using your UserService
        currentUser.setLastName(updatedLastName);
        currentUser.setFirstName(updatedFirstName);
        // Set other updated values...
        currentUser.setNumTel(Integer.parseInt(updatedNumTel));
        currentUser.setAddress(updatedEmail);

        // Update the user using your UserService
        userService.modifier(currentUser);
        if (updateListener != null) {
            updateListener.onUpdate(currentUser);
        }

        // Show a confirmation alert
        showConfirmationAlert("User updated successfully!");

        // Close the update stage
        closeUpdateStage();
    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

}