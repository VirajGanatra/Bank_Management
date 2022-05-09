import java.io.IOException;
import java.util.InputMismatchException;

public class Admin extends User {
    private final String username = "Admin";

    public Admin() {
        super();
    }

    public void adminmenu() throws IOException {
        System.out.println("Press 1 to view all users. Press 2 to view all accounts. Press 3 to change password. " +
                "Press 4 to quit.");
        try {
            int choice = driver.scint.nextInt();
            switch (choice) {
                case 1 -> {
                    for (var entry : driver.users.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                }
                case 2 -> {
                    for (var entry : driver.accounts.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                }
                case 3 -> {
                    System.out.println("Enter new password: ");
                    String newpass = driver.scstring.nextLine();
                    driver.users.replace("Admin", newpass);
                }
                case 4 -> driver.quit();
                default -> throw new IllegalArgumentException();
            }
        adminmenu();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a value from 1 to 4.");
            adminmenu();
        }


    }

}



