package prog3;
import java.security.MessageDigest;
import java.util.UUID;

public class Message {
    private String sender;
    private String recipient;
    private String content;
    private String flag; // Sent, Stored, Disregard
    private String hash;
    private String messageId;

    public Message(String sender, String recipient, String content, String flag) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.flag = flag;
        this.hash = generateHash();
        this.messageId = UUID.randomUUID().toString();
    }

    private String generateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = sender + recipient + content + flag;
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Message hashing failed", e);
        }
    }

    // Getters
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public String getFlag() { return flag; }
    public String getHash() { return hash; }
    public String getMessageId() { return messageId; }
}