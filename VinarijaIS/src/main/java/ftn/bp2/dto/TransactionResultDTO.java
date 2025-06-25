package ftn.bp2.dto;

public class TransactionResultDTO {
    private boolean success;
    private String message;
    private String error;
    private Integer customerId;
    private Integer orderId;

    public TransactionResultDTO() {}

    public TransactionResultDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public TransactionResultDTO(boolean success, String message, String error) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

    public TransactionResultDTO(boolean success, String message, Integer customerId, Integer orderId) {
        this.success = success;
        this.message = message;
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    @Override
    public String toString() {
        return "TransactionResultDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", customerId=" + customerId +
                ", orderId=" + orderId +
                '}';
    }
} 