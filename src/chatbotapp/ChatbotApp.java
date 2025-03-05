import java.util.Random;
import java.util.Scanner;

class Chatbot {
    private String name;
    private StringBuffer chatHistory;
    private Random random;

    public Chatbot(String name) {
        this.name = name;
        this.chatHistory = new StringBuffer();
        this.random = new Random();
    }

   public String processMessage(String message) {
    if (message.trim().isEmpty()) {
        return name.concat(": Please enter a message.");
    }

    message = filterOffensiveWords(message);
    message = parseNumbers(message);

    System.out.println(message);

    chatHistory.append("User: ").append(message).append("\n");

    if (message.equalsIgnoreCase("hello")) {
        chatHistory.append(name).append(": Hi there! \n");
        return name.concat(": Hi there! ");
    }

    String response = generateResponse();
    chatHistory.append(name).append(": ").append(response).append("\n");

    return name.concat(": ") + response;
}

    private String parseNumbers(String message) {
        String[] words = message.split(" ");
        StringBuilder formattedMessage = new StringBuilder();

        for (String word : words) {
            try {
                if (word.contains(".")) {
                    formattedMessage.append(String.format("%.2f", Double.parseDouble(word))).append(" ");
                } else {
                    formattedMessage.append(Integer.parseInt(word)).append(" ");
                }
            } catch (NumberFormatException e) {
                formattedMessage.append(word).append(" ");
            }
        }
        return formattedMessage.toString().trim();
    }

    private String generateResponse() {
        String[] responses = {
            "That is interesting!",
            "Can you tell me more?",
            "I see what you mean.",
            "That's a good point!",
            "Let's discuss further.",
            "I understand. ",
            "Hmm, tell me more!",
            "That's cool! What else?",
            "I'm here to listen.",
            "Let's explore that idea!"
        };
        return responses[(int) (Math.random() * responses.length)];
    }

    private String filterOffensiveWords(String message) {
    String[] badWords = {
        "idiot", "stupid", "dumb", "ugly", "fool",
        "hate", "loser", "trash", "jerk", "darn", "fuck", "fuck you", "fuck u",
        "moron", "crap", "shut up", "nasty", "gross"
    };

    for (String badWord : badWords) {
        message = message.replaceAll("(?i)\\b" + badWord + "\\b\\s*[.,!?:;]*", "****");
    }
    return message;
}

    public void modifyChatHistory() {
        if (chatHistory.length() > 10) {
            chatHistory.deleteCharAt(10);
        }
        if (chatHistory.length() > 5) {
            chatHistory.setCharAt(5, '*');
        }
    }

    public void reverseChatHistory() {
        chatHistory.reverse();
    }

    public void displayChatHistory() {
        System.out.println("\nChat History:");
        System.out.println(chatHistory.toString());
    }
}

public class ChatbotApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Chatbot chatbot = new Chatbot("ChatBot");

        System.out.println("Start chatting with ChatBot! Type 'exit' to stop.");
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.println(chatbot.processMessage(userInput));
        }

        chatbot.modifyChatHistory();
        chatbot.reverseChatHistory();
        chatbot.displayChatHistory();

        scanner.close();
    }
}
