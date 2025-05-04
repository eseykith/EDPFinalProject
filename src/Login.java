import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {
    private HotelController controller;
    private Stage primaryStage;

    public Login(HotelController controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
    }

    public Scene getLoginScene() {
        // Create labels and fields
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(300);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        loginButton.setDefaultButton(true);

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px;");

        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.add(usernameLabel, 0, 0);
        formGrid.add(usernameField, 1, 0);
        formGrid.add(passwordLabel, 0, 1);
        formGrid.add(passwordField, 1, 1);
        formGrid.add(errorLabel, 1, 2);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(loginButton, cancelButton);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel, formGrid, buttonsBox);
        layout.setStyle("-fx-background-color: #F4F7FA; -fx-background-radius: 10;");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (isValidCredentials(username, password)) {
                // Open admin dashboard
                new AdminDashboard(controller).start(new Stage());
                primaryStage.close();
            } else {
                errorLabel.setText("Invalid credentials. Please try again.");
            }
        });

        cancelButton.setOnAction(e -> {
            // Go back to main screen
            MainGUI mainGUI = new MainGUI(controller);
            try {
                mainGUI.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return new Scene(layout, 1000, 600);
    }

    private boolean isValidCredentials(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}
