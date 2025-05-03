import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainGUI extends Application {
    private HotelController controller;
    private Stage primaryStage; // keep reference to primaryStage

    public MainGUI(HotelController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // save reference

        primaryStage.setTitle("Hotel Reservation System");

        // Create a label for the title with color
        Label titleLabel = new Label("AI HOTEL RESERVATION SYSTEM");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Create buttons for Admin and Guest with colors
        Button adminButton = new Button("Admin");
        adminButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        adminButton.setOnAction(e -> showLoginDialog());

        Button guestButton = new Button("Guest");
        guestButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");

        // Create a horizontal layout for the buttons
        HBox buttonLayout = new HBox(20);
        buttonLayout.getChildren().addAll(adminButton, guestButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Main Layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(javafx.geometry.Pos.CENTER);
        mainLayout.getChildren().addAll(titleLabel, buttonLayout);

        // Set padding, background color, and rounded corners
        mainLayout.setPadding(new javafx.geometry.Insets(20));
        mainLayout.setStyle("-fx-background-color: #F4F7FA; -fx-background-radius: 10;");

        // Create and set the scene
        Scene scene = new Scene(mainLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoginDialog() {
        // Create a new dialog for login
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Admin Login");
        dialog.setHeaderText("Please enter your credentials");

        // Create the username and password fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Create a grid pane to hold the fields
        GridPane grid = new GridPane();
        grid.setStyle("-fx-padding: 20px;");
        grid.setVgap(10);
        grid.setHgap(5);

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        dialog.getDialogPane().setContent(grid);

        // Add buttons for login and cancel
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        // Set the preferred size of the dialog
        dialog.getDialogPane().setPrefSize(400, 200);

        // Convert the result to a Boolean value
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                // Check credentials
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (isValidCredentials(username, password)) {
                    return true; // Login successful
                } else {
                    showAlert("Invalid credentials. Please try again.");
                    return false; // Login failed
                }
            }
            return false; // Cancelled
        });

        // Show the dialog and wait for the result
        dialog.showAndWait().ifPresent(result -> {
            if (result) {
                // Close the main GUI window
                primaryStage.close();
                // Open admin dashboard
                new AdminDashboard(controller).start(new Stage());
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        // Default username and password for admin
        return "admin".equals(username) && "password".equals(password);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
