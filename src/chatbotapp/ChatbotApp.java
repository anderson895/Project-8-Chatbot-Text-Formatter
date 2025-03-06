import java.util.Random;
import java.util.Scanner;
import java.util.regex.*;

class Chatbot {
    private String name;
    private Random random;
    private StringBuffer chatHistory;

    public Chatbot(String name) {
        this.name = name;
        this.random = new Random();
        this.chatHistory = new StringBuffer();
    }

    public String processMessage(String message) {
        message = message.trim();
        chatHistory.append("You: ").append(message).append("\n");

        // Exit conditions: number > 9, 0, or equation
        if (isExitCondition(message)) {
            return "[end]";
        }

        // Convert informal text
        if (message.equalsIgnoreCase("mY b4D")) {
            return name.concat(": do you mean \"mY b4D\" as \"my bad\"?");
        }

        // Detect and filter offensive words
        String filteredMessage = filterOffensiveWords(message);
        if (!filteredMessage.equals(message)) {
            if (message.toLowerCase().startsWith("thank you!")) {
                return name.concat(": You're welcome! But your sentence, \"").concat(filteredMessage).concat("\", contains an offensive word. In conversing with me, you must be polite.");
            }
            return name.concat(": Yes, I can help you with that! But your sentence, \"").concat(filteredMessage).concat("\", contains an offensive word. In conversing with me, you must be polite.");
        }

        // Check for palindrome
        if (containsPalindrome(message)) {
            return name.concat(": Okay! Wowww. Coool. Your sentence, \"").concat(message).concat("\" contains a palindrome. So, if we reverse it, it's still the same, which is, ").concat(extractPalindrome(message)).concat(".");
        }

        // Convert numerical values
        message = convertNumbers(message);

        // Predefined responses
        if (message.matches("(?i).*\\b(hi|hello|hey)\\b.*")) {
            return name.concat(": Hello, user!");
        } else if (message.equalsIgnoreCase("Can you help me?")) {
            return name.concat(": How may I help you?");
        } else if (message.equalsIgnoreCase("Sorry")) {
            return name.concat(": It's okay!");
        } else if (message.equalsIgnoreCase("Thank you!")) {
            return name.concat(": You're welcome!");
        } else if (message.equalsIgnoreCase("Okay, byei!")) {
            return name.concat(": Okay, BYE?");
        } else if (message.equalsIgnoreCase("whatevar")) {
            return name.concat(": WHATEVER?");
        }

        return generateResponse();
    }

    private boolean isExitCondition(String message) {
        if (message.matches(".*[+\\-*/=].*")) {
            return true;
        }
        try {
            int num = Integer.parseInt(message);
            return num == 0 || num > 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean containsPalindrome(String message) {
        String[] words = message.split("\\s+");
        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            if (cleanWord.length() > 1 && cleanWord.equals(new StringBuilder(cleanWord).reverse().toString())) {
                return true;
            }
        }
        return false;
    }

    private String extractPalindrome(String message) {
        String[] words = message.split("\\s+");
        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            if (cleanWord.length() > 1 && cleanWord.equals(new StringBuilder(cleanWord).reverse().toString())) {
                return cleanWord;
            }
        }
        return "";
    }

    private String filterOffensiveWords(String message) {
        String[] badWords = { "sucks", "stupid", "idiot", "dumb", "ugly", "fool", "loser", "trash", "jerk" };
        StringBuffer filteredMessage = new StringBuffer(message);
        for (String badWord : badWords) {
            int index = filteredMessage.toString().toLowerCase().indexOf(badWord);
            while (index != -1) {
                filteredMessage.replace(index, index + badWord.length(), "***");
                index = filteredMessage.toString().toLowerCase().indexOf(badWord, index + 3);
            }
        }
        return filteredMessage.toString();
    }

    private String generateResponse() {
        String[] responses = {
            "That is interesting!",
            "Can you tell me more?",
            "I see what you mean.",
            "That's a good point!",
            "Let's discuss further.",
            "I understand.",
            "Hmm, tell me more!",
            "That's cool! What else?",
            "I'm here to listen.",
            "Let's explore that idea!"
        };
        return name.concat(": ").concat(responses[random.nextInt(responses.length)]);
    }

    private String convertNumbers(String message) {
        try {
            int intValue = Integer.parseInt(message);
            return String.valueOf(intValue);
        } catch (NumberFormatException e) {
            try {
                double doubleValue = Double.parseDouble(message);
                return String.valueOf(doubleValue);
            } catch (NumberFormatException ex) {
                return message;
            }
        }
    }
    
    public String getChatHistory() {
        return chatHistory.toString();
    }

    
}


public class ChatbotApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Chatbot chatbot = new Chatbot("Chatbot");

        System.out.println("Start chatting with Chatbot! Type 'exit' to stop.");
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            String response = chatbot.processMessage(userInput);
            System.out.println(response);

            if (response.equals("[end]")) {
                break;
            }
        }

        // Display chat history before exiting
        System.out.println("\nChat History:");
        System.out.println(chatbot.getChatHistory());

        scanner.close();
    }
}
