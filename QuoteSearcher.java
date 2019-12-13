package bqb;

import java.util.ArrayList;

public class QuoteSearcher {



    public static ArrayList searchThroughQuote(String chatMessage) {

        String searchTerm = chatMessage.substring(14);
        System.out.println(searchTerm);

        boolean didIfStatement = true;
        int ifCounter = 0;
        int quotePartition = 0;
        ArrayList<Integer> quoteNumbers = new ArrayList<>();

        int fileLength = 0;

        try {
            fileLength = FileHandler.getFileLength("quotes");
        }
        catch(Exception e) {
           System.err.println("Not able to find file length: " + e);
        }




        for(int i= 0; i<fileLength - 1; i++) {
            String quoteLine = "";

            try {
                quoteLine = FileHandler.readFromFile("quotes", i);
            }
            catch(Exception e) {
                System.err.println("Not able to read quotes from file: " + e);
            }

            for(int j = 0; j<quoteLine.length(); j++) {


                String letter = String.valueOf(quoteLine.charAt(j));

                if(letter == "\"" && didIfStatement) {
                    quotePartition = j;
                    if(ifCounter < 1) {
                        ifCounter = 1;
                    } else {
                        didIfStatement = false;
                    }
                }


            }
            System.out.println(quotePartition);
            String quote = quoteLine.substring(quotePartition);
            System.out.println(quote);
            if(quote.contains(searchTerm)) {
                quoteNumbers.add(i);
            }
        }

        return quoteNumbers;


    }
}
