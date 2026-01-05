import java.util.ArrayList;
import java.util.Scanner;

public class ATMInterface {

    // ---------- USER ----------
    static class User {
        private String userId = "user123";
        private String pin = "1234";

        public boolean login(String id, String enteredPin) {
            return userId.equals(id) && pin.equals(enteredPin);
        }
    }

    // ---------- ACCOUNT ----------
    static class Account {
        private double balance = 10000;

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
        }

        public boolean withdraw(double amount) {
            if (amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        }
    }

    // ---------- TRANSACTION ----------
    static class Transaction {
        private ArrayList<String> history = new ArrayList<String>();

        public void add(String record) {
            history.add(record);
        }

        public void show() {
            if (history.isEmpty()) {
                System.out.println("No transactions available.");
            } else {
                for (String h : history) {
                    System.out.println(h);
                }
            }
        }
    }

    // ---------- ATM OPERATIONS ----------
    static class ATMOperations {
        private Account account;
        private Transaction transaction;
        private Scanner sc;

        public ATMOperations(Account account, Transaction transaction, Scanner sc) {
            this.account = account;
            this.transaction = transaction;
            this.sc = sc;
        }

        public void showHistory() {
            System.out.println("\n--- Transaction History ---");
            transaction.show();
            System.out.println("Current Balance: " + account.getBalance());
        }

        public void withdraw() {
            System.out.print("Enter amount to withdraw: ");
            double amount = sc.nextDouble();

            if (account.withdraw(amount)) {
                transaction.add("Withdrawn: " + amount);
                System.out.println("Withdrawal successful.");
            } else {
                System.out.println("Insufficient balance.");
            }
        }

        public void deposit() {
            System.out.print("Enter amount to deposit: ");
            double amount = sc.nextDouble();

            account.deposit(amount);
            transaction.add("Deposited: " + amount);
            System.out.println("Deposit successful.");
        }

        public void transfer() {
            System.out.print("Enter amount to transfer: ");
            double amount = sc.nextDouble();

            if (account.withdraw(amount)) {
                transaction.add("Transferred: " + amount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient balance.");
            }
        }
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); 

        User user = new User();
        Account account = new Account();
        Transaction transaction = new Transaction();
        ATMOperations atm = new ATMOperations(account, transaction, sc);

        System.out.println("===== WELCOME TO ATM INTERFACE =====");

        System.out.print("Enter User ID: ");
        String id = sc.nextLine();

        System.out.print("Enter User PIN: ");
        String pin = sc.nextLine();

        if (!user.login(id, pin)) {
            System.out.println("Invalid User ID or PIN!");
            sc.close();
            return;
        }

        System.out.println("\nLogin Successful!");

        int choice;
        do {
            System.out.println("\n----- ATM MENU -----");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    atm.showHistory();
                    break;
                case 2:
                    atm.withdraw();
                    break;
                case 3:
                    atm.deposit();
                    break;
                case 4:
                    atm.transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close(); 
    }
}
