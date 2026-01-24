package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankDAO {

    // Add customer
    public void addCustomer(String name, String city, String mobile) throws Exception {
        String sql = "INSERT INTO customers (customer_name, city, mobile) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, city);
            ps.setString(3, mobile);
            ps.executeUpdate();
        }
    }

    // Create account
    public void createAccount(int customerId, double balance) throws Exception {
        String sql = "INSERT INTO accounts (customer_id, balance) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.setDouble(2, balance);
            ps.executeUpdate();
        }
    }

    // Get balance
    public double getBalance(int accountId) throws Exception {
        String sql = "SELECT balance FROM accounts WHERE account_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            } else {
                throw new Exception("Account not found");
            }
        }
    }

    // Public deposit
    public void deposit(int accountId, double amount) throws Exception {
        try (Connection con = DBConnection.getConnection()) {
            depositInternal(con, accountId, amount);
        }
    }

    // Public withdraw
    public void withdraw(int accountId, double amount) throws Exception {
        try (Connection con = DBConnection.getConnection()) {
            withdrawInternal(con, accountId, amount);
        }
    }

    // Transfer (ACID transaction)
    public void transfer(int fromAcc, int toAcc, double amount) throws Exception {
        Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);

        try {
            withdrawInternal(con, fromAcc, amount);
            depositInternal(con, toAcc, amount);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    // Internal deposit
    private void depositInternal(Connection con, int accountId, double amount) throws Exception {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        }

        recordTransaction(con, accountId, "DEPOSIT", amount);
    }

    // Internal withdraw
    private void withdrawInternal(Connection con, int accountId, double amount) throws Exception {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        }

        recordTransaction(con, accountId, "WITHDRAW", amount);
    }

    // Transaction log
    private void recordTransaction(Connection con, int accountId, String type, double amount) throws Exception {
        String sql = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        }
    }
}
