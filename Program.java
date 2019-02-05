package bqb;

import java.io.IOException;

public class Program {
    public static void main(String[] main)  throws IOException {
        FileHandler.checkForFilesAndCreateIfNone();
        AccountsManager.updateAll();
        GUIHandler.createWindow("Quote Bot", "Bean.png"); // Start button on GUI handles other start ups
    }
}
