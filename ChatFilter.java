import java.io.*;
import java.util.ArrayList;

public class ChatFilter {
    private String badWordsFileName;

    public ChatFilter(String badWordsFileName) {
        this.badWordsFileName = badWordsFileName;
    }

    public String filter(String msg) {
        String returnMessage= "";
        File file;
        File messageFile;
        FileReader fileReader;
        FileReader messageFileReader;
        BufferedReader bufferedReader;
        BufferedReader messageBufferedReader;

        try {
            String filter = "";
            // creates a buffered reader by for the bad words text file and message
            file = new File(badWordsFileName);
            messageFile = new File(msg);
            fileReader = new FileReader(file);
            messageFileReader = new FileReader(messageFile);
            bufferedReader = new BufferedReader(fileReader);
            messageBufferedReader = new BufferedReader(messageFileReader);

            ArrayList<String> badWords = new ArrayList<>();
            ArrayList<String> messageWords = new ArrayList<>();

            while (bufferedReader.readLine() != null) {
                badWords.add(bufferedReader.readLine());
            }

            while(messageBufferedReader.readLine() != null) {
                messageWords.add(messageBufferedReader.readLine());
            }

            for (int i = 0; i < badWords.toArray().length; i++) {
                for (int j = 0; j < messageWords.toArray().length; j++) {
                    if (messageWords.get(j).equals(badWords.get(i))) {
                        for (int k = 0; k < messageWords.get(j).length(); k++) {
                            filter += "*";
                        }
                        messageWords.set(j, filter);
                    }
                }

            }



            for (int i = 0; i < messageWords.toArray().length; i++) {
                returnMessage += messageWords.get(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMessage;
    }
}