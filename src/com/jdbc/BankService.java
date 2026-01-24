package com.jdbc;

public class BankService {

    private BankDAO dao = new BankDAO();

    public void addCustomer(String name, String city, String mobile) throws Exception {
        dao.addCustomer(name, city, mobile);
        System.out.println("Customer added successfully");
    }

    public void createAccount(int custId, double balance) throws Exception {
        dao.createAccount(custId, balance);
        System.out.println("Account created successfully");
    }

    public void showBalance(int accId) throws Exception {
        double balance = dao.getBalance(accId);
        System.out.println("Current Balance: " + balance);
    }

    public void deposit(int accId, double amount) throws Exception {
        dao.deposit(accId, amount);
        System.out.println("Deposit successful");
    }

    public void withdraw(int accId, double amount) throws Exception {
        dao.withdraw(accId, amount);
        System.out.println("Withdrawal successful");
    }

    public void transfer(int fromAcc, int toAcc, double amount) throws Exception {
        dao.transfer(fromAcc, toAcc, amount);
        System.out.println("Transfer completed successfully");
    }
}
