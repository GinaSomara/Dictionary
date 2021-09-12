import java.util.ArrayList;

public class DictionaryBackEnd
{
    //====== DATA FIELDS ======//
    static private ArrayList<Words> enumWordList = new ArrayList<>();
    static private ArrayList<String> notFoundWords = new ArrayList<>(); //storing for future updates, can add words not found in system
    static private String ordinalNumeral; //used for error messages in .displayErrorMessage()
    static final private String howToMessage = "\t|\n\tPARAMETER HOW-TO, please enter:\n\t1. A search key -then 2. An optional part of speech -then\n\t3. An optional 'distinct' -then 4. An optional 'reverse'\n\t|",
                                notFoundMessage = "\t|\n\t<NOT FOUND> To be considered for the next release. Thank you.\n\t|";

    //====== STATIC METHODS ======//

    //===INTRO METHODS===//

    static public boolean loadData()
    {
        try {
            for (Words word : Words.values())
            {
                enumWordList.add(word);    //ArrayList is not used other than here, keeping for add future release updates
            }
            return true;
        } catch(Exception e) {
            System.out.println("Error Loading Data . . ."); return false;
        }
    }

    //===USER_INPUT CHECKING METHODS===//

    static public boolean checkInput(String[] userInput)  //checking full user input
    {
        //ensuring main search word does not contain any non-letters
        if(!userInput[0].matches("[a-zA-Z]+") || userInput.length > 4) {
            System.out.println(howToMessage); return false;
        }

        //checking to see if word is in enum dictionary
        try {
            Words.valueOf(userInput[0]);
        } catch(IllegalArgumentException e) {
            System.out.println(notFoundMessage + "\n" +howToMessage);
            return false;
        }

        //checking part of speech, reverse, distinct [IF applicable]
        switch(userInput.length)
        {
            //case 1 ->  //no further action. verified above
            case 2 ->    //must be either part of speech / reverse / distinct
                    {
                        if(!(checkPartOfSpeech(userInput[1]) || checkReverse(userInput[1]) || checkDistinct(userInput[1])))
                        { ordinalNumeral = "2nd";
                          displayErrorMessage(userInput[1], 2);
                        }
                        break;
                    }
            case 3->
                    {   //second word must either be part of speech or distinct
                        if(!(checkPartOfSpeech(userInput[1]) || checkDistinct(userInput[1])))
                        {
                            ordinalNumeral = "2nd";
                            displayErrorMessage(userInput[1], 2);
                        }
                        //third word can only be reverse or distinct
                        if(!(checkReverse(userInput[2]) || checkDistinct(userInput[2])))
                        {
                            ordinalNumeral = "3rd";
                            displayErrorMessage(userInput[2], 3);
                        }
                        break;
                    }
            case 4 ->
                    {
                        if(!(checkPartOfSpeech(userInput[1]) || checkReverse(userInput[1]) || checkDistinct(userInput[1])))  //second word must be part of speech, unless other words are errors
                        {
                            ordinalNumeral = "2nd";
                            displayErrorMessage(userInput[1], 2);
                        }
                        if(!(checkDistinct(userInput[2]) || checkReverse(userInput[2])))     //third word must be distinct, or reverse if fourth word is an error
                        {
                            ordinalNumeral = "3rd";
                            displayErrorMessage(userInput[2], 3);
                        }
                        if(!checkReverse(userInput[3]))      //fourth word must be reverse
                        {
                            ordinalNumeral = "4th";
                            displayErrorMessage(userInput[3], 4);
                        }
                    }
        }
        return true;
    }

    static private boolean checkPartOfSpeech(String word)
    {
        if(!(word.equals("noun") || word.equals("adjective") || word.equals("adverb") || word.equals("conjunction") || word.equals("interjection") || word.equals("preposition") || word.equals("pronoun") || word.equals("verb")))
            return false;
        else return true;
    }


    static private boolean checkReverse(String word)
    {
        if(!word.equals("reverse"))
            return false;
        else return true;
    }

    static private boolean checkDistinct(String word)
    {
        if(!word.equals("distinct"))
            return false;
        else return true;
    }


    //===CONSOLE DISPLAYING METHODS===//

