import java.util.Date;

public class Payment {
    private String paymentID;
    private double amountPaid;
    private Date paymentDate;
    private String paymentMethod;
    private String paymentStatus;

    // Constructor
    public Payment(String paymentID, double amountPaid, Date paymentDate, String paymentMethod, String paymentStatus) {
        this.paymentID = paymentID;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}