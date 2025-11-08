# E-Commerce Application

A terminal-based Java application that simulates a complete e-commerce workflow including product browsing, cart management, order processing, inventory and account management. The system interacts with a MySQL database using JDBC.

---

## Features

- Customer Registration & Login
- View Available Products
- Add / Remove Items from Cart
- Place Orders and View Order Summary
- Manage Customer Profile
- Database-backed Product & Order Storage
- Custom Exception Handling for Clear Error Messages

---

## Tech Stack

| Layer        | Technology Used        |
|--------------|------------------------|
| Language     | Java (JDK 21 or later) |
| Database     | MySQL (via XAMPP)      |
| Connectivity | JDBC                   |
| Interface    | Terminal / CLI         |

---

## Project Structure

***architecture :***
(DAO → Services → Main UI)

```
src/
│
├── dao/                                                    ----------->        Data Access Layer
│ ├── CartDao.java
│ ├── CartDaoImpl.java
│ ├── CustomerDao.java
│ ├── CustomerDaoImpl.java
│ ├── OrderProcessRepository.java
│ ├── OrderProcessRepositoryImpl.java
│ ├── ProductDao.java
│ └── ProductDaoImpl.java
│
├── database/                                                     ------->      DB Connection Utilities
│ └── scheme.sql
│
├── entity/                                                        ------->    Entity Classes
│ └── (Product, Customer, Cart, Order )
│
├── exception/                                                 ---------->      Custom Exceptions
│ ├── CustomerNotFoundException.java
│ ├── OrderNotFoundException.java
│ └── ProductNotFoundException.java
│
├── services/                           ----->    just a middle layer to keep user inputs stmts and daoimpl seperate
│ ├── CartServices.java
│ ├── CustomerServices.java
│ ├── OrderServices.java
│ └── ProductServices.java
│
├── util/                                                    ------------>    Utility Classes
│ └── (DBConnectionUtil, DBPropertyUtil, TestDBConnectionUtil)
│
└── Main.java                                                 ---------->   Application Entry Point (Menu-based UI)
```

---

## Database Setup (MySQL - XAMPP)
1. Start **Apache** and **MySQL** in XAMPP.
2. Open **phpMyAdmin**.
3. Create a new database and name it as `ecommerce_db`
4. Go to **Import** and upload the `ecommerce.sql` file.
5. Update your DB credentials in `DBConnectionUtil.java`.


```java
private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db";
private static final String USER = "root";
private static final String PASSWORD = "";
```
