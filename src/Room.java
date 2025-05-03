public class Room {
    private String roomID;
    private String roomType;
    private String roomStatus;
    private double rate;
    private int occupancyLimit;


    public Room(String roomID, String status, String roomType, double rate, int occupancyLimit) {
        this.roomID = roomID;
        this.roomStatus = status;
        this.roomType = roomType;
        this.rate = rate;
        this.occupancyLimit = occupancyLimit;
    }

    // Getters and Setters
    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getOccupancyLimit() {
        return occupancyLimit;
    }

    public void setOccupancyLimit(int occupancyLimit) {
        this.occupancyLimit = occupancyLimit;
    }
}