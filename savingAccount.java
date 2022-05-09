import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.InputMismatchException;

import static java.time.temporal.ChronoUnit.DAYS;

public class savingAccount extends Account {
    private BigDecimal interestRate;

    public savingAccount(BigDecimal balance, BigDecimal interestRate, String username) {
        super(balance, username);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void addInterest() {
        BigDecimal interest = this.getBalance().multiply(this.interestRate);
        this.deposit(interest);
    }

    public void accountmenu() throws IOException {
        System.out.println("Press 1 to view account details. Press 2 to " +
                "edit interest rate. Press 3 to add interest. Press 4 to view past transactions. Press 5 to exit.");
        int choice = driver.scint.nextInt();
        try {
            switch(choice){
                case 1 -> printDetails();
                case 2 -> {
                    System.out.println("Enter new interest rate: ");
                    BigDecimal amount = driver.scbd.nextBigDecimal();
                    setInterestRate(amount);
                }
                case 3 -> {
                    LocalDate current = LocalDate.now();
                    if (DAYS.between(created, current)%31 == 0) {
                        addInterest();
                        System.out.println("Interest added.");
                    } else {
                        System.out.println("Interest cannot be added at this time. It must be added every month.");
                    }
                }
                case 4 -> pastTransactions();
                case 5 -> driver.user.usermenu();
                default -> throw new IllegalArgumentException();
            }
        accountmenu();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Enter a number between 1 and 4.");
            accountmenu();
        }

    }
}
