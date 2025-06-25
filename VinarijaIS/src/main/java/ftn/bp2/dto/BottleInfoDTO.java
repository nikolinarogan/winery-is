package ftn.bp2.dto;

public class BottleInfoDTO {
    private Integer serialNumber;
    private Float capacity;
    private Integer wineId;
    private String wineName;
    private Integer orderId; // null if not assigned to any order

    public BottleInfoDTO() {}

    public BottleInfoDTO(Integer serialNumber, Float capacity, Integer wineId, String wineName, Integer orderId) {
        this.serialNumber = serialNumber;
        this.capacity = capacity;
        this.wineId = wineId;
        this.wineName = wineName;
        this.orderId = orderId;
    }

    public Integer getSerialNumber() { return serialNumber; }
    public void setSerialNumber(Integer serialNumber) { this.serialNumber = serialNumber; }

    public Float getCapacity() { return capacity; }
    public void setCapacity(Float capacity) { this.capacity = capacity; }

    public Integer getWineId() { return wineId; }
    public void setWineId(Integer wineId) { this.wineId = wineId; }

    public String getWineName() { return wineName; }
    public void setWineName(String wineName) { this.wineName = wineName; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public boolean isAvailable() {
        return orderId == null;
    }

    @Override
    public String toString() {
        return "BottleInfoDTO{" +
                "serialNumber=" + serialNumber +
                ", capacity=" + capacity +
                ", wineId=" + wineId +
                ", wineName='" + wineName + '\'' +
                ", orderId=" + orderId +
                ", available=" + isAvailable() +
                '}';
    }
} 