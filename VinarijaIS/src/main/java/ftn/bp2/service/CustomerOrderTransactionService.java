package ftn.bp2.service;

import ftn.bp2.dao.CustomerOrderTransactionDAO;
import ftn.bp2.dto.CustomerOrderTransactionDTO;
import ftn.bp2.dto.TransactionResultDTO;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class CustomerOrderTransactionService {
    private final CustomerOrderTransactionDAO customerOrderTransactionDAO;

    public CustomerOrderTransactionService() {
        this.customerOrderTransactionDAO = new CustomerOrderTransactionDAO();
    }

    public TransactionResultDTO executeCustomerOrderTransaction(CustomerOrderTransactionDTO transaction) throws SQLException {
        // Validate transaction data
        TransactionResultDTO validationResult = validateTransaction(transaction);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        // Check if wine exists
        if (!customerOrderTransactionDAO.validateWineExists(transaction.getWineId())) {
            return new TransactionResultDTO(false, "Wine not found", "Wine with ID " + transaction.getWineId() + " does not exist");
        }

        // Check if customer already exists - email must be unique
        boolean customerExists = customerOrderTransactionDAO.validateCustomerEmailExists(transaction.getEmail());
        if (customerExists) {
            return new TransactionResultDTO(false, "Customer already exists", 
                "Customer with email " + transaction.getEmail() + " already exists. Email must be unique.");
        }

        // Execute the transaction
        return customerOrderTransactionDAO.executeCustomerOrderTransaction(transaction);
    }

    public TransactionResultDTO executeCustomerOrderTransaction(String email, String phoneNumber, 
                                                              String paymentMethod, Integer wineId, 
                                                              Float bottleCapacity) throws SQLException {
        CustomerOrderTransactionDTO transaction = new CustomerOrderTransactionDTO(
                email, phoneNumber, paymentMethod, wineId, bottleCapacity);
        return executeCustomerOrderTransaction(transaction);
    }

    private TransactionResultDTO validateTransaction(CustomerOrderTransactionDTO transaction) {
        // Validate email
        if (transaction.getEmail() == null || transaction.getEmail().trim().isEmpty()) {
            return new TransactionResultDTO(false, "Email is required", "Email cannot be empty");
        }

        if (!isValidEmail(transaction.getEmail())) {
            return new TransactionResultDTO(false, "Invalid email format", "Email format is not valid");
        }

        // Validate phone number
        if (transaction.getPhoneNumber() == null || transaction.getPhoneNumber().trim().isEmpty()) {
            return new TransactionResultDTO(false, "Phone number is required", "Phone number cannot be empty");
        }

        if (!isValidPhoneNumber(transaction.getPhoneNumber())) {
            return new TransactionResultDTO(false, "Invalid phone number format", "Phone number format is not valid");
        }

        // Validate payment method
        if (transaction.getPaymentMethod() == null || transaction.getPaymentMethod().trim().isEmpty()) {
            return new TransactionResultDTO(false, "Payment method is required", "Payment method cannot be empty");
        }

        if (!isValidPaymentMethod(transaction.getPaymentMethod())) {
            return new TransactionResultDTO(false, "Invalid payment method", "Payment method must be one of: cash, card, transfer");
        }

        // Validate wine ID
        if (transaction.getWineId() == null || transaction.getWineId() <= 0) {
            return new TransactionResultDTO(false, "Valid wine ID is required", "Wine ID must be a positive number");
        }

        // Validate bottle capacity
        if (transaction.getBottleCapacity() == null || transaction.getBottleCapacity() <= 0) {
            return new TransactionResultDTO(false, "Valid bottle capacity is required", "Bottle capacity must be a positive number");
        }

        if (transaction.getBottleCapacity() > 10.0f) {
            return new TransactionResultDTO(false, "Bottle capacity too large", "Bottle capacity cannot exceed 10 liters");
        }

        return new TransactionResultDTO(true, "Validation successful");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Basic phone number validation - can be customized based on requirements
        String phoneRegex = "^[+]?[0-9\\s\\-\\(\\)]{7,15}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phoneNumber).matches();
    }

    private boolean isValidPaymentMethod(String paymentMethod) {
        String lowerPaymentMethod = paymentMethod.toLowerCase();
        return lowerPaymentMethod.equals("cash") || 
               lowerPaymentMethod.equals("card") || 
               lowerPaymentMethod.equals("transfer");
    }

    public boolean validateWineExists(Integer wineId) throws SQLException {
        if (wineId == null || wineId <= 0) {
            return false;
        }
        return customerOrderTransactionDAO.validateWineExists(wineId);
    }

    public boolean validateCustomerEmailExists(String email) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return customerOrderTransactionDAO.validateCustomerEmailExists(email);
    }

    public Integer getCustomerIdByEmail(String email) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return customerOrderTransactionDAO.getCustomerIdByEmail(email);
    }

    public TransactionResultDTO validateTransactionData(String email, String phoneNumber, 
                                                       String paymentMethod, Integer wineId, 
                                                       Float bottleCapacity) {
        CustomerOrderTransactionDTO transaction = new CustomerOrderTransactionDTO(
                email, phoneNumber, paymentMethod, wineId, bottleCapacity);
        return validateTransaction(transaction);
    }

    public CustomerOrderTransactionDAO getCustomerOrderTransactionDAO() {
        return customerOrderTransactionDAO;
    }
} 