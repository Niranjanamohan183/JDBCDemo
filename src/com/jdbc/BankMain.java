package com.jdbc;

import java.util.Scanner;

public class BankMain {

    public static void main(String[] args) {
    	
    	System.out.println("Connection Test");

        Scanner sc = new Scanner(System.in);
        BankService service = new BankService();

        while (true) {
            try {
                System.out.println("\n===== BANK MENU =====");
                System.out.println("1. Add Customer");
                System.out.println("2. Create Account");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. Transfer");
                System.out.println("6. View Balance");
                System.out.println("7. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Customer Name: ");
                        sc.nextLine();
                        String name = sc.nextLine();

                        System.out.print("Enter City: ");
                        String city = sc.nextLine();

                        System.out.print("Enter Mobile: ");
                        String mobile = sc.nextLine();

                        service.addCustomer(name, city, mobile);
                        break;

                    case 2:
                        System.out.print("Enter Customer ID: ");
                        int custId = sc.nextInt();

                        System.out.print("Enter Initial Balance: ");
                        double balance = sc.nextDouble();

                        service.createAccount(custId, balance);
                        break;

                    case 3:
                        System.out.print("Enter Account ID: ");
                        int depAcc = sc.nextInt();

                        System.out.print("Enter Deposit Amount: ");
                        double depAmt = sc.nextDouble();

                        service.deposit(depAcc, depAmt);
                        break;

                    case 4:
                        System.out.print("Enter Account ID: ");
                        int withAcc = sc.nextInt();

                        System.out.print("Enter Withdraw Amount: ");
                        double withAmt = sc.nextDouble();

                        service.withdraw(withAcc, withAmt);
                        break;

                    case 5:
                        System.out.print("Enter From Account ID: ");
                        int fromAcc = sc.nextInt();

                        System.out.print("Enter To Account ID: ");
                        int toAcc = sc.nextInt();

                        System.out.print("Enter Transfer Amount: ");
                        double transferAmt = sc.nextDouble();

                        service.transfer(fromAcc, toAcc, transferAmt);
                        break;

                    case 6:
                        System.out.print("Enter Account ID: ");
                        int balAcc = sc.nextInt();

                        service.showBalance(balAcc);
                        break;

                    case 7:
                        System.out.println("Thank you for using Bank App!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
}
