package ru.netology.dataBase;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.*;

public class DataBaseHelper {
    private DataBaseHelper() {

    }

    @SneakyThrows
    public static ResultSet getConnectionMySQL(String selectSQL) {
        var dbUrl = System.getProperty("db.url");
        var login = System.getProperty("login");
        var password = System.getProperty("password");
        Connection connection = DriverManager.getConnection(dbUrl, login, password);
        PreparedStatement StatusStmt = connection.prepareStatement(selectSQL);
        return StatusStmt.executeQuery();

    }

    @SneakyThrows
    public static int getConnectionMySQLWithUpdate(String selectSQL) {
        var dbUrl = System.getProperty("db.url");
        var login = System.getProperty("login");
        var password = System.getProperty("password");
        Connection connection = DriverManager.getConnection(dbUrl, login, password);
        PreparedStatement StatusStmt = connection.prepareStatement(selectSQL);
        return StatusStmt.executeUpdate();

    }


    @SneakyThrows
    public static String dbGetStatus() {
        String statusSQL = "SELECT status FROM payment_entity; ";
        String status = null;
        try (val rs = getConnectionMySQL(statusSQL)) {
            if (rs.next()) {
                status = rs.getString("status");
            }
        }

        return status;
    }

    @SneakyThrows
    public static String dbGetAmountByDebitCard() {
        String dbSQLAmount = "SELECT amount FROM payment_entity;";
        String amount = null;
        try (val rs = getConnectionMySQL(dbSQLAmount)) {
            if (rs.next()) {
                amount = rs.getString("amount");
            }
        }
        return amount;
    }

    @SneakyThrows
    public static String dbGetOrderEntity() {
        String dbSQLEntity = "SELECT count(oe.id) as count FROM order_entity oe, payment_entity pe WHERE oe.payment_id = pe.transaction_id;";
        String orderEntity = null;

        try (val rs = getConnectionMySQL(dbSQLEntity)) {
            if (rs.next()) {
                orderEntity = rs.getString("count");
            }
        }

        return orderEntity;
    }


    @SneakyThrows
    public static void dbClean() {
        String deleteCreditRequestEntity = "DELETE FROM credit_request_entity;";
        String deletePaymentEntity = "DELETE FROM payment_entity;";
        String deleteOrderEntity = "DELETE FROM order_entity;";

        try {
            getConnectionMySQLWithUpdate(deletePaymentEntity);
            getConnectionMySQLWithUpdate(deleteOrderEntity);
            getConnectionMySQLWithUpdate(deleteCreditRequestEntity);
        } catch (Exception e) {

        }

    }

    @SneakyThrows
    public static String dbGetStatusByCreditCard() {
        String dbStatus = "SELECT status FROM credit_request_entity ;";
        String status = null;

        try (val rs = getConnectionMySQL(dbStatus)) {
            if (rs.next()) {
                status = rs.getString("status");
            }
        }

        return status;
    }
}
