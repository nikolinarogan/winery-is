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
- **Purpose**: Shows how many wines are made from each grape variety
- **Access**: Main Menu → Reports → Grape Variety Wine Count
- **Features**: 
  - Filter by grape variety
  - Sort by wine count
  - Export functionality

### 2. Wine Sales with Grape Varieties
- **Purpose**: Analyzes wine sales performance by grape variety
- **Access**: Main Menu → Reports → Wine Sales with Grape Varieties
- **Features**:
  - Revenue analysis
  - Bottle count tracking
  - Grape variety performance comparison

### 3. Customer Analysis Report
- **Purpose**: Customer behavior and preference analysis
- **Access**: Main Menu → Reports → Customer Analysis
- **Features**:
  - Customer segmentation
  - Preference analysis
  - Order frequency tracking

## 💳 Transaction System

### Customer Order Transaction
- **Purpose**: Complete customer order workflow (customer + order + bottle)
- **Access**: Main Menu → Transactions → Customer Order
- **Features**:
  - Atomic transaction with rollback support
  - Email uniqueness validation
  - Comprehensive data validation
  - Real-time error feedback
