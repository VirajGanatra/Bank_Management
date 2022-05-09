import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.time.LocalDate;
import java.util.Map;

public class Account implements Serializable {
    BigDecimal balance;
    final String username;
    static Map<LocalDate, BigDecimal> transactions = new HashMap<>();
    final LocalDate created = LocalDate.now();

    Account(BigDecimal balance, String username) {
        this.balance = balance;
        this.username = username;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void deposit(BigDecimal amount) {
        this.setBalance(this.balance.add(amount));
        transactions.put(LocalDate.now(), amount);
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) >= 0) {
            this.setBalance(this.balance.subtract(amount));
            transactions.put(LocalDate.now(), amount.negate());
            System.out.println("Transaction successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(BigDecimal amount) {
        System.out.println("Enter account number to transfer to: ");
        int accnum = driver.scint.nextInt();
        if (driver.accounts.containsKey(accnum)){
            Account transfer = driver.accounts.get(accnum);
            withdraw(amount);
            transfer.deposit(amount);
        } else {
            System.out.println("Account does not exist.");
        }

    }

    public void pastTransactions() throws IOException {
        System.out.println("Press 1 to view all transactions. Press 2 to view transactions up to a specified date. " +
                "Press 3 to view transactions on a specified date. Press 4 to exit.");
        int choice = driver.scint.nextInt();
        try {
            switch (choice) {
                case 1 -> System.out.println(transactions);
                case 2 -> {
                    System.out.println("Enter date to view transactions up to: ");
                    LocalDate date = LocalDate.parse(driver.scstring.nextLine());
                    System.out.println(transactions.entrySet().stream().filter(x -> x.getKey().isBefore(date)));
                }
                case 3 -> {
                    System.out.println("Enter date to view transactions on: ");
                    LocalDate date = LocalDate.parse(driver.scstring.nextLine());
                    System.out.println(transactions.entrySet().stream().filter(x -> x.getKey().isEqual(date)));
                }
                case 4 -> accountmenu();
                default -> throw new IllegalArgumentException();
            }

        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Enter a number from 1 to 4.");
        }
    }

    public void printDetails() {
        System.out.println("Account Details:");
        System.out.println("Balance: " + getBalance());
    }

    public void accountmenu() throws IOException{
        System.out.println("Press 1 to view account details. Press 2 to deposit. Press 3 to withdraw. Press 4 to " +
                "transfer. Press 5 to view transactions. Press 6 to exit.");
        int choice = driver.scint.nextInt();
        try {
            switch(choice){
                case 1 -> printDetails();
                case 2 -> {
                    System.out.println("Enter amount to deposit: ");
                    BigDecimal amount = driver.scbd.nextBigDecimal();
                    deposit(amount);
                }
                case 3 -> {
                    System.out.println("Enter amount to withdraw: ");
                    BigDecimal amount = driver.scbd.nextBigDecimal();
                    withdraw(amount);
                }
                case 4 -> {
                    System.out.println("Enter amount to transfer: ");
                    BigDecimal amount = driver.scbd.nextBigDecimal();
                    transfer(amount);
                }
                case 5 -> pastTransactions();
                case 6 -> driver.user.usermenu();
                default -> throw new IllegalArgumentException();
            }
        accountmenu();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Enter a number between 1 and 4.");
            accountmenu();
        }

    }


}


