package prog3;
import java.util.List;
import java.util.Scanner;

public class MessagingApp {
    private Scanner scanner = new Scanner(System.in);
    private DatabaseManager dbManager = new DatabaseManager();
    private User currentUser = null;

    public static void main(String[] args) {
        MessagingApp app = new MessagingApp();
        app.run();
    }

    private void run() {
        System.out.println("=== Messaging Application ===");
        
        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showAuthMenu() {
        System.out.println("\n1. Register\n2. Login\n3. Exit");
        System.out.print("Select option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        switch (choice) {
            case 1: register(); break;
            case 2: login(); break;
            case 3: System.exit(0);
            default: System.out.println("Invalid choice");
        }
    }

    private void register() {
        System.out.println("\n--- Registration ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        try {
            User newUser = new User(firstName, surname, username, phone, password);
            dbManager.registerUser(newUser);
            System.out.println("Registration successful!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        currentUser = dbManager.authenticate(username, password);
        if (currentUser != null) {
            System.out.println("Welcome, " + currentUser.getFirstName() + "!");
        } else {
            System.out.println("Invalid credentials");
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Send Message");
        System.out.println("2. View Sent Messages (Sender/Recipient)");
        System.out.println("3. Find Longest Message");
        System.out.println("4. Search by Message ID");
        System.out.println("5. Search by Recipient");
        System.out.println("6. Delete Message");
        System.out.println("7. Generate Full Report");
        System.out.println("8. Logout");
        System.out.print("Select option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1: sendMessage(); break;
            case 2: viewSentMessages(); break;
            case 3: findLongestMessage(); break;
            case 4: searchByMessageId(); break;
            case 5: searchByRecipient(); break;
            case 6: deleteMessage(); break;
            case 7: generateReport(); break;
            case 8: logout(); break;
            default: System.out.println("Invalid choice");
        }
    }

    private void sendMessage() {
        System.out.println("\n--- Send Message ---");
        System.out.print("Recipient: ");
        String recipient = scanner.nextLine();
        
        System.out.print("Message: ");
        String content = scanner.nextLine();
        
        System.out.print("Flag (Sent/Stored/Disregard): ");
        String flag = scanner.nextLine();
        
        Message message = new Message(currentUser.getUsername(), recipient, content, flag);
        dbManager.addMessage(message);
        System.out.println("Message sent! ID: " + message.getMessageId());
    }

    private void viewSentMessages() {
        System.out.println("\n--- Sent Messages ---");
        List<String> details = dbManager.getSentMessageDetails();
        if (details.isEmpty()) {
            System.out.println("No sent messages");
        } else {
            details.forEach(System.out::println);
        }
    }

    private void findLongestMessage() {
        System.out.println("\n--- Longest Message ---");
        System.out.println(dbManager.getLongestSentMessage());
    }

    private void searchByMessageId() {
        System.out.println("\n--- Search by Message ID ---");
        System.out.print("Enter Message ID: ");
        String id = scanner.nextLine();
        System.out.println(dbManager.searchByMessageId(id));
    }

    private void searchByRecipient() {
        System.out.println("\n--- Search by Recipient ---");
        System.out.print("Enter Recipient Username: ");
        String recipient = scanner.nextLine();
        List<Message> messages = dbManager.getMessagesByRecipient(recipient);
        if (messages.isEmpty()) {
            System.out.println("No messages found for this recipient");
        } else {
            System.out.println("Messages to " + recipient + ":");
            messages.forEach(msg -> System.out.println("- " + msg.getContent()));
        }
    }

    private void deleteMessage() {
        System.out.println("\n--- Delete Message ---");
        System.out.print("Enter Message Hash: ");
        String hash = scanner.nextLine();
        if (dbManager.deleteMessageByHash(hash)) {
            System.out.println("Message deleted successfully");
        } else {
            System.out.println("Message not found");
        }
    }

    private void generateReport() {
        System.out.println("\n--- Sent Messages Report ---");
        List<String> report = dbManager.generateSentMessagesReport();
        if (report.isEmpty()) {
            System.out.println("No sent messages to report");
        } else {
            report.forEach(System.out::println);
        }
    }

    private void logout() {
        currentUser = null;
        System.out.println("Logged out successfully");
    }
}