package ru.netology.dataBase;

import lombok.SneakyThrows;

import java.sql.*;

public class DataBaseHelper {
    private DataBaseHelper() {

    }

    @SneakyThrows
    public static String dbGetStatus() {
        String dbSQLStatus = "SELECT status FROM payment_entity ;";
        String status = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                PreparedStatement statusStatement = connection.prepareStatement(dbSQLStatus);
        ) {
            try (ResultSet rs = statusStatement.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }

    @SneakyThrows
    public static String dbGetAmount() {
        String dbSQLAmount = "SELECT amount FROM payment_entity;";
        String amount = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                PreparedStatement amountStatement = connection.prepareStatement(dbSQLAmount);
        ) {
            try (ResultSet rs = amountStatement.executeQuery()) {
                if (rs.next()) {
                    amount = rs.getString("amount");
                }
            }
        }
        return amount;
    }

    @SneakyThrows
    public static String dbGetOrderEntity() {
        String dataSQLCount = "SELECT count(oe.id) as count FROM order_entity oe, payment_entity pe WHERE oe.payment_id = pe.transaction_id;";
        String orderEntity = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                PreparedStatement countStatement = connection.prepareStatement(dataSQLCount);
        ) {
            try (ResultSet rs = countStatement.executeQuery()) {
                if (rs.next()) {
                    orderEntity = rs.getString("count");
                }
            }
        }
        return orderEntity;
    }


    @SneakyThrows
    public static void dbClean() {
        String deleteCreditRequestEntity = "DELETE FROM credit_request_entity;";
        String deletePaymentEntity = "DELETE FROM payment_entity;";
        String deleteOrderEntity = "DELETE FROM order_entity;";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             Statement deleteCreditRequestEntityStatement = connection.createStatement();
             Statement deletePaymentEntityStatement = connection.createStatement();
             Statement deleteOrderEntityStatement = connection.createStatement();
        ) {
            deleteCreditRequestEntityStatement.executeUpdate(deleteCreditRequestEntity);
            deletePaymentEntityStatement.executeUpdate(deletePaymentEntity);
            deleteOrderEntityStatement.executeUpdate(deleteOrderEntity);
        }
    }
    @SneakyThrows
    public static String dbGetStatusByCreditCard() {
        String dbSQLStatus = "SELECT status FROM credit_request_entity ;";
        String status = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                PreparedStatement statusStatement = connection.prepareStatement(dbSQLStatus);
        ) {
            try (ResultSet rs = statusStatement.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }
}
