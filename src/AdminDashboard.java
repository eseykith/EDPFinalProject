// Keep all existing imports
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AdminDashboard extends Stage {
    private HotelController controller;
    private TableView<Room> roomTable;
    private TableView<Guest> guestTable;
    private TableView<Reservation> reservationTable;
    private TableView<Payment> paymentTable;

    private TextField roomSearchField, guestSearchField;
    private Button roomSearchButton, guestSearchButton;

    // Room Form Fields and Buttons
    private TextField roomID, roomType, roomStatus, rate, occupancy;
    private Button confirm, updateRoom, deleteRoom, createRoom;
    private VBox formContainer;

    private VBox centerContent;
    private VBox tableBox;
    private BorderPane root;
    private Stage primaryStage;

    public AdminDashboard(HotelController controller) {
        this.controller = controller;
    }

    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Admin Dashboard");

        // Side Menu
        VBox sideMenu = createSideMenu();

        // Root Layout
        root = new BorderPane();
        root.setLeft(sideMenu);

        // Center Content
        centerContent = createCenterContent();

        ScrollPane scrollPane = new ScrollPane(centerContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Create Side Menu with buttons and logout at bottom
    private VBox createSideMenu() {
        VBox sideMenu = new VBox();
        sideMenu.setPadding(new Insets(20));
        sideMenu.setStyle("-fx-background-color: #2E3A59;");
        sideMenu.setPrefWidth(200);
        sideMenu.setSpacing(20);

        Label services = new Label("Services");
        services.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        services.setStyle("-fx-text-fill: #FFFFFF;");

        Button back = new Button("Back");
        back.setMaxWidth(Double.MAX_VALUE);
        back.setStyle("-fx-background-color: #FFFFFF; -fx-font-weight: bold;");
        back.setOnAction(event -> {
            AdminDashboard adminDashboard = new AdminDashboard(controller);
            try {
                adminDashboard.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button manageRoomBtn = createSideButton("Rooms", e -> manageRoom());
        Button manageGuestBtn = createSideButton("Guest", e -> manageGuest());
        Button managePaymentBtn = createSideButton("Payment", e -> managePayment());
        Button manageReservationBtn = createSideButton("Reservation", e -> manageReservation());

        // Spacer Pane to push logout to bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-weight: bold;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: #f08080; -fx-text-fill: white;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;"));
        logoutButton.setOnAction(e -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Logout");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to logout?");
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    new MainGUI(controller).start(new Stage());
                    primaryStage.close();
                }
            });
        });

        sideMenu.getChildren().addAll(
                services,
                back,
                manageRoomBtn,
                manageGuestBtn,
                managePaymentBtn,
                manageReservationBtn,
                spacer,
                logoutButton
        );

        return sideMenu;
    }

    private Button createSideButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefHeight(70);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #6A9FF6; -fx-text-fill: white;-fx-font-weight: bold;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white;-fx-font-weight: bold;"));
        button.setOnAction(handler);
        return button;
    }

    private VBox createCenterContent() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setStyle("-fx-background-color: #F4F7FA;");

        Label titleLabel = new Label("AI HOTEL RESERVATION SYSTEM");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #1E5AA8;");
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        Image image = new Image("/hotelimg.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(750);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        vbox.getChildren().addAll(titleLabel, imageView);
        return vbox;
    }

    // Room Management UI and Logic
    private void manageRoom() {
        centerContent.getChildren().clear();

        Label header = new Label("Check Available Room");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #1E5AA8;");
        header.setAlignment(Pos.CENTER);

        // Initialize search UI
        roomSearchField = new TextField();
        roomSearchField.setPromptText("Search Room...");
        roomSearchButton = new Button("Search");
        roomSearchButton.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");
        roomSearchButton.setOnAction(e -> searchRooms());

        HBox searchBox = new HBox(10, roomSearchField, roomSearchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10));

        // Table and buttons box
        tableBox = new VBox();
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(10));
        tableBox.getChildren().clear();

        roomTable = controller.loadRooms();

        VBox tableAndButton = new VBox(10, searchBox, roomTable);
        tableAndButton.setAlignment(Pos.CENTER);
        tableAndButton.setPadding(new Insets(10));

        createRoom = new Button("CREATE");
        createRoom.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");

        updateRoom = new Button("UPDATE");
        updateRoom.setStyle("-fx-background-color: #f0ad4e; -fx-text-fill: white; -fx-font-weight: bold;");

        deleteRoom = new Button("DELETE");
        deleteRoom.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox buttons = new HBox(10, createRoom, updateRoom, deleteRoom);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        // Create room form hidden initially
        createRoomForm();

        tableBox.getChildren().addAll(tableAndButton, buttons, formContainer);

        centerContent.getChildren().addAll(header, tableBox);
    }

    private void createRoomForm() {
        // Initialize Room Form fields and labels
        roomID = new TextField();
        roomID.setPromptText("Room ID");
        roomID.setPrefWidth(500);
        roomType = new TextField();
        roomType.setPromptText("Room Type");
        roomStatus = new TextField();
        roomStatus.setPromptText("Room Status");
        rate = new TextField();
        rate.setPromptText("Rate");
        occupancy = new TextField();
        occupancy.setPromptText("Occupancy");

        Label roomIDLabel = new Label("Room ID:");
        Label roomTypeLabel = new Label("Room Type:");
        Label roomStatusLabel = new Label("Room Status:");
        Label rateLabel = new Label("Rate:");
        Label occupancyLabel = new Label("Occupancy:");

        for (Label label : new Label[]{roomIDLabel, roomTypeLabel, rateLabel, occupancyLabel, roomStatusLabel}) {
            label.setStyle("-fx-text-fill: #2E3A59; -fx-font-weight: bold;");
        }

        GridPane roomForm = new GridPane();
        roomForm.setHgap(10);
        roomForm.setVgap(15);
        roomForm.setPadding(new Insets(20));
        roomForm.add(roomIDLabel, 0, 0);
        roomForm.add(roomID, 1, 0);
        roomForm.add(roomTypeLabel, 0, 1);
        roomForm.add(roomType, 1, 1);
        roomForm.add(rateLabel, 0, 2);
        roomForm.add(rate, 1, 2);
        roomForm.add(occupancyLabel, 0, 3);
        roomForm.add(occupancy, 1, 3);
        roomForm.add(roomStatusLabel, 0, 4);
        roomForm.add(roomStatus, 1, 4);

        confirm = new Button("Confirm");
        confirm.setStyle("-fx-background-color: #1E5AA8; -fx-text-fill: white; -fx-font-weight: bold;");

        formContainer = new VBox(10, roomForm, confirm);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setVisible(false);
        formContainer.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-color: #dcdcdc; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        // Event Handlers
        createRoom.setOnAction(ev -> {
            formContainer.setVisible(true);
            confirm.setVisible(true);
            roomID.setDisable(false);
            roomID.clear();
            roomType.clear();
            roomStatus.clear();
            rate.clear();
            occupancy.clear();
        });

        confirm.setOnAction(event -> {
            try {
                // Validate inputs
                if (roomID.getText().isEmpty() || roomStatus.getText().isEmpty() || roomType.getText().isEmpty()
                        || rate.getText().isEmpty() || occupancy.getText().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please fill in all fields.", ButtonType.OK).showAndWait();
                    return;
                }

                double roomRate = Double.parseDouble(rate.getText());
                int roomOccupancy = Integer.parseInt(occupancy.getText());

                Room room = new Room(roomID.getText(), roomStatus.getText(), roomType.getText(), roomRate, roomOccupancy);
                controller.addRoom(room);

                roomID.clear();
                roomType.clear();
                roomStatus.clear();
                rate.clear();
                occupancy.clear();
                formContainer.setVisible(false);
                confirm.setVisible(false);
                manageRoom(); // Refresh table
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Rate must be a number and Occupancy must be an integer.", ButtonType.OK).showAndWait();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to add room: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        });

        updateRoom.setOnAction(e -> {
            Room selected = roomTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a room to update.", ButtonType.OK).showAndWait();
                return;
            }
            roomID.setText(selected.getRoomID());
            roomType.setText(selected.getRoomType());
            roomStatus.setText(selected.getRoomStatus());
            rate.setText(String.valueOf(selected.getRate()));
            occupancy.setText(String.valueOf(selected.getOccupancyLimit()));
            roomID.setDisable(true);

            formContainer.setVisible(true);
            confirm.setVisible(false); // hide create confirm button if visible

            // Create update confirm button if not already added
            Button confirmUpdate = new Button("Update Confirm");
            confirmUpdate.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
            if (!formContainer.getChildren().contains(confirmUpdate)) {
                formContainer.getChildren().add(confirmUpdate);
            }

            confirmUpdate.setOnAction(event -> {
                try {
                    selected.setStatus(roomStatus.getText());
                    selected.setRoomType(roomType.getText());
                    selected.setOccupancyLimit(Integer.parseInt(occupancy.getText()));
                    selected.setRate(Double.parseDouble(rate.getText()));

                    controller.updateRoom(selected);
                    formContainer.setVisible(false);
                    formContainer.getChildren().remove(confirmUpdate);
                    roomID.setDisable(false);
                    manageRoom(); // Refresh table
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Failed to update room: " + ex.getMessage(), ButtonType.OK).showAndWait();
                }
            });
        });

        deleteRoom.setOnAction(e -> {
            Room selected = roomTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a room to delete.", ButtonType.OK).showAndWait();
                return;
            }

            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete room " + selected.getRoomID() + "?", ButtonType.YES, ButtonType.NO);
            confirmDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    controller.deleteRoom(selected.getRoomID());
                    manageRoom(); // Refresh table
                }
            });
        });
    }

    // Search for rooms using controller search
    private void searchRooms() {
        String searchText = roomSearchField.getText().toLowerCase().trim();
        if (roomTable != null) {
            try {
                ObservableList<Room> filteredRooms = controller.searchRooms(searchText);
                roomTable.setItems(filteredRooms);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to search rooms: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    // Guest Management UI and Logic
    private void manageGuest() {
        centerContent.getChildren().clear();

        Label header = new Label("Guest");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #1E5AA8;");
        header.setAlignment(Pos.CENTER);

        guestSearchField = new TextField();
        guestSearchField.setPromptText("Search Guest...");
        guestSearchButton = new Button("Search");
        guestSearchButton.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");
        guestSearchButton.setOnAction(e -> searchGuest());

        HBox guestSearchBox = new HBox(10, guestSearchField, guestSearchButton);
        guestSearchBox.setAlignment(Pos.CENTER);
        guestSearchBox.setPadding(new Insets(10));

        tableBox = new VBox();
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(10));
        tableBox.getChildren().clear();

        guestTable = controller.loadGuest();

        VBox tableAndButton = new VBox(10, guestSearchBox, guestTable);
        tableAndButton.setAlignment(Pos.CENTER);
        tableAndButton.setPadding(new Insets(10));

        HBox buttons = new HBox(); // Optional placeholder for guest buttons
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        tableBox.getChildren().addAll(tableAndButton, buttons);
        centerContent.getChildren().addAll(header, tableBox);
    }

    // Search guests using controller search
    private void searchGuest() {
        String searchText = guestSearchField.getText().toLowerCase().trim();
        if (guestTable != null) {
            try {
                ObservableList<Guest> filteredGuest = controller.searchGuest(searchText);
                guestTable.setItems(filteredGuest);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to search guests: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    // Payment Management UI and Logic
    private void managePayment() {
        centerContent.getChildren().clear();

        Label header = new Label("Payment");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #1E5AA8;");
        header.setAlignment(Pos.CENTER);

        tableBox = new VBox();
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(10));
        tableBox.getChildren().clear();

        paymentTable = controller.loadPayment();

        VBox tableAndButton = new VBox(10, paymentTable);
        tableAndButton.setAlignment(Pos.CENTER);
        tableAndButton.setPadding(new Insets(10));

        HBox buttons = new HBox(); // Optional buttons for payment
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        tableBox.getChildren().addAll(tableAndButton, buttons);
        centerContent.getChildren().addAll(header, tableBox);
    }

    // Reservation Management UI and Logic
    private void manageReservation() {
        centerContent.getChildren().clear();

        Label header = new Label("Reservation");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #1E5AA8;");
        header.setAlignment(Pos.CENTER);

        tableBox = new VBox();
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setPadding(new Insets(10));
        tableBox.getChildren().clear();

        reservationTable = controller.loadReservation();

        // Show reservation details on selection
        reservationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showReservationDetails(newSelection);
            }
        });

        VBox tableAndButton = new VBox(10, reservationTable);
        tableAndButton.setAlignment(Pos.CENTER);
        tableAndButton.setPadding(new Insets(10));

        HBox buttons = new HBox(); // Optional buttons for reservation
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        tableBox.getChildren().addAll(tableAndButton, buttons);
        centerContent.getChildren().addAll(header, tableBox);
    }

    // Show detailed reservation info dialog
    private void showReservationDetails(Reservation reservation) {
        ReservationDetails details = controller.getReservationDetails(reservation.getReservationID());
        if (details == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reservation Details");
            alert.setHeaderText(null);
            alert.setContentText("No detailed information available for this reservation.");
            alert.showAndWait();
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Reservation Details");
        dialog.getDialogPane().setPrefSize(400, 300);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Reservation ID:"), 0, 0);
        grid.add(new Label(details.getReservationID()), 1, 0);
        grid.add(new Label("Total Amount:"), 0, 1);
        grid.add(new Label(String.format("PHP%.2f", details.getTotalAmount())), 1, 1);
        grid.add(new Label("Total Occupants:"), 0, 2);
        grid.add(new Label(String.valueOf(details.getTotalOccupants())), 1, 2);
        grid.add(new Label("Reservation Date:"), 0, 3);
        grid.add(new Label(details.getReservationDate().toString()), 1, 3);
        grid.add(new Label("Check-In Date:"), 0, 4);
        grid.add(new Label(details.getCheckInDate().toString()), 1, 4);
        grid.add(new Label("Check-Out Date:"), 0, 5);
        grid.add(new Label(details.getCheckOutDate().toString()), 1, 5);
        grid.add(new Label("Deposit Amount:"), 0, 6);
        grid.add(new Label(String.format("PHP%.2f", details.getDepositAmount())), 1, 6);

        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #F4F7FA");

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
}
