import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class driver {
    public static User user;
    public static Admin admin;
    static String username;
    static String password;
    public static final Scanner scstring = new Scanner(System.in);
    public static final Scanner scint = new Scanner(System.in);
    public static final Scanner scbd = new Scanner(System.in);

    static Map <String, String> users = new HashMap<>();
    static Map <Integer, Account> accounts = new HashMap<>();


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        read();
        users.putIfAbsent("Admin", "admin");
        loginmenu();
    }

    public static void loginmenu() {
        System.out.println("Press 1 to login. Press 2 to register. Press 3 to exit.");
        try {
            int choice = scint.nextInt();
            switch (choice) {
                case 1 -> {
                    userpass();
                    if (users.get(username).equals(password)) {
                        System.out.println("Welcome " + username + "!");
                        if (username.equals("Admin")) {
                            admin = new Admin();
                            admin.adminmenu();
                        } else {
                            user = new User(username);
                            user.usermenu();
                        }
                    } else {
                        System.out.println("Incorrect password.");
                        loginmenu();
                    }
                }
                case 2 -> {
                    userpass();
                    if (users.containsKey(username)) {
                        System.out.println("Username already exists.");
                        loginmenu();
                    } else {
                        System.out.println("Thank you for registering.");
                        User user = new User(username);
                        user.usermenu();
                    }
                }
                case 3 -> quit();
                default -> throw new IllegalArgumentException();
            }
        }
        catch (InputMismatchException | IllegalArgumentException e){
            System.out.println("Invalid input. Please enter a value from 1 to 3.");
            loginmenu();
        } catch (IOException e) {
            e.printStackTrace();
            loginmenu();
        } catch (NullPointerException e){
            System.out.println("User does not exist.");
            loginmenu();
        }
    }

    @SuppressWarnings("unchecked")
    public static void read() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.json"));
            users = (HashMap<String, String>) ois.readObject();
            ois = new ObjectInputStream(new FileInputStream("accounts.json"));
            accounts = (HashMap<Integer, Account>) ois.readObject();
            ois.close();
            System.out.println("Data loaded successfully.");
        } catch (FileNotFoundException e) {
            save();
            System.out.println("No data found. File created.");
        }
    }

    public static void userpass(){
        System.out.println("Please enter your username: ");
        username = scstring.nextLine();
        System.out.println("Please enter your password: ");
        password = scstring.nextLine();
    }

    public static void quit() throws IOException {
        save();
        System.out.println("Thank you for using the ATM.");
        System.exit(0);
    }

    public static void save() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.json"));
        oos.writeObject(users);
        oos = new ObjectOutputStream(new FileOutputStream("accounts.json"));
        oos.writeObject(accounts);
        oos.close();
    }
    }

