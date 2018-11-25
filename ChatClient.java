import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;

final class ChatClient {
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;

    private final String server;
    private final String username;
    private final int port;



    private ChatClient(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    public Socket getSocket(){
        return socket;
    }

    /*
     * This starts the Chat Client
     */
    private boolean start() {
        // Create a socket
        try {
            socket = new Socket(server, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create your input and output streams
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // This thread will listen from the server for incoming messages
        Runnable r = new ListenFromServer();
        Thread t = new Thread(r);
        t.start();

        // After starting, send the clients username to the server.
        try {
            sOutput.writeObject(username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    /*
     * This method is used to send a ChatMessage Objects to the server
     */
    private void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * To start the Client use one of the following command
     * > java ChatClient
     * > java ChatClient username
     * > java ChatClient username portNumber
     * > java ChatClient username portNumber serverAddress
     *
     * If the portNumber is not specified 1500 should be used
     * If the serverAddress is not specified "localHost" should be used
     * If the username is not specified "Anonymous" should be used
     */
    public static void main(String[] args) {
        // Get proper arguments and override default
        ChatClient client;
        if (args.length == 2) {
            client = new ChatClient("localhost", 1500, "Anonymous");
        } else if (args.length == 3) {
            client = new ChatClient("localhost", 1500, args[2]);
        } else if (args.length == 4) {
            client = new ChatClient("localhost", Integer.parseInt(args[3]), args[2]);
        } else if (args.length == 5) {
            client = new ChatClient(args[4], Integer.parseInt(args[3]), args[2]);
        } else {
            client = new ChatClient("localhost", 1500, "Ay yo something went wrong");
        }
        // Create your client and start it
        client.start();

        // Send an empty message to the server
        ObjectInputStream inputStream = client.sInput;
        String input1 = inputStream.toString();

        if (input1.equals("/logout")) {
            try {
                // closes ObjectInputStream, ObjectOutputStream and the Socket
                client.sInput.close();
                client.sOutput.close();
                client.socket.close();
                client.sendMessage(new ChatMessage(input1, 1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            client.sendMessage(new ChatMessage(input1, 0));
        }
    }


    /*
     * This is a private class inside of the ChatClient
     * It will be responsible for listening for messages from the ChatServer.
     * ie: When other clients send messages, the server will relay it to the client.
     */
    private final class ListenFromServer implements Runnable {
        public void run() {
            while (true) {

                try {
                    String msg = (String) sInput.readObject();
                    System.out.print(msg);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}