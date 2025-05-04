import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GuestDashboard {
    private HotelController controller;
    private Stage primaryStage;
    private Guest guest;

    public GuestDashboard(HotelController controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
    }
    public GuestDashboard(Guest guest){
        this.guest = guest;
    }

    // Show only available rooms with clickable rows to open reservation form
    public void showAvailableRooms() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F4F7FA;");

        Label titleLabel = new Label("Available Rooms");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);

        TableView<Room> roomTable = controller.loadAvailableRooms();
        roomTable.setPrefHeight(450);

        root.setCenter(roomTable);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            try {
                new MainGUI(controller).start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Label instrLabel = new Label("Double click a room to proceed to reservation form");
        instrLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
        HBox bottomBox = new HBox(10, backButton, instrLabel);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setAlignment(Pos.CENTER_LEFT);

        root.setBottom(bottomBox);

        // On double-click on a room row, open GuestForm with selected Room
        roomTable.setRowFactory(tv -> {
            TableRow<Room> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Room selectedRoom = row.getItem();
                    showGuestForm(selectedRoom);
                }
            });
            return row;
        });

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Available Rooms");
        primaryStage.show();
    }

    private void showGuestForm(Room selectedRoom) {
        GuestForm guestForm = new GuestForm(controller, primaryStage, selectedRoom);
        guestForm.getGuestScene();
    }
}
