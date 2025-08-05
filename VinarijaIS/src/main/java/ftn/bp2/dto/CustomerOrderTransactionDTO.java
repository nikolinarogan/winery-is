package ftn.bp2.dto;

import java.time.LocalDate;
import java.util.List;

public class CustomerOrderTransactionDTO {
    private String email;
    private String phoneNumber;
    private String paymentMethod;
    private List<Integer> bottleSerialNumbers;
    private LocalDate orderDate;

    public CustomerOrderTransactionDTO() {}

    public CustomerOrderTransactionDTO(String email, String phoneNumber, String paymentMethod, 
                                     List<Integer> bottleSerialNumbers) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
        this.bottleSerialNumbers = bottleSerialNumbers;
        this.orderDate = LocalDate.now();
    }

    public CustomerOrderTransactionDTO(String email, String phoneNumber, String paymentMethod, 
                                     List<Integer> bottleSerialNumbers, LocalDate orderDate) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
        this.bottleSerialNumbers = bottleSerialNumbers;
        this.orderDate = orderDate;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public List<Integer> getBottleSerialNumbers() { return bottleSerialNumbers; }
    public void setBottleSerialNumbers(List<Integer> bottleSerialNumbers) { this.bottleSerialNumbers = bottleSerialNumbers; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
        return "CustomerOrderTransactionDTO{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", bottleSerialNumbers=" + bottleSerialNumbers +
                ", orderDate=" + orderDate +
                '}';
    }
} 