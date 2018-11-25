
import java.io.Serializable;


final class ChatMessage implements Serializable {
    private static final long serialVersionUID = 6898543889087L;

    private String message;
    private int typeOfMessage;

    // Here is where you should implement the chat message object.
    // Variables, Constructors, Methods, etc.

    public ChatMessage(String message, int typeOfMessage) {
        this.message = message;
        this.typeOfMessage = typeOfMessage;
    }

    public String getMessage() {
        return message;
    }

    public int getTypeOfMessage() {
        return typeOfMessage;
    }



}