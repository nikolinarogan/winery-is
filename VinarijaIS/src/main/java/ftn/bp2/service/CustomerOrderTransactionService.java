package ftn.bp2.service;

import ftn.bp2.dao.CustomerOrderTransactionDAO;
import ftn.bp2.dto.BottleInfoDTO;
import ftn.bp2.dto.CustomerOrderTransactionDTO;
import ftn.bp2.dto.TransactionResultDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomerOrderTransactionService {
    private final CustomerOrderTransactionDAO customerOrderTransactionDAO;

    public CustomerOrderTransactionService() {
        this.customerOrderTransactionDAO = new CustomerOrderTransactionDAO();
    }

    public TransactionResultDTO executeCustomerOrderTransaction(CustomerOrderTransactionDTO transaction) throws SQLException {
        TransactionResultDTO validationResult = validateTransaction(transaction);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        // Validacija za boce
        for (Integer serialNumber : transaction.getBottleSerialNumbers()) {
            if (!customerOrderTransactionDAO.validateBottleExists(serialNumber)) {
                return new TransactionResultDTO(false, "Bottle not found", 
                    "Bottle with serial number " + serialNumber + " does not exist");
            }
            
            if (!customerOrderTransactionDAO.validateBottleAvailable(serialNumber)) {
                return new TransactionResultDTO(false, "Bottle not available", 
                    "Bottle with serial number " + serialNumber + " is already sold or not available");
            }
        }

        boolean customerExists = customerOrderTransactionDAO.validateCustomerEmailExists(transaction.getEmail());
        if (customerExists) {
            return new TransactionResultDTO(false, "Customer already exists", 
                "Customer with email " + transaction.getEmail() + " already exists. Email must be unique.");
        }

        return customerOrderTransactionDAO.executeCustomerOrderTransaction(transaction);
    }

    public TransactionResultDTO executeCustomerOrderTransaction(String email, String phoneNumber, 
                                                              String paymentMethod, List<Integer> bottleSerialNumbers) throws SQLException {
        CustomerOrderTransactionDTO transaction = new CustomerOrderTransactionDTO(email, phoneNumber, paymentMethod, bottleSerialNumbers);
        return executeCustomerOrderTransaction(transaction);
    }

    public List<BottleInfoDTO> getAvailableBottles() throws SQLException {
        List<Map<String, Object>> rawData = customerOrderTransactionDAO.getAvailableBottlesData();
        return rawData.stream()
                .map(this::mapToBottleInfoDTO)
                .collect(Collectors.toList());
    }

    public List<BottleInfoDTO> getBottlesByWineId(Integer wineId) throws SQLException {
        if (wineId == null || wineId <= 0) {
            throw new SQLException("Invalid wine ID");
        }
        
        if (!customerOrderTransactionDAO.validateWineExists(wineId)) {
            throw new SQLException("Wine with ID " + wineId + " does not exist");
        }
        
        List<Map<String, Object>> rawData = customerOrderTransactionDAO.getBottlesByWineIdData(wineId);
        return rawData.stream()
                .map(this::mapToBottleInfoDTO)
                .collect(Collectors.toList());
    }

    private BottleInfoDTO mapToBottleInfoDTO(Map<String, Object> row) {
        return new BottleInfoDTO(
                (Integer) row.get("SerBr"),
                (Float) row.get("KapBoc"),
                (Integer) row.get("Vino_IdVina"),
                (String) row.get("NazVina"),
                (Integer) row.get("Narudzba_IdNar")
        );
    }

    private TransactionResultDTO validateTransaction(CustomerOrderTransactionDTO transaction) {
        if (transaction == null) {
            return new TransactionResultDTO(false, "Transaction data is required", "Transaction cannot be null");
        }

        if (transaction.getEmail() == null || transaction.getEmail().trim().isEmpty()) {
            return new TransactionResultDTO(false, "Email is required", "Customer email cannot be empty");
        }

        if (!isValidEmail(transaction.getEmail())) {
            return new TransactionResultDTO(false, "Invalid email format", "Please enter a valid email address");
        }

        if (transaction.getPhoneNumber() == null || transaction.getPhoneNumber().trim().isEmpty()) {
            return new TransactionResultDTO(false, "Phone number is required", "Phone number cannot be empty");
        }

        if (!isValidPhoneNumber(transaction.getPhoneNumber())) {
            return new TransactionResultDTO(false, "Invalid phone number format (7-15 characters, + optional)", "Please enter a valid phone number");
        }

        if (transaction.getPaymentMethod() == null || transaction.getPaymentMethod().trim().isEmpty()) {
            return new TransactionResultDTO(false, "Payment method is required", "Payment method cannot be empty");
        }

        if (!isValidPaymentMethod(transaction.getPaymentMethod())) {
            return new TransactionResultDTO(false, "Invalid payment method", 
                "Payment method must be 'karticno placanje' or 'gotovinsko placanje'");
        }

        if (transaction.getBottleSerialNumbers() == null || transaction.getBottleSerialNumbers().isEmpty()) {
            return new TransactionResultDTO(false, "Bottles selection is required", "At least one bottle must be selected");
        }

        // da ne selektuje istu bocu 2x
        long uniqueBottles = transaction.getBottleSerialNumbers().stream().distinct().count();
        if (uniqueBottles != transaction.getBottleSerialNumbers().size()) {
            return new TransactionResultDTO(false, "Duplicate bottles selected", "Each bottle can only be selected once");
        }

        return new TransactionResultDTO(true, "Validation successful");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[+]?[0-9\\s\\-\\(\\)]{7,15}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phoneNumber).matches();
    }

    private boolean isValidPaymentMethod(String paymentMethod) {
        String lowerPaymentMethod = paymentMethod.toLowerCase();
        return lowerPaymentMethod.equals("karticno placanje") ||
               lowerPaymentMethod.equals("gotovinsko placanje");
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
} 