import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.InputMismatchException;

public class checkingAccount extends Account{
    private BigDecimal creditLimit;
    private BigDecimal fee;

    checkingAccount(BigDecimal creditLimit, BigDecimal fee, BigDecimal balance, String username){
        super(balance, username);
        this.creditLimit = creditLimit;
        this.fee = fee;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }
    public void setCreditLimit(BigDecimal creditLimit) {
        System.out.println("Current Balance: " + this.balance);
        if (creditLimit.compareTo(this.balance) < 0) {
            System.out.println("You can't set a credit limit that is less than your current balance.");
        } else {this.creditLimit = creditLimit;}
    }

    public BigDecimal getFee() {
        return fee;
    }
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }


    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(this.creditLimit) > 0) {
            super.withdraw(amount);
            if (amount.compareTo(this.balance) > 0) {
                this.setBalance(this.balance.subtract(this.fee));
                transactions.put(LocalDate.now(), this.fee.negate());
            }
        } else {
            System.out.println("You can't withdraw more than your credit limit.");
        }
    }

    public void printDetails(){
        super.printDetails();
        System.out.println("Credit Limit: " + getCreditLimit());
        System.out.println("Fee: " + getFee());
    }

    public void accountmenu() throws IOException {
        System.out.println("Press 1 to view account details. Press 2 to deposit. Press 3 to withdraw. Press 4 to " +
                "edit credit limit. Press 5 to edit overdraft fee. Press 6 to view past transactions. Press 7 to exit" +
                ".");
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
                    System.out.println("Enter new credit limit: ");
                    BigDecimal amount = driver.scbd.nextBigDecimal();
                    setCreditLimit(amount);
                }
                case 5 -> {
                    System.out.println("Enter new overdraft fee: ");
                    BigDecimal amount = driver.scbd.nextBigDecimal();
                    setFee(amount);
                }
                case 6 -> pastTransactions();
                case 7 -> driver.user.usermenu();
                default -> throw new IllegalArgumentException();
            }
        accountmenu();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Enter a number between 1 and 4.");
            accountmenu();
        }

    }
}
