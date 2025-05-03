import java.util.Date;

public class ReservationDetails {
    private String reservationID;
    private double totalAmount;
    private int totalOccupants;
    private Date reservationDate;
    private Date checkInDate;
    private Date checkOutDate;
    private double depositAmount;

    // Constructor
    public ReservationDetails(String reservationID, double totalAmount, int totalOccupants, Date reservationDate, Date checkInDate, Date checkOutDate, double depositAmount) {
        this.reservationID = reservationID;
        this.totalAmount = totalAmount;
        this.totalOccupants = totalOccupants;
        this.reservationDate = reservationDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.depositAmount = depositAmount;
    }

    // Getters and Setters
    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public double getTotalAmount(){
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }

    public int getTotalOccupants() {
        return totalOccupants;
    }

    public void setTotalOccupants(int totalOccupants) {
        this.totalOccupants = totalOccupants;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }
}