import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {
    private HotelController controller;
    private Stage primaryStage;

    public MainGUI() {
        this.controller = new HotelController(); // Initialize your controller
    }

    public MainGUI(HotelController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Hotel Reservation System");

        Label titleLabel = new Label("AI HOTEL RESERVATION SYSTEM");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Button adminButton = new Button("Admin");
        adminButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        adminButton.setOnAction(e -> showLoginScreen());

        Button guestButton = new Button("Guest");
        guestButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        guestButton.setOnAction(e -> showGuestScreen());

        HBox buttonLayout = new HBox(20, adminButton, guestButton);
        buttonLayout.setPadding(new Insets(20));
        buttonLayout.setStyle("-fx-alignment: center;");

        VBox mainLayout = new VBox(20, titleLabel, buttonLayout);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #F4F7FA; -fx-background-radius: 10;");
        mainLayout.setPrefSize(1000, 600);
        mainLayout.setStyle("-fx-alignment: center; -fx-background-color: #F4F7FA;");

        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoginScreen() {
        Login login = new Login(controller, primaryStage);
        primaryStage.setScene(login.getLoginScene());
        primaryStage.setTitle("Admin Login");
    }

    private void showGuestScreen() {
        GuestDashboard guestDashboard = new GuestDashboard(controller, primaryStage);
        guestDashboard.showAvailableRooms();
        primaryStage.setTitle("Available Rooms");
    }
}
