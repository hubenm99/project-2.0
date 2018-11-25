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
        FileReader fileReader;
        BufferedReader bufferedReader;

        try {
            String filter = "";
            String returnMessage;
            // creates a buffered reader by for the bad words text file and message
            file = new File(badWordsFileName);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> badWords = new ArrayList<>();

            while (bufferedReader.readLine() != null) {
                badWords.add(bufferedReader.readLine());
            }

            for (int i = 0; i < msg.length; i++) {
                for (int j = 0; j < number of words in message; j++) {
                    if (word in message.equals(badWords.get(i))) {
                        for (int k = 0; k <length of word in message; k++) {
                            filter += "*";
                        }
                        returnMessage += filter;
                    } else {
                        filter = word in message;
                        returnMessage += filter
                    }
                filter = "";
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMessage;
    }
}
