    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.scene.control.Alert;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.cell.PropertyValueFactory;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.Date;

    public class HotelController {
        // Method to add a guest
        public void addGuest(Guest guest) {
            String sql = "INSERT INTO Guest (GuestID, FirstName, LastName, City, Province, ContactNo, Email) VALUES (?,?, ?, ?, ?, ?, ?)";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, guest.getGuestID());
                preparedStatement.setString(2, guest.getFirstName());
                preparedStatement.setString(3, guest.getLastName());
                preparedStatement.setString(4, guest.getCity());
                preparedStatement.setString(5, guest.getProvince());
                preparedStatement.setString(6, guest.getContactNo());
                preparedStatement.setString(7, guest.getEmail());
                preparedStatement.executeUpdate();
                showAlert("Success", "Guest added successfully!");


            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add guest: " + e.getMessage());
            }
        }

        // Method to update a guest
        public void updateGuest(Guest guest) {
            String sql = "UPDATE Guest SET FirstName = ?, LastName = ?, City = ?, Province = ?, ContactNo = ?, Email = ? WHERE GuestID = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, guest.getFirstName());
                preparedStatement.setString(2, guest.getLastName());
                preparedStatement.setString(3, guest.getCity());
                preparedStatement.setString(4, guest.getProvince());
                preparedStatement.setString(5, guest.getContactNo());
                preparedStatement.setString(6, guest.getEmail());
                preparedStatement.setString(7, guest.getGuestID()); // Assuming Guest has a getGuestID() method
                preparedStatement.executeUpdate();
                showAlert("Success", "Guest updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to update guest: " + e.getMessage());
            }
        }

        // Method to delete a guest
        public void deleteGuest(String guestID) {
            String sql = "DELETE FROM Guest WHERE GuestID = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, guestID);
                preparedStatement.executeUpdate();
                showAlert("Success", "Guest deleted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete guest: " + e.getMessage());
            }
        }

        // Method to add a room
        public void addRoom(Room room) {
            String sql = "INSERT INTO Room (RoomID, RoomStatus,RoomType, OccupancyLimit,Rate) VALUES (?, ?, ?, ?,?)";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, room.getRoomID());
                preparedStatement.setString(2, room.getRoomStatus());
                preparedStatement.setString(3, room.getRoomType());
                preparedStatement.setInt(4, room.getOccupancyLimit());
                preparedStatement.setDouble(5, room.getRate());

                preparedStatement.executeUpdate();
                showAlert("Success", "Room added successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add room: " + e.getMessage());
            }
        }

        public void updateRoom(Room room) {
            String sql = "UPDATE Room SET RoomStatus = ?, RoomType = ?, OccupancyLimit = ?, Rate = ? WHERE RoomID = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, room.getRoomStatus());
                preparedStatement.setString(2, room.getRoomType());
                preparedStatement.setInt(3, room.getOccupancyLimit());
                preparedStatement.setDouble(4, room.getRate());
                preparedStatement.setString(5, room.getRoomID());
                preparedStatement.executeUpdate();
                showAlert("Success", "Room updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to update room: " + e.getMessage());
            }
        }

        // Method to delete a room
        public void deleteRoom(String roomID) {
            String sql = "DELETE FROM Room WHERE RoomID = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, roomID);
                preparedStatement.executeUpdate();
                showAlert("Success", "Room deleted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete room: " + e.getMessage());
            }
        }


        // Method to add a payment
        public void addPayment(Payment payment) {
            String sql = "INSERT INTO Payment (AmountPaid, PaymentDate, PaymentMethod, PaymentStatus) VALUES (?, ?, ?, ?)";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, payment.getAmountPaid());
                preparedStatement.setDate(2, new java.sql.Date(payment.getPaymentDate().getTime()));
                preparedStatement.setString(3, payment.getPaymentMethod());
                preparedStatement.setString(4, payment.getPaymentStatus());
                preparedStatement.executeUpdate();
                showAlert("Success", "Payment added successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add payment: " + e.getMessage());
            }
        }

        // Method to add a reservation
        public void addReservation(Reservation reservation) {
            String sql = "INSERT INTO Reservation (ReservationID, GuestID, PaymentID, RoomID) VALUES (?, ?, ?)";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, reservation.getReservationID());
                preparedStatement.setString(2, reservation.getGuestID());
                preparedStatement.setString(3, reservation.getPaymentID());
                preparedStatement.setString(4, reservation.getRoomID());
                preparedStatement.executeUpdate();
                showAlert("Success", "Reservation added successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add reservation: " + e.getMessage());
            }
        }

        public void addReservationDetails(ReservationDetails details){
            String sql = "INSERT INTO Reservation_Details(ReservationID, TotalAmount, TotalOccupants,ReservationDate, CheckInDate ,CheckOutDate, DepositAmount) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, details.getReservationID());
                preparedStatement.setDouble(2, details.getTotalAmount());
                preparedStatement.setInt(3, details.getTotalOccupants());
                preparedStatement.setDate(4,new java.sql.Date(details.getReservationDate().getTime()));
                preparedStatement.setDate(5, new java.sql.Date(details.getCheckInDate().getTime()));
                preparedStatement.setDate(6, new java.sql.Date(details.getCheckOutDate().getTime()));
                preparedStatement.setDouble(7, details.getDepositAmount());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add reservation details: " + e.getMessage());
            }
        }

        public TableView<Room> loadRooms() {
            String sql = "SELECT RoomID, RoomStatus, RoomType, Rate, OccupancyLimit FROM Room";

            ObservableList<Room> roomList = FXCollections.observableArrayList();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String roomID = resultSet.getString("RoomID");
                    String roomType = resultSet.getString("RoomType");
                    String roomStatus = resultSet.getString("RoomStatus");
                    double rate = resultSet.getDouble("Rate");
                    int occupancyLimit = resultSet.getInt("OccupancyLimit");

                    roomList.add(new Room(roomID, roomStatus, roomType, rate, occupancyLimit));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to retrieve available rooms: " + e.getMessage());
            }

            // Build the TableView

            TableView<Room> tableView = new TableView<>();

            TableColumn<Room, String> roomIDCol = new TableColumn<>("Room ID");
            roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));

            TableColumn<Room, String> roomTypeCol = new TableColumn<>("Room Type");
            roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

            TableColumn<Room, Double> rateCol = new TableColumn<>("Rate");
            rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));

            TableColumn<Room, Integer> occupancyCol = new TableColumn<>("Occupancy Limit");
            occupancyCol.setCellValueFactory(new PropertyValueFactory<>("occupancyLimit"));

            TableColumn<Room, String> statusCol = new TableColumn<>("Room Status");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));

            tableView.setPrefWidth(600);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // Equal width: bind each column to 1/5th of table width
            int numCols = 5;
            roomIDCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            roomTypeCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            rateCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            occupancyCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            statusCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));

            tableView.getColumns().addAll(roomIDCol, roomTypeCol, rateCol, occupancyCol, statusCol);
            tableView.setItems(roomList);
            tableView.setPrefHeight(400);


            return tableView;
        }


        public TableView<Room> loadAvailableRooms() {
            String sql = "SELECT*FROM Room WHERE RoomStatus = 'Available'";

            ObservableList<Room> roomList = FXCollections.observableArrayList();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String roomID = resultSet.getString("RoomID");
                    String roomType = resultSet.getString("RoomType");
                    String roomStatus = resultSet.getString("RoomStatus");
                    double rate = resultSet.getDouble("Rate");
                    int occupancyLimit = resultSet.getInt("OccupancyLimit");

                    roomList.add(new Room(roomID, roomStatus, roomType, rate, occupancyLimit));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to retrieve available rooms: " + e.getMessage());
            }

            // Build the TableView
            TableView<Room> tableView = new TableView<>();

            TableColumn<Room, String> roomIDCol = new TableColumn<>("Room ID");
            roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));

            TableColumn<Room, String> roomTypeCol = new TableColumn<>("Room Type");
            roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

            TableColumn<Room, Double> rateCol = new TableColumn<>("Rate");
            rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));

            TableColumn<Room, Integer> occupancyCol = new TableColumn<>("Occupancy Limit");
            occupancyCol.setCellValueFactory(new PropertyValueFactory<>("occupancyLimit"));

            TableColumn<Room, String> statusCol = new TableColumn<>("Room Status");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));

            tableView.setPrefWidth(600);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // Equal width: bind each column to 1/5th of table width
            int numCols = 5;
            roomIDCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            roomTypeCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            rateCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            occupancyCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            statusCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));

            tableView.getColumns().addAll(roomIDCol, roomTypeCol, rateCol, occupancyCol, statusCol);
            tableView.setItems(roomList);
            tableView.setPrefHeight(400);

            return tableView;
        }


        public TableView<Guest> loadGuest() {
            String sql = "SELECT * FROM Guest";

            ObservableList<Guest> guestList = FXCollections.observableArrayList();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String guestId = resultSet.getString("GuestID");
                    String first = resultSet.getString("FirstName");
                    String last = resultSet.getString("LastName");
                    String city = resultSet.getString("City");
                    String province = resultSet.getString("Province");
                    String contact = resultSet.getString("ContactNo");
                    String email = resultSet.getString("Email");

                    guestList.add(new Guest(guestId,first,last,city,province,contact,email));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to retrieve guest: " + e.getMessage());
            }

            // Build the TableView
            TableView<Guest> tableView = new TableView<>();

            TableColumn<Guest, String> guestIdCol = new TableColumn<>("Guest ID");
            guestIdCol.setCellValueFactory(new PropertyValueFactory<>("guestID"));

            TableColumn<Guest, String> firstCol = new TableColumn<>("First Name");
            firstCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

            TableColumn<Guest, String> lastCol = new TableColumn<>("Last Name");
            lastCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

            TableColumn<Guest, String> cityCol = new TableColumn<>("City");
            cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));

            TableColumn<Guest, String> provinceCol = new TableColumn<>("Province");
            provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));

            TableColumn<Guest, String> contactCol = new TableColumn<>("Contact No");
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactNo"));

            TableColumn<Guest, String> emailCol = new TableColumn<>("Email");
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

            tableView.setPrefWidth(700);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // Equal width: bind each column to 1/7th of table width (since there are 7 columns)
            int numCols = 7;
            guestIdCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            firstCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            lastCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            cityCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            provinceCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            contactCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            emailCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));

            tableView.getColumns().addAll(guestIdCol, firstCol, lastCol, cityCol, provinceCol, contactCol, emailCol);
            tableView.setItems(guestList);

            tableView.setPrefHeight(400);

            return tableView;
        }


        public TableView<Reservation> loadReservation() {
            String sql = "SELECT * FROM Reservation";

            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String reservationID = resultSet.getString("ReservationID");
                    String guestID = resultSet.getString("GuestID");
                    String roomID = resultSet.getString("RoomID");
                    String paymentID = resultSet.getString("PaymentiD");

                    reservationList.add(new Reservation(reservationID, guestID, paymentID, roomID));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to retrieve reservation: " + e.getMessage());
            }

            // Build the TableView
            TableView<Reservation> tableView = new TableView<>();

            TableColumn<Reservation, String> reservationIDCol = new TableColumn<>("Reservation ID");
            reservationIDCol.setCellValueFactory(new PropertyValueFactory<>("reservationID"));

            TableColumn<Reservation, String> guestIDCol = new TableColumn<>("Guest ID");
            guestIDCol.setCellValueFactory(new PropertyValueFactory<>("guestID"));

            TableColumn<Reservation, String> roomIDCol = new TableColumn<>("Room ID");
            roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));

            TableColumn<Reservation, String> paymentIDCol = new TableColumn<>("Payment ID");
            paymentIDCol.setCellValueFactory(new PropertyValueFactory<>("paymentID"));

            // Set TableView width and column resizing policy
            tableView.setPrefWidth(700);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // Equal width: bind each column to 1/4th of table width (since there are 4 columns)
            int numCols = 4;
            reservationIDCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            guestIDCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            roomIDCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
            paymentIDCol.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));

            tableView.getColumns().addAll(reservationIDCol, guestIDCol, roomIDCol, paymentIDCol);
            tableView.setItems(reservationList);

            tableView.setPrefHeight(400);

            return tableView;
        }


        public TableView<Payment> loadPayment() {
            String sql = "SELECT * FROM Payment"; // Adjust your SQL query as necessary.

            ObservableList<Payment> paymentList = FXCollections.observableArrayList();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String paymentID = resultSet.getString("PaymentID");
                    double amountPaid = resultSet.getDouble("AmountPaid");
                    Date paymentDate = resultSet.getDate("PaymentDate");
                    String paymentMethod = resultSet.getString("PaymentMethod");
                    String paymentStatus = resultSet.getString("PaymentStatus");

                    paymentList.add(new Payment(paymentID, amountPaid, paymentDate, paymentMethod, paymentStatus));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to retrieve payment: " + e.getMessage());
            }

            // Build the TableView
            TableView<Payment> tableView = new TableView<>();

            TableColumn<Payment, String> paymentIDCol = new TableColumn<>("Payment ID");
            paymentIDCol.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
            paymentIDCol.setPrefWidth(100); // Set preferred width

            TableColumn<Payment, Double> amountPaidCol = new TableColumn<>("Amount Paid");
            amountPaidCol.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
            amountPaidCol.setPrefWidth(150); // Set preferred width

            TableColumn<Payment, Date> paymentDateCol = new TableColumn<>("Payment Date");
            paymentDateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            paymentDateCol.setPrefWidth(150); // Set preferred width

            TableColumn<Payment, String> paymentMethodCol = new TableColumn<>("Payment Method");
            paymentMethodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
            paymentMethodCol.setPrefWidth(150); // Set preferred width

            TableColumn<Payment, String> paymentStatusCol = new TableColumn<>("Payment Status");
            paymentStatusCol.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
            paymentStatusCol.setPrefWidth(150); // Set preferred width

            // Add all columns to the table
            tableView.getColumns().addAll(paymentIDCol, amountPaidCol, paymentDateCol, paymentMethodCol, paymentStatusCol);
            tableView.setItems(paymentList);

            tableView.setPrefHeight(400);
            tableView.setPrefWidth(750);

            return tableView;
        }

        public ReservationDetails getReservationDetails(String reservationID) {
            String sql = "SELECT * FROM Reservation_Details WHERE ReservationID = ?";
            ReservationDetails details = null;

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, reservationID);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String id = resultSet.getString("ReservationID");
                    double totalAmount = resultSet.getDouble("TotalAmount");
                    int totalOccupants = resultSet.getInt("TotalOccupants");
                    Date reservationDate = resultSet.getDate("ReservationDate");
                    Date checkInDate = resultSet.getDate("CheckInDate");
                    Date checkOutDate = resultSet.getDate("CheckOutDate");
                    double depositAmount = resultSet.getDouble("DepositAmount");

                    details = new ReservationDetails(id, totalAmount, totalOccupants, reservationDate, checkInDate, checkOutDate, depositAmount);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to retrieve reservation details: " + e.getMessage());
            }

            return details;
        }

        public ObservableList<Room> searchRooms(String searchText) {
            String sql = "SELECT * FROM Room WHERE RoomID LIKE ? OR RoomType LIKE ? OR RoomStatus LIKE ?";
            ObservableList<Room> roomList = FXCollections.observableArrayList();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + searchText + "%"); // Search by RoomID
                preparedStatement.setString(2, "%" + searchText + "%"); // Search by RoomType
                preparedStatement.setString(3, "%" + searchText + "%");

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String roomID = resultSet.getString("RoomID");
                    String roomType = resultSet.getString("RoomType");
                    String roomStatus = resultSet.getString("RoomStatus");
                    double rate = resultSet.getDouble("Rate");
                    int occupancyLimit = resultSet.getInt("OccupancyLimit");

                    roomList.add(new Room(roomID, roomStatus, roomType, rate, occupancyLimit));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to search rooms: " + e.getMessage());
            }

            return roomList;
        }



        // Method to show alerts
        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        public ObservableList<Guest> searchGuest(String searchText) {
            String sql = "SELECT * FROM GUEST WHERE GuestID LIKE ? OR FirstName LIKE ? OR LastName LIKE ?";
            ObservableList<Guest> guestList = FXCollections.observableArrayList();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + searchText + "%"); // Search by RoomID
                preparedStatement.setString(2, "%" + searchText + "%"); // Search by RoomType
                preparedStatement.setString(3, "%" + searchText + "%");

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String guestId = resultSet.getString("GuestID");
                    String first = resultSet.getString("FirstName");
                    String last = resultSet.getString("LastName");
                    String city = resultSet.getString("City");
                    String province = resultSet.getString("Province");
                    String contact = resultSet.getString("ContactNo");
                    String email = resultSet.getString("Email");

                    guestList.add(new Guest(guestId, first, last, city, province,contact,email));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to search guests: " + e.getMessage());
            }

            return guestList;
        }

        public String getNextGuestID() {
            String prefix = "G";
            String sql = "SELECT MAX(GuestID) AS MaxID FROM Guest";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String maxID = rs.getString("MaxID");
                    if (maxID != null) {
                        int num = Integer.parseInt(maxID.substring(1)); // skip prefix
                        num++;
                        return prefix + num;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // If no record found, start from G100
            return prefix + "100";
        }

        // Method to get next ReservationID in format R100, R101, ...
        public String getNextReservationID() {
            String prefix = "R";
            String sql = "SELECT MAX(ReservationID) AS MaxID FROM Reservation";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String maxID = rs.getString("MaxID");
                    if (maxID != null) {
                        int num = Integer.parseInt(maxID.substring(1)); // skip prefix
                        num++;
                        return prefix + num;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // If no record found, start from R100
            return prefix + "100";
        }

        // Method to get next PaymentID in format P100, P101, ...
        public String getNextPaymentID() {
            String prefix = "P";
            String sql = "SELECT MAX(PaymentID) AS MaxID FROM Payment";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String maxID = rs.getString("MaxID");
                    if (maxID != null) {
                        int num = Integer.parseInt(maxID.substring(1)); // skip prefix
                        num++;
                        return prefix + num;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // If no record found, start from P100
            return prefix + "100";
        }

    }