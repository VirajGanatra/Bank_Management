import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.InputMismatchException;

import static java.time.temporal.ChronoUnit.DAYS;

public class loanAccount extends Account{
    private BigDecimal principal;
    private BigDecimal interestRate;
    private int term;

    loanAccount(BigDecimal balance, BigDecimal principal, BigDecimal interestRate, int term, String username) {
        super(balance, username);
        this.principal = principal;
        this.interestRate = interestRate;
        this.term = term;
    }

    public void withdraw(){}

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void removeBalanceMonthly() {
        BigDecimal interest = this.principal.multiply(this.interestRate.divide(new BigDecimal("100"), RoundingMode.HALF_EVEN).add(BigDecimal.ONE));
        this.setBalance(interest.subtract(this.balance));
    }

    public void accountmenu() throws IOException {
        System.out.println("Press 1 to view account details. Press 2 to " +
                "edit interest rate. Press 3 to debit interest. Press 4 to view past transactions. Press 5 to exit.");
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
                        removeBalanceMonthly();
                        System.out.println("Interest debited.");
                    } else {
                        System.out.println("Interest cannot be debited at this time. It must be debited every month.");
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





