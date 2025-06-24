package ftn.bp2.dto;

import java.time.LocalDate;

public class CustomerOrderTransactionDTO {
    private String email;
    private String phoneNumber;
    private String paymentMethod;
    private Integer wineId;
    private Float bottleCapacity;
    private LocalDate orderDate;

    public CustomerOrderTransactionDTO() {}

    public CustomerOrderTransactionDTO(String email, String phoneNumber, String paymentMethod, 
                                     Integer wineId, Float bottleCapacity) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
        this.wineId = wineId;
        this.bottleCapacity = bottleCapacity;
        this.orderDate = LocalDate.now();
    }

    public CustomerOrderTransactionDTO(String email, String phoneNumber, String paymentMethod, 
                                     Integer wineId, Float bottleCapacity, LocalDate orderDate) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
        this.wineId = wineId;
        this.bottleCapacity = bottleCapacity;
        this.orderDate = orderDate;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Integer getWineId() { return wineId; }
    public void setWineId(Integer wineId) { this.wineId = wineId; }

    public Float getBottleCapacity() { return bottleCapacity; }
    public void setBottleCapacity(Float bottleCapacity) { this.bottleCapacity = bottleCapacity; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
        return "CustomerOrderTransactionDTO{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", wineId=" + wineId +
                ", bottleCapacity=" + bottleCapacity +
                ", orderDate=" + orderDate +
                '}';
    }
} 