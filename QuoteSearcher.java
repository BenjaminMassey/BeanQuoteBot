package bqb;

import java.util.ArrayList;

public class QuoteSearcher {



    public static ArrayList searchThroughQuote(String chatMessage) {

        String searchTerm = chatMessage.substring(14); //cuts out !searchquotes
        // System.out.println(searchTerm); (debug)

        boolean didIfStatement = true;
        int ifCounter = 0;  
        int quotePartition = 0;
        ArrayList<Integer> quoteNumbers = new ArrayList<>();  //Init variables for later
        
        
        int fileLength = 0; 

        try {
            fileLength = FileHandler.getFileLength("quotes"); //Tries to get number of lines from quotes.txt
        }
        catch(Exception e) {
           System.err.println("Not able to find file length: " + e);
        }



        //goes through every line (quote)
        for(int i= 0; i<fileLength - 1; i++) {
            String quoteLine = ""; 

            try {
                quoteLine = FileHandler.readFromFile("quotes", i); //Tries to get quote from file
            }
            catch(Exception e) {
                System.err.println("Not able to read quotes from file: " + e);
            }
            // Goes through every character in quote
            for(int j = 0; j<quoteLine.length(); j++) {


                String letter = String.valueOf(quoteLine.charAt(j)); //Gets character (not char because it broke the thing for some reason
                
                
                //Goes through and checks for the second pair of double quotes
                if(letter == "\"" && didIfStatement) {
                    quotePartition = j;
                    if(ifCounter < 1) { 
                        ifCounter = 1;
                    } else {
                        didIfStatement = false;
                    }
                }


            }
            // System.out.println(quotePartition); (debug)
            String quote = quoteLine.substring(quotePartition); // separates quote from date
            // System.out.println(quote); (debug)
            if(quote.contains(searchTerm)) {
                quoteNumbers.add(i); //Searches the quote for the searchterm and adds the quote # to array list if true
            }
        }

        return quoteNumbers;


    }
}
