public class Guest {
    private String guestID;
    private String firstName;
    private String lastName;
    private String city;
    private String province;
    private String contactNo;
    private String email;

    // Constructor
    public Guest(String guestID,String firstName, String lastName, String city, String province, String contactNo, String email) {
        this.guestID = guestID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.province = province;
        this.contactNo = contactNo;
        this.email = email;
    }

    // Getters and Setters
    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}