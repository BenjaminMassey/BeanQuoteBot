package bqb;

import java.io.IOException;

public class Program {
    public static void main(String[] main)  throws IOException {
        FileHandler.checkForFilesAndCreateIfNone();
        AccountsManager.updateAll();
        try {
            TwitchChat.initialize();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
