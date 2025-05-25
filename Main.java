import java.util.*;

// Base class
class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    BankAccount(String accountNumber, String accountHolder, double accBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = Math.max(accBalance, 0);
    }

    public void deposit(double depositAmount) {
        if (depositAmount > 0) {
            balance += depositAmount;
            System.out.println("₹" + depositAmount + " deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void showDetails() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Type: " + this.getClass().getSimpleName());
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: ₹" + balance);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        System.out.println("Withdrawal not allowed from base class.");
    }
}

// Savings Account with interest
class SavingsAccount extends BankAccount {
    private final double interestRate = 0.04; // 4%

    SavingsAccount(String accNo, String accHolder, double balance) {
        super(accNo, accHolder, balance);
    }

    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest ₹" + String.format("%.2f", interest) + " added to account.");
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully.");
        } else {
            System.out.println("Invalid or insufficient balance.");
        }
    }
}

// Current Account with overdraft
class CurrentAccount extends BankAccount {
    private final double overdraftLimit = 10000;

    CurrentAccount(String accNo, String accHolder, double balance) {
        super(accNo, accHolder, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully (includes overdraft if applicable).");
        } else {
            System.out.println("Withdrawal exceeds overdraft limit.");
        }
    }
}

// Main banking system
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Banking System Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Show Details");
            System.out.println("5. Add Interest (Savings only)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> depositToAccount();
                case 3 -> withdrawFromAccount();
                case 4 -> showAccountDetails();
                case 5 -> addInterestToSavings();
                case 6 -> {
                    System.out.println("Thank you for using the banking system.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter account type (savings/current): ");
        String type = sc.nextLine().toLowerCase();
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        System.out.print("Enter account holder name: ");
        String name = sc.nextLine();
        System.out.print("Enter initial deposit: ");
        double deposit = sc.nextDouble();
        sc.nextLine(); // consume newline

        BankAccount account = switch (type) {
            case "savings" -> new SavingsAccount(accNo, name, deposit);
            case "current" -> new CurrentAccount(accNo, name, deposit);
            default -> {
                System.out.println("Invalid account type.");
                yield null;
            }
        };

        if (account != null) {
            accounts.put(accNo, account);
            System.out.println(type.substring(0, 1).toUpperCase() + type.substring(1) + " account created successfully.");
        }
    }

    private static BankAccount getAccount(String accNo) {
        if (!accounts.containsKey(accNo)) {
            System.out.println("Account not found.");
            return null;
        }
        return accounts.get(accNo);
    }

    private static void depositToAccount() {
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        BankAccount acc = getAccount(accNo);
        if (acc != null) {
            System.out.print("Enter amount to deposit: ");
            double amt = sc.nextDouble();
            sc.nextLine();
            acc.deposit(amt);
        }
    }

    private static void withdrawFromAccount() {
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        BankAccount acc = getAccount(accNo);
        if (acc != null) {
            System.out.print("Enter amount to withdraw: ");
            double amt = sc.nextDouble();
            sc.nextLine();
            acc.withdraw(amt);
        }
    }

    private static void showAccountDetails() {
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        BankAccount acc = getAccount(accNo);
        if (acc != null) {
            acc.showDetails();
        }
    }

    private static void addInterestToSavings() {
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        BankAccount acc = getAccount(accNo);
        if (acc instanceof SavingsAccount) {
            ((SavingsAccount) acc).addInterest();
        } else {
            System.out.println("Interest only applies to savings accounts.");
}

}}

