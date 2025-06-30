# Vinarija IS - Winery Management System

A comprehensive Java-based wine management system for wineries.

## 🍷 Features

### Core Functionality
- **Wine Production Tracking**: Complete wine lifecycle from grape to bottle
- **Customer Management**: Customer registration with unique email validation
- **Order Processing**: Transactional order system with atomic operations
- **Inventory Management**: Bottle tracking and cellar management
- **Employee Management**: Staff tracking with role-based categorization

### Advanced Features
- **Comprehensive Reporting**: Multiple analytical reports 
- **Transaction Management**: Atomic customer order transactions with rollback support
- **Data Validation**: Multi-level validation (UI, Service, Database)
- **Error Handling**: Comprehensive error handling and user feedback

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher


## 📊 Available Reports

### 1. Grape Variety Wine Count
This report shows a list of all grape varieties in your winery and tells you how many different wines are made from each variety.  
**Purpose:** Helps you see which grape varieties are most commonly used in your wine production and which ones are less represented.

---

### 2. Wine Sales with Grape Varieties
This report analyzes your wine sales by showing, for each wine and its associated grape variety, how many bottles have been sold.  
**Purpose:** Helps you understand which grape varieties and wines are most popular with customers, and which contribute most to your sales.

---

### 3. Customer Analysis Report
This report provides insights into your customers’ behavior. For each customer, it shows how many orders they have placed and what their favorite wines are (based on their order history).  
**Purpose:** Helps you identify your most loyal customers, understand their preferences, and tailor marketing or loyalty programs accordingly.

---

## 🔄 Transaction Flow: New Customer Order

**How a customer order transaction works:**

1. **Customer Entry:**  
   The user enters a new customer’s email and phone number. The system checks that the email is unique.

2. **Payment Method:**  
   The user selects how the customer will pay (cash or card).

3. **Bottle Selection:**  
   The system displays all bottles that are available for sale. The user selects one or more bottles for the order.

4. **Validation:**  
   The system checks that all selected bottles are available, the customer email is unique, and all information is valid.

5. **Transaction Execution:**  
   - A new customer is created (if the email is unique).
   - A new order is created and linked to the customer.
   - The selected bottles are assigned to the order (they become “sold”).
   - If any step fails, the whole transaction is cancelled (no partial changes).


