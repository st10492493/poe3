package prog3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    // All required arrays
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> disregardedMessages = new ArrayList<>();
    private List<Message> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIds = new ArrayList<>();
    
    private Map<String, User> users = new HashMap<>();

    public void addMessage(Message message) {
        switch (message.getFlag()) {
            case "Sent":
                sentMessages.add(message);
                break;
            case "Disregard":
                disregardedMessages.add(message);
                break;
            case "Stored":
                storedMessages.add(message);
                break;
        }
        messageHashes.add(message.getHash());
        messageIds.add(message.getMessageId());
    }

    // User management
    public void registerUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.verifyPassword(password)) {
            return user;
        }
        return null;
    }

    // Required functionality implementations
    public List<String> getSentMessageDetails() {
        List<String> details = new ArrayList<>();
        for (Message msg : sentMessages) {
            details.add("From: " + msg.getSender() + " | To: " + msg.getRecipient());
        }
        return details;
    }

    public String getLongestSentMessage() {
        Message longest = null;
        for (Message msg : sentMessages) {
            if (longest == null || msg.getContent().length() > longest.getContent().length()) {
                longest = msg;
            }
        }
        return longest != null ? longest.getContent() : "No sent messages";
    }

    public String searchByMessageId(String messageId) {
        for (Message msg : getAllMessages()) {
            if (msg.getMessageId().equals(messageId)) {
                return "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getContent();
            }
        }
        return "Message not found";
    }

    public List<Message> getMessagesByRecipient(String recipient) {
        List<Message> results = new ArrayList<>();
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                results.add(msg);
            }
        }
        return results;
    }

    public boolean deleteMessageByHash(String hash) {
        for (List<Message> messageList : List.of(sentMessages, disregardedMessages, storedMessages)) {
            if (messageList.removeIf(msg -> msg.getHash().equals(hash))) {
                messageHashes.remove(hash);
                return true;
            }
        }
        return false;
    }

    public List<String> generateSentMessagesReport() {
        List<String> report = new ArrayList<>();
        for (Message msg : sentMessages) {
            report.add(String.format(
                "Hash: %s | ID: %s\nFrom: %s\nTo: %s\nContent: %s\nFlag: %s\n",
                msg.getHash(), msg.getMessageId(), msg.getSender(), 
                msg.getRecipient(), msg.getContent(), msg.getFlag()
            ));
        }
        return report;
    }

    private List<Message> getAllMessages() {
        List<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(disregardedMessages);
        all.addAll(storedMessages);
        return all;
    }

   
   
}