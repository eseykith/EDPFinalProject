// Keep all existing imports
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class AdminDashboard extends Stage{
    private HotelController controller;
    private TableView<Room> roomTable;
    private TableView<Guest> guestTable;
    private TableView<Reservation> reservationTable;
    private TableView<Payment> paymentTable;

    private TextField roomSearchField, guestSearchField, reserveSearchField;
    private Button roomSearchButton, guestSearchButton, reserveSearchButton;

    public AdminDashboard(HotelController controller) {
        this.controller = controller;

    }

    public void start(Stage stage) {
        stage.setTitle("Admin Dashboard");

        VBox sideMenu = new VBox(20);
            sideMenu.setPadding(new Insets(20));
            sideMenu.setStyle("-fx-background-color: #2E3A59;");
            sideMenu.setPrefWidth(200);

        BorderPane root = new BorderPane();
            root.setLeft(sideMenu);

        Label services = new Label("Services");
            services.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            services.setStyle("-fx-text-fill: #FFFFFF;");

        Button manageRoom = new Button("Manage Rooms");
        Button manageGuest = new Button("Manage Guest");
        Button managePayment = new Button("Manage Payment");
        Button manageReservation = new Button("Manage Reservation");

        for (Button button : new Button[]{manageRoom, manageGuest, managePayment, manageReservation}) {
            button.setPrefHeight(150);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #6A9FF6; -fx-text-fill: white; -fx-font-size: 16px;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white;"));
        }
        Button logoutButton = new Button("Logout");
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
                    new MainGUI(controller).start(new Stage()); // Open main GUI
                    stage.close();
                }

            });
        });

        sideMenu.getChildren().addAll(services, manageRoom, manageGuest, managePayment, manageReservation, logoutButton);

        VBox centerContent = new VBox(10);
            centerContent.setPadding(new Insets(20));
            centerContent.setAlignment(Pos.TOP_CENTER);

        VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setPadding(new Insets(10));

        // For Managing Rooms
        TextField roomID = new TextField();
            roomID.setPromptText("Room ID");
            roomID.setPrefWidth(500);
        TextField roomType = new TextField();
            roomType.setPromptText("Room Type");
        TextField roomStatus = new TextField();
            roomStatus.setPromptText("Room Status");
        TextField rate = new TextField();
            rate.setPromptText("Rate");
        TextField occupancy = new TextField();
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

        Button confirm = new Button("Confirm");
        Button updateRoom = new Button("UPDATE");
        Button deleteRoom = new Button("DELETE");
        Button createRoom = new Button("CREATE");
            createRoom.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");
            updateRoom.setStyle("-fx-background-color: #f0ad4e; -fx-text-fill: white; -fx-font-weight: bold;");
            deleteRoom.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");
            confirm.setStyle("-fx-background-color: #1E5AA8; -fx-text-fill: white; -fx-font-weight: bold;");


        VBox formContainer = new VBox(10, roomForm, confirm);
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


        //Event Handling
        createRoom.setOnAction(ev -> {
            formContainer.setVisible(true);
            confirm.setVisible(true);
            roomID.setDisable(false);
            roomID.clear(); roomType.clear(); roomStatus.clear(); rate.clear(); occupancy.clear();
        });

        confirm.setOnAction(event -> {
            try {
                if (roomID.getText().isEmpty() || roomStatus.getText().isEmpty() || roomType.getText().isEmpty()
                        || rate.getText().isEmpty() || occupancy.getText().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please fill in all fields.", ButtonType.OK).showAndWait();
                    return;
                }

                double roomRate = Double.parseDouble(rate.getText());
                int roomOccupancy = Integer.parseInt(occupancy.getText());

                Room room = new Room(roomID.getText(), roomStatus.getText(), roomType.getText(), roomRate, roomOccupancy);
                controller.addRoom(room);

                roomID.clear(); roomType.clear(); roomStatus.clear(); rate.clear(); occupancy.clear();
                formContainer.setVisible(false);
                confirm.setVisible(false);
                manageRoom.fire(); // Refresh table
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
            confirm.setVisible(false); // hide create confirm

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
                    manageRoom.fire(); // Refresh table
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
                    manageRoom.fire(); // Refresh table
                }
            });
        });



        manageRoom.setOnAction(e -> {
            formContainer.setVisible(false);
            root.setRight(null);
            Label header = new Label("Check Available Room");
                header.setStyle("-fx-text-fill: #1E5AA8;");
                header.setFont(Font.font("Arial", FontWeight.BOLD, 24));

            // Initialize search field and button
            roomSearchField = new TextField();
            roomSearchField.setPromptText("Search Room...");
            roomSearchButton = new Button("Search");
            roomSearchButton.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");

            // Set up search button action
            roomSearchButton.setOnAction(event -> searchRooms());

            HBox searchBox = new HBox(10, roomSearchField, roomSearchButton);
                searchBox.setAlignment(Pos.CENTER);
                searchBox.setPadding(new Insets(10));
            tableBox.getChildren().clear();
            roomTable = controller.loadRooms();

            VBox tableAndButton = new VBox(10, searchBox, roomTable); // Include search box here
            HBox buttons = new HBox(10, createRoom, updateRoom, deleteRoom);
                buttons.setAlignment(Pos.CENTER);
                buttons.setStyle("-fx-padding: 20px;");
                tableAndButton.setAlignment(Pos.CENTER);
                tableAndButton.setPadding(new Insets(10));

            tableBox.getChildren().addAll(tableAndButton, buttons, formContainer);
            centerContent.getChildren().setAll(header, tableBox);
        });



        // For Managing Guest
        manageGuest.setOnAction(event -> {
            formContainer.setVisible(false);
            root.setRight(null);

            Label header = new Label("Guest");
                header.setStyle("-fx-text-fill: #1E5AA8;");
                header.setFont(Font.font("Arial", FontWeight.BOLD, 24));

            guestSearchField = new TextField();
            guestSearchField.setPromptText("Search Guest...");
            guestSearchButton = new Button("Search");
            guestSearchButton.setStyle("-fx-background-color: #4C8BF5; -fx-text-fill: white; -fx-font-weight: bold;");


            // Set up search button action
            guestSearchButton.setOnAction(e -> searchGuest());

            HBox guestSearchBox = new HBox(10, guestSearchField, guestSearchButton);
                guestSearchBox.setAlignment(Pos.CENTER);
                guestSearchBox.setPadding(new Insets(10));

            tableBox.getChildren().clear();
            guestTable = controller.loadGuest();

            VBox tableAndButton = new VBox(10, guestSearchBox, guestTable);
            HBox buttons = new HBox(); // Optional
                buttons.setAlignment(Pos.CENTER);
                buttons.setStyle("-fx-padding: 20px;");
                tableAndButton.setAlignment(Pos.CENTER);
                tableAndButton.setPadding(new Insets(10));

            tableBox.getChildren().addAll(tableAndButton, buttons, formContainer);
            centerContent.getChildren().setAll(header, tableBox);
        });

        managePayment.setOnAction(event -> {
            formContainer.setVisible(false);
            root.setRight(null);
            Label header = new Label("Payment");
                header.setStyle("-fx-text-fill: #1E5AA8;");
                header.setFont(Font.font("Arial", FontWeight.BOLD, 24));

            tableBox.getChildren().clear();
            paymentTable = controller.loadPayment();

            VBox tableAndButton = new VBox(10, paymentTable);
            HBox buttons = new HBox(); // Optional
                buttons.setAlignment(Pos.CENTER);
                buttons.setStyle("-fx-padding: 20px;");
                tableAndButton.setAlignment(Pos.CENTER);
                tableAndButton.setPadding(new Insets(10));

            tableBox.getChildren().addAll(tableAndButton, buttons, formContainer);
            centerContent.getChildren().setAll(header, tableBox);
        });

        manageReservation.setOnAction(event -> {
            formContainer.setVisible(false);
            root.setRight(null);
            Label header = new Label("Reservation");
                header.setStyle("-fx-text-fill: #1E5AA8;");
                header.setFont(Font.font("Arial", FontWeight.BOLD, 24));

            tableBox.getChildren().clear();
            reservationTable = controller.loadReservation();

            // Add listener for selection to show reservation details
            reservationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    showReservationDetails(newSelection);
                }
            });

            VBox tableAndButton = new VBox(10, reservationTable);
            HBox buttons = new HBox(); // Optional buttons area
                buttons.setAlignment(Pos.CENTER);
                buttons.setStyle("-fx-padding: 20px;");
                tableAndButton.setAlignment(Pos.CENTER);
                tableAndButton.setPadding(new Insets(10));

            tableBox.getChildren().addAll(tableAndButton, buttons, formContainer);
            centerContent.getChildren().setAll(header, tableBox);
        });

        Image image = new Image("/hotelimg.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(750);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

        Label titleLabel = new Label("AI HOTEL RESERVATION SYSTEM");
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            titleLabel.setStyle("-fx-text-fill: #1E5AA8;");
            titleLabel.setWrapText(true);
            titleLabel.setAlignment(Pos.CENTER);
            titleLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        centerContent.getChildren().addAll(titleLabel, imageView);
        centerContent.setStyle("-fx-background-color: #F4F7FA;");
        centerContent.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(centerContent);
            scrollPane.setFitToWidth(true);
            scrollPane.setPannable(true);
            root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
            stage.show();
    }

    // Method to search rooms based on the input in the search field
    private void searchRooms() {
        String searchText = roomSearchField.getText().toLowerCase().trim();
        if (roomTable != null) {
            // Fetch filtered rooms from the controller
            try {
                ObservableList<Room> filteredRooms = controller.searchRooms(searchText);
                roomTable.setItems(filteredRooms);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to search rooms: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    private void searchGuest() {
        String searchText = guestSearchField.getText().toLowerCase().trim();
        if (guestTable != null) {
            // Fetch filtered rooms from the controller
            try {
                ObservableList<Guest> filteredGuest = controller.searchGuest(searchText);
                guestTable.setItems(filteredGuest);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to search guests: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    private void showReservationDetails(Reservation reservation) {
        // Fetch details from controller
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
        dialog.getDialogPane().setPrefSize(400, 200);
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
