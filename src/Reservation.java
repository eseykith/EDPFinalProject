public class Reservation {
    private String reservationID;
    private String guestID;
    private String paymentID;
    private String roomID;


    // Constructor
    public Reservation(String reservationID,String guestID, String paymentID, String roomID) {
        this.reservationID = reservationID;
        this.guestID = guestID;
        this.paymentID = paymentID;
        this.roomID = roomID;

    }

    // Getters and Setters
    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

}