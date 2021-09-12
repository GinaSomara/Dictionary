import java.util.Scanner;

public class DictionaryDriver
{
    //===DATA FIELDS - STATIC===//
    static private int searchCounter = 1;
    static private Scanner sc = new Scanner(System.in);
    static private String[] splitArray;
    static private final String ANSI_RESET = "\u001B[0m",
                                ANSI_YELLOW = "\u001B[33m",
                                ANSI_BLUE = "\u001B[34m";

    //MAIN
    public static void main(String[] args) throws InterruptedException
    {
        //===GREETING===//
        System.out.println("\n\n! Loading data. . .");
        //Adding enums to ArrayList for program searching
        if(DictionaryBackEnd.loadData()) System.out.println("! Loading completed. . .\n");
        else System.out.println("Program Terminating");

        displayGreeting();

        //RUN//
        do{
            //prompting user input
            System.out.print("Search [" + searchCounter + "]: ");
            searchCounter++;

            //split userInput into an array for analysis of user requests
            splitArray = sc.nextLine().toLowerCase().split(" "); //after every space, splitting word into

            if(splitArray[0].equals("!q")) continue;
            //checking user input, if false, reprompt user input
            if(!DictionaryBackEnd.checkInput(splitArray)) continue;

            DictionaryBackEnd.displayDefinitions(splitArray);

        }while(!splitArray[0].equals("!q"));

        System.out.println("\n-----THANK YOU-----");
    }

    private static void displayGreeting() throws InterruptedException
    {
        Thread.sleep(2000);
        System.out.println(ANSI_YELLOW + "===== DICTIONARY 340 JAVA =====" + ANSI_RESET +"\n----- Keywords: 19" + "\n----- Definitions: 61\n\nThe " +
                "four words you can search are:" + ANSI_BLUE + "  arrow " + ANSI_RESET + " | " + ANSI_BLUE + " book " + ANSI_RESET + " | " + ANSI_BLUE +
                " distinct " + ANSI_RESET + " | " + ANSI_BLUE + " placeholder " + ANSI_RESET + " | " + ANSI_BLUE + " reverse " + ANSI_RESET + " ||-> " + ANSI_BLUE +
                " !help " + ANSI_RESET + " (for more information)\n");
        Thread.sleep(2000);

        System.out.println(DictionaryBackEnd.getHowToMessage() + "\n" + ANSI_YELLOW + "*** Order of parameters DOES MATTER ***" + ANSI_RESET);

        Thread.sleep(2000);

        System.out.println("Three examples would be: ");
        Thread.sleep(1000);
        System.out.println("\n 1) A normal search\n Search [1]: "+ ANSI_BLUE +"revERse" + ANSI_RESET +"\n\t|\n" +
                "\tReverse [adjective] : On back side.\n" +
                "\tReverse [adjective] : Opposite to usual or previous arrangement.\n" +
                "\tReverse [noun] : A dictionary program's parameter.\n" +
                "\tReverse [noun] : Change to opposite direction.\n" +
                "\tReverse [noun] : The opposite.\n" +
                "\tReverse [noun] : To be updated...\n" +
                "\tReverse [noun] : To be updated... \n" +
                "\tReverse [noun] : To be updated...  \n" +
                "\tReverse [noun] : To be updated...   \n" +
                "\tReverse [verb] : Change something to opposite.\n" +
                "\tReverse [verb] : Go back\n" +
                "\tReverse [verb] : Revoke ruling.\n" +
                "\tReverse [verb] : To be updated. . .\n" +
                "\tReverse [verb] : To be updated. . .  \n" +
                "\tReverse [verb] : Turn something inside out\n" +
                "\t|" +
                "\n 2) A search for the reversed list of defintions" +
                "\n Search [2]:" + ANSI_BLUE + "revERse reverse"  + ANSI_RESET +"\n\t|\n" +
                "\tReverse [verb] : Turn something inside out\n" +
                "\tReverse [verb] : To be updated. . .  \n" +
                "\tReverse [verb] : To be updated. . .\n" +
                "\tReverse [verb] : Revoke ruling.\n" +
                "\tReverse [verb] : Go back\n" +
                "\tReverse [verb] : Change something to opposite.\n" +
                "\tReverse [noun] : To be updated...   \n" +
                "\tReverse [noun] : To be updated...  \n" +
                "\tReverse [noun] : To be updated... \n" +
                "\tReverse [noun] : To be updated...\n" +
                "\tReverse [noun] : The opposite.\n" +
                "\tReverse [noun] : Change to opposite direction.\n" +
                "\tReverse [noun] : A dictionary program's parameter.\n" +
                "\tReverse [adjective] : Opposite to usual or previous arrangement.\n" +
                "\tReverse [adjective] : On back side.\n" +
                "\t|" +
                "\n 3) A search for the unique, non-duplicate, definitions\n" +
                " Search [3]:" + ANSI_BLUE + "REVERSE distinct"  + ANSI_RESET +"\n\t|\n" +
                "\tReverse [adjective] : On back side.\n" +
                "\tReverse [adjective] : Opposite to usual or previous arrangement.\n" +
                "\tReverse [noun] : A dictionary program's parameter.\n" +
                "\tReverse [noun] : Change to opposite direction.\n" +
                "\tReverse [noun] : The opposite.\n" +
                "\tReverse [noun] : To be updated...   \n" +
                "\tReverse [verb] : Change something to opposite.\n" +
                "\tReverse [verb] : Go back\n" +
                "\tReverse [verb] : Revoke ruling.\n" +
                "\tReverse [verb] : To be updated. . .  \n" +
                "\tReverse [verb] : Turn something inside out\n" +
                "\t|");
    }
}

