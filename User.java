import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class User {
    String username;

    public User() {}

    public User(String username) {
        this.username = username;
    }


    public void usermenu() throws IOException {
        System.out.println("Press 1 to access an account. Press 2 to open an account. Press 3 to close an " +
                "account. Press 4 to change password. Press 5 to quit.");
        try {
            int choice = driver.scint.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Your accounts: \n");
                    for (var account : driver.accounts.entrySet()) {
                        if (account.getValue().getUsername().equals(username)) {
                            System.out.println("Type: " + account.getValue().getClass().getSimpleName());
                            System.out.println("Account number: " + account.getKey());
                            System.out.println("Balance: " + account.getValue().getBalance());

                        }
                    }
                    System.out.println("Enter account number: ");
                    Integer accnum = driver.scint.nextInt();
                    if (driver.accounts.containsKey(accnum)) {
                        Account account = driver.accounts.get(accnum);
                        if (account.getUsername().equals(username)) {
                            account.accountmenu();
                        } else {
                            System.out.println("You are not the owner of this account.");
                        }
                    } else {
                        System.out.println("Account does not exist.");
                    }
                }
                case 2 -> newacc();
                case 3 -> delacc();
                case 4 -> {
                    System.out.println("Enter new password: ");
                    String newpass = driver.scstring.nextLine();
                    driver.users.replace(username, newpass);
                }
                case 5 -> driver.quit();
                default -> throw new IllegalArgumentException();
            }
        usermenu();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a value from 1 to 4.");
            usermenu();
        }

    }

    public void newacc() throws IOException {
        int accnum;
        try {
            accnum = Collections.max(driver.accounts.keySet()) + 1;
        } catch (NoSuchElementException e) {
            accnum = 1;
        }
        try {
            System.out.println("Press 1 for standard account. Press 2 for checking account. Press 3 for saving account. " +
                    "Press 4 for loan account. Press 5 to go back.");
            int choice = driver.scint.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter starting balance: ");
                    BigDecimal balance = driver.scbd.nextBigDecimal();
                    Account account = new Account(balance, username);
                    driver.accounts.put(accnum, account);
                }
                case 2 -> {
                    System.out.println("Enter starting balance: ");
                    BigDecimal balance = driver.scbd.nextBigDecimal();
                    System.out.println("Enter credit limit: ");
                    BigDecimal credit = driver.scbd.nextBigDecimal();
                    System.out.println("Enter overdraft fee: ");
                    BigDecimal fee = driver.scbd.nextBigDecimal();
                    Account account = new checkingAccount(credit, fee, balance, username);
                    driver.accounts.put(accnum, account);
                }
                case 3 -> {
                    System.out.println("Enter starting balance: ");
                    BigDecimal balance = driver.scbd.nextBigDecimal();
                    System.out.println("Enter interest rate: ");
                    BigDecimal interest = driver.scbd.nextBigDecimal();
                    Account account = new savingAccount(balance, interest, username);
                    driver.accounts.put(accnum, account);
                }
                case 4 -> {
                    System.out.println("Enter starting balance: ");
                    BigDecimal balance = driver.scbd.nextBigDecimal();
                    System.out.println("Enter interest rate: ");
                    BigDecimal interest = driver.scbd.nextBigDecimal();
                    System.out.println("Enter term length in months: ");
                    int term = driver.scint.nextInt();
                    Account account = new loanAccount(balance, balance, interest, term, username);
                    driver.accounts.put(accnum, account);
                }
                case 5 -> usermenu();
                default -> throw new IllegalArgumentException();
            }
            System.out.println("Account created. Your new account number is " + accnum + ".");
            newacc();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a value from 1 to 5.");
            newacc();
        }
    }

    public void delacc() throws IOException {
        System.out.println("Enter account number to delete: ");
        Integer accnum = driver.scint.nextInt();
        if (driver.accounts.containsKey(accnum)) {
            Account account = driver.accounts.get(accnum);
            if (account.getUsername().equals(username)) {
                driver.accounts.remove(accnum);
            } else {
                System.out.println("You are not the owner of this account.");
                usermenu();
            }
        } else {
            System.out.println("Account does not exist.");
            delacc();
        }
    }

}