    static public void displayDefinitions(String[] userInput)
    {
        switch (userInput.length)
        {
            case 1 -> { Words.valueOf(userInput[0]).printGuide(null, false, false, false); }
            case 2 -> { Words.valueOf(userInput[0]).printGuide(userInput[1], checkPartOfSpeech(userInput[1]), checkReverse(userInput[1]), checkDistinct(userInput[1])); }
            case 3 ->
                    {   //second word must either be part of speech or distinct
                        if(checkPartOfSpeech(userInput[1]))
                            Words.valueOf(userInput[0]).printGuide(userInput[1], true, checkReverse(userInput[2]), checkDistinct(userInput[2]));
                        else if(checkDistinct(userInput[1]))
                            Words.valueOf(userInput[0]).printGuide(null, false, checkReverse(userInput[2]), checkDistinct(userInput[2]));
                    }
            case 4 ->    //must be in order of [Searched Word, partOfSpeech, Distinct, Reverse]  <-3rd/4th word may be an error word - need to check  - IF user input errors, then must account for that
                    {
                        if(checkPartOfSpeech(userInput[1]) && checkDistinct(userInput[2]) && checkReverse(userInput[3]))    //how it should be without error
                            Words.valueOf(userInput[0]).printGuide(userInput[1], checkPartOfSpeech(userInput[1]), checkDistinct(userInput[2]), checkReverse(userInput[3]));
                        else if (!checkPartOfSpeech(userInput[1]))    //error in user input for second word, must either be distinct or reverse
                        {
                            if(checkDistinct(userInput[1]) && checkReverse(userInput[2]))
                                Words.valueOf(userInput[0]).printGuide("", false, checkDistinct(userInput[1]), checkReverse(userInput[2]));  //error w/ fourth word
                            else if(checkReverse(userInput[1]))  //reverse ok, third and fourth word error
                                Words.valueOf(userInput[0]).printGuide("", false, true, false);
                            else  if(checkDistinct(userInput[1]))   //distinct ok, third and fourht word error
                                Words.valueOf(userInput[0]).printGuide("", false, false, true);
                            else //only 1st word is valid. 2nd/3rd/4th all errors, printing all
                                Words.valueOf(userInput[0]).printGuide("", false, false, false);
                        }
                        else if (checkPartOfSpeech(userInput[1]))   //error with  3rd/4th words
                        {
                            if(checkDistinct(userInput[2]))
                                Words.valueOf(userInput[0]).printGuide(userInput[1], true, false, true);
                            else //only 1st word is valid. 2nd/3rd/4th all errors, printing all
                            Words.valueOf(userInput[0]).printGuide(userInput[1], true, false, false);
                        }
                        else    //only part of speech, 3rd/4th word errors
                            Words.valueOf(userInput[0]).printGuide(userInput[1], true, false, false);

                    }
        }
    }

    static private void displayErrorMessage(String invalidParameter, int invalidPosition)
    {
        //not using new switch statement because we want fall thorugh for case 2/3

        System.out.println("\t|");
        switch (invalidPosition) //invalid position = whichever array element is invalid
        {
            case 2 :
                        System.out.println("\t<The entered " + ordinalNumeral + " parameter '" + invalidParameter + "' is NOT a part of speech.>");
                        //fall through to case 3 - print all relevant error messages
            case 3 :
                    {
                        System.out.println("\t<The entered " + ordinalNumeral + " parameter '" + invalidParameter + "' is NOT 'distinct'.>" +
                                           "\n\t<The entered " + ordinalNumeral + " parameter '" + invalidParameter + "' is NOT 'reverse'.>" +
                                           "\n\t<The entered " + ordinalNumeral + " parameter '" + invalidParameter + "' was disregarded.>");
                        //since fall through w/ case 2, need to account for both cases error message
                        if(invalidPosition == 2) System.out.println("\t<The " + ordinalNumeral + " parameter should be a part of speech or 'distinct' or 'reverse'.>");
                        if(invalidPosition == 3) System.out.println("\t<The " + ordinalNumeral + " parameter should be 'distinct' or 'reverse'.>");
                        break;
                    }
            case 4 :
                     {
                         System.out.println("\t<The entered 4th parameter '" + invalidParameter + "' is NOT 'reverse'.>\n" +
                                            "\t<The entered 4th parameter '" + invalidParameter + "' was disregarded.>\n" +
                                            "\t<The 4th parameter should be 'reverse'.>");
                     }
            //case 4:    if array.length > length 4 , handled previously in .checkInput()  -> no need for default
        }
        System.out.println("\t|");
    }


    //===GETTERS===//

    static public String getNotFoundMessage()
    {
        return notFoundMessage;
    }

    static public String getHowToMessage()
    {
        return howToMessage;
    }


}