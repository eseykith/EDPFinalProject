import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GuestForm {
    private HotelController controller;
    private Stage primaryStage;
    private Room selectedRoom;

    // Updated constructor to accept selectedRoom when navigated from room list
    public GuestForm(HotelController controller, Stage primaryStage, Room selectedRoom) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        this.selectedRoom = selectedRoom;
    }

    public Scene getGuestScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: #F4F7FA;");

        Label title = new Label("Reservation Form - Room: " + (selectedRoom != null ? selectedRoom.getRoomID() : "N/A"));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        grid.add(title, 0, 0, 2, 1);

        // Guest personal info fields
        // Removed GuestID input field, ID will be auto-generated
        Label guestIdInfoLabel = new Label("Guest ID will be auto-generated.");
        guestIdInfoLabel.setStyle("-fx-font-style: italic;");
        grid.add(guestIdInfoLabel, 0, 1, 2, 1);

        Label fnameLabel = new Label("First Name:");
        TextField fnameField = new TextField();
        grid.add(fnameLabel, 0, 2);
        grid.add(fnameField, 1, 2);

        Label lnameLabel = new Label("Last Name:");
        TextField lnameField = new TextField();
        grid.add(lnameLabel, 0, 3);
        grid.add(lnameField, 1, 3);

        Label cityLabel = new Label("City:");
        TextField cityField = new TextField();
        grid.add(cityLabel, 0, 4);
        grid.add(cityField, 1, 4);

        Label provinceLabel = new Label("Province:");
        TextField provinceField = new TextField();
        grid.add(provinceLabel, 0, 5);
        grid.add(provinceField, 1, 5);

        Label contactLabel = new Label("Contact Number:");
        TextField contactField = new TextField();
        grid.add(contactLabel, 0, 6);
        grid.add(contactField, 1, 6);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        grid.add(emailLabel, 0, 7);
        grid.add(emailField, 1, 7);

        // Reservation info fields
        Label checkInLabel = new Label("Check-in Date:");
        DatePicker checkInPicker = new DatePicker();
        grid.add(checkInLabel, 0, 8);
        grid.add(checkInPicker, 1, 8);

        Label checkOutLabel = new Label("Check-out Date:");
        DatePicker checkOutPicker = new DatePicker();
        grid.add(checkOutLabel, 0, 9);
        grid.add(checkOutPicker, 1, 9);


        Label reservationDateLabel = new Label("Reservation Date: ");
        LocalDate date = LocalDate.now();


        Label occupantsLabel = new Label("Number of Occupants (max " + (selectedRoom != null ? selectedRoom.getOccupancyLimit() : 1) + "):");
        Spinner<Integer> occupantsSpinner = new Spinner<>(1, selectedRoom != null ? selectedRoom.getOccupancyLimit() : 1, 1);
        occupantsSpinner.setEditable(true);
        grid.add(occupantsLabel, 0, 10);
        grid.add(occupantsSpinner, 1, 10);

        Button reserveBtn = new Button("Reserve");
        reserveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        grid.add(reserveBtn, 1, 11);
        GridPane.setHalignment(reserveBtn, HPos.RIGHT);

        Button backBtn = new Button("Back to Rooms");
        backBtn.setStyle("-fx-background-color: #808080; -fx-text-fill: white; -fx-font-size: 14px;");
        grid.add(backBtn, 0, 11);

        reserveBtn.setOnAction(e -> {
            if (!validateInputs(fnameField, lnameField, cityField, provinceField,
                    contactField, emailField, checkInPicker, checkOutPicker, occupantsSpinner)) {
                return;
            }

            String guestID = controller.getNextGuestID();
            String reservationID = controller.getNextReservationID();

            Guest guest = new Guest(
                    guestID,
                    fnameField.getText().trim(),
                    lnameField.getText().trim(),
                    cityField.getText().trim(),
                    provinceField.getText().trim(),
                    contactField.getText().trim(),
                    emailField.getText().trim()
            );

        });

        backBtn.setOnAction(e -> {
            GuestDashboard guestDashboard = new GuestDashboard(controller, primaryStage);
            guestDashboard.showAvailableRooms();
        });

        Scene scene = new Scene(grid, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reservation Form");
        primaryStage.show();

        return scene;
    }

    private boolean validateInputs(TextField fnameField, TextField lnameField, TextField cityField,
                                   TextField provinceField, TextField contactField, TextField emailField,
                                   DatePicker checkInPicker, DatePicker checkOutPicker,
                                   Spinner<Integer> occupantsSpinner) {

        if (fnameField.getText().trim().isEmpty() || lnameField.getText().trim().isEmpty() ||
                cityField.getText().trim().isEmpty() || provinceField.getText().trim().isEmpty() ||
                contactField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all personal details.");
            return false;
        }

        if (!contactField.getText().matches("\\d{11}")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Contact number must be exactly 11 digits.");
            return false;
        }

        if (!emailField.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a valid email address.");
            return false;
        }

        if (checkInPicker.getValue() == null || checkOutPicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select check-in and check-out dates.");
            return false;
        }

        if (!checkOutPicker.getValue().isAfter(checkInPicker.getValue())) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Check-out date must be after check-in date.");
            return false;
        }

        if (occupantsSpinner.getValue() == null || occupantsSpinner.getValue() <= 0) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Number of occupants must be positive.");
            return false;
        }

        if (selectedRoom != null && occupantsSpinner.getValue() > selectedRoom.getOccupancyLimit()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error",
                    "Number of occupants exceeds room occupancy limit (" + selectedRoom.getOccupancyLimit() + ").");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
}
