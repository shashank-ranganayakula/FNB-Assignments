package Java_Assignment2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Custom Exceptions

class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String username) {
        super("Error: User '" + username + "' already exists in the registry.");
    }
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String username) {
        super("Error: User '" + username + "' was not found in the registry.");
    }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super("Invalid input: " + message);
    }
}

// User Model

class User {
    private String username;
    private String email;
    private int age;

    public User(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }

    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public int    getAge()      { return age; }

    public void setEmail(String email) { this.email = email; }
    public void setAge(int age)        { this.age = age; }

    @Override
    public String toString() {
        return "Username: " + username + ", Email: " + email + ", Age: " + age;
    }
}

// UserRegistry - core logic using HashMap and ArrayList

class UserRegistry {

    // HashMap: username -> User (O(1) lookup)
    private HashMap<String, User> userMap = new HashMap<>();

    // ArrayList: preserves insertion order for listing
    private ArrayList<String> insertionOrder = new ArrayList<>();

    public void addUser(String username, String email, int age)
            throws UserAlreadyExistsException, InvalidInputException {

        if (username == null || username.isBlank())
            throw new InvalidInputException("Username cannot be empty.");
        if (!email.contains("@"))
            throw new InvalidInputException("Email must contain '@'.");
        if (age < 1 || age > 120)
            throw new InvalidInputException("Age must be between 1 and 120.");
        if (userMap.containsKey(username))
            throw new UserAlreadyExistsException(username);

        User newUser = new User(username, email, age);
        userMap.put(username, newUser);      // HashMap: store
        insertionOrder.add(username);        // ArrayList: track order
        System.out.println("User '" + username + "' registered successfully.");
    }

    public User searchUser(String username) throws UserNotFoundException {
        if (!userMap.containsKey(username))
            throw new UserNotFoundException(username);
        return userMap.get(username);        // HashMap: O(1) lookup
    }

    public void deleteUser(String username) throws UserNotFoundException {
        if (!userMap.containsKey(username))
            throw new UserNotFoundException(username);
        userMap.remove(username);            // HashMap: remove
        insertionOrder.remove(username);     // ArrayList: remove
        System.out.println("User '" + username + "' deleted.");
    }

    public void updateUser(String username, String newEmail, int newAge)
            throws UserNotFoundException, InvalidInputException {

        User user = searchUser(username);
        if (!newEmail.contains("@"))
            throw new InvalidInputException("Email must contain '@'.");
        if (newAge < 1 || newAge > 120)
            throw new InvalidInputException("Age must be between 1 and 120.");
        user.setEmail(newEmail);
        user.setAge(newAge);
        System.out.println("User '" + username + "' updated successfully.");
    }

    public void listAllUsers() {
        if (insertionOrder.isEmpty()) {
            System.out.println("Registry is empty.");
            return;
        }
        System.out.println("\nAll Users:");
        // Iterate ArrayList to preserve insertion order
        for (String uname : insertionOrder) {
            System.out.println(userMap.get(uname));
        }
        System.out.println("Total users: " + userMap.size());
    }

    public int size() { return userMap.size(); }
}

// Main CLI Application

public class UserRegistryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserRegistry registry = new UserRegistry();

    public static void main(String[] args) {
        System.out.println("User Registry CLI App");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addUserFlow();
                case 2 -> searchUserFlow();
                case 3 -> deleteUserFlow();
                case 4 -> updateUserFlow();
                case 5 -> registry.listAllUsers();
                case 6 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1-6.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Register User");
        System.out.println("2. Search User");
        System.out.println("3. Delete User");
        System.out.println("4. Update User");
        System.out.println("5. List All Users");
        System.out.println("6. Exit");
    }

    private static void addUserFlow() {
        System.out.println("\nRegister New User");
        String username = readString("Username: ");
        String email    = readString("Email: ");
        int    age      = readInt("Age: ");

        try {
            registry.addUser(username, email, age);
        } catch (UserAlreadyExistsException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchUserFlow() {
        System.out.println("\nSearch User");
        String username = readString("Username: ");
        try {
            User user = registry.searchUser(username);
            System.out.println("User found: " + user);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteUserFlow() {
        System.out.println("\nDelete User");
        String username = readString("Username: ");
        try {
            registry.deleteUser(username);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateUserFlow() {
        System.out.println("\nUpdate User");
        String username = readString("Username: ");
        String newEmail = readString("New Email: ");
        int    newAge   = readInt("New Age: ");
        try {
            registry.updateUser(username, newEmail, newAge);
        } catch (UserNotFoundException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}