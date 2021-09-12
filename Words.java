// Google Guava, Interface Multimap: https://bit.ly/2qWXeyW
// Google Guava, Download https://bit.ly/2Hpo2PA
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;


public enum Words {
    /*ENUM arguments are set up as (arg1 = part of speech array, arg2 = all definitions)
     *     **arg2, null values in array are a break point so we know to switch to next part of speech for entry into hashmap, look @constructor*/
    arrow("noun", new String[]{"Here is one arrow: <IMG> -=>> </IMG>"}),
    book(new String[]{"noun", "verb"}, new String[][]{{"A set of pages.", "A written work published in printed or electronic form."}, {"To arrange for someone to have a seat on a plane.", "To arrange something on a particular date."}}),
    distinct(new String[]{"adjective", "adverb", "noun"}, new String[][]{{"Familiar. Worked in Java.", "Unique. No duplicates. Clearly different or of a different kind."}, {"Uniquely. Written \"distinctly\"."}, {"A keyword in this project.", "A keyword in this assignment.   ", "A keyword in this assignment.  ", "An advanced search option.", "Distinct is a parameter in this assignment."}}),
    placeholder(new String[]{"adjective", "adverb", "conjunction", "interjection", "noun", "preposition", "pronoun", "verb"}, new String[][]{{"To be updated...", "To be updated... "}, {"To be updated..."}, {"To be updated..."}, {"To be updated..."}, {"To be updated...", "To be updated... ", "To be updated...  "}, {"To be updated..."}, {"To be updated..."}, {"To be updated..."}}),
    reverse(new String[]{"adjective", "noun", "verb"}, new String[][]{{"Opposite to usual or previous arrangement.", "On back side."}, {"To be updated...   ", "To be updated...", "To be updated...  ", "To be updated... ", "The opposite.", "Change to opposite direction.", "A dictionary program's parameter."}, {"Turn something inside out", "To be updated. . .", "To be updated. . .  ", "Revoke ruling.", "Go back", "Change something to opposite."}});


    //======DATA FIELDS=====//
    private TreeMultimap<String, String> mmap;
    //  ^^ note** using TreeMultiMap instead of MultiMap because TreeMultiMap stores entries in their natural order, helps ease of user output

    /* Note, some entries in the words will have the same exact definitions. HOWEVER, TreeMultiMap does NOT support same key AND same definitions.
    *  So I have added extra spaces to some of the matching definitions, therefore allowing all definitions to be technically unique #hack  */


    //=====CONSTRUCTORS======//
    private Words(String key, String[] values) //words w/ 1 part of speech
    {
        mmap = TreeMultimap.create();
        for (String s : values) {
            mmap.put(key, s);
        }
    }

    private Words(String[] keys, String[][] values) //words w/ multiple parts of speech
    {
        mmap = TreeMultimap.create();
        int a = 0;
        for (String key : keys) {
            for (int b = 0; b < values[a].length; b++) {
                mmap.put(key, values[a][b]);
            }
            a++;
        }
    }


    //======METHODS - Console_Printing=====//
    public void printGuide(String partOfSpeech, boolean pos, boolean reverse, boolean distinct)
    {
        if (pos) getPartOfSpeech(partOfSpeech, reverse, distinct);
        else if (distinct) getDistinct(partOfSpeech, false, reverse);
        else if (reverse) getReverse(partOfSpeech, false, false);
        else   //printing all
        {
            System.out.println("\t|");
            for (Map.Entry<String, String> x : mmap.entries())
            {                            //this == enum name with capital first letter
                System.out.println("\t" + this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1) + " [" + x.getKey() + "] : " + x.getValue());
            }
            System.out.println("\t|");
        }
    }

    public void getPartOfSpeech(String partOfSpeech, boolean reverse, boolean distinct)
    {
        if (distinct) getDistinct(partOfSpeech, true, reverse); //forward printing to distinct method
        else if (reverse) getReverse(partOfSpeech, true, false);  //forward printing to reverse method
        else //printing part of speech, all definitions
        {
            if(mmap.get(partOfSpeech).size() == 0)  //if word does not have requested part of speech
            {
                DictionaryBackEnd dict = new DictionaryBackEnd();
                System.out.println(DictionaryBackEnd.getNotFoundMessage() + "\n" + DictionaryBackEnd.getHowToMessage());
                return;
            }
            System.out.println("\t|");
            mmap.get(partOfSpeech).forEach((entry) ->   System.out.println("\t" + this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1) + " [" + partOfSpeech + "] : " + entry));
            System.out.println("\t|");
        }
    }

    public void getDistinct(String partOfSpeech, boolean pos, boolean reverse)
    {
        if (reverse) getReverse(partOfSpeech, pos, true);  //forward printing to reverse method
        else if (pos)
        {
           NavigableSet<String> navMap = mmap.get(partOfSpeech);
           ArrayList<String> noDuplicateDefinitions = new ArrayList<>();   //to store all entries of navMap, so we can iterate through
           Iterator it = navMap.iterator();

            //adding all entries into arrayList
            while(it.hasNext())
            {
                noDuplicateDefinitions.add((String)it.next());
            }

            System.out.println("\t|");
            getDistinctPartWithPartOfSpeechOnly(noDuplicateDefinitions, partOfSpeech);
            System.out.println("\t|");
        }
        else   //printing all non-duplicate definitions
        {
            ArrayList<String> temp;

            System.out.println("\t|");
            for (String x : mmap.keySet())
            {
                temp = new ArrayList<>(mmap.get(x));
                getDistinctPartWithPartOfSpeechOnly( temp, x);
            }
            System.out.println("\t|");
        }
    }

    private void getDistinctPartWithPartOfSpeechOnly(ArrayList<String> noDuplicateDefinitions, String partOfSpeech)
    {
        int a, b;

        //tempPlaceHolder needed in order to ensure all values within for() -> if() loop are printed. w/o tempPlaceHolder, last String never gets printed
        noDuplicateDefinitions.add("tempPlaceHolder");

         /* iterating through arrayList to ensure duplicates do not print out, we need to ignore the spaces in order to compare Strings. Strings that that matches Strings,
          * may have a different number of spaces. For more info, see Notes above constructors */
        for(a=0, b=1; b < noDuplicateDefinitions.size() ; a++, b++)
        {
            if(!noDuplicateDefinitions.get(a).replaceAll("\\s", "").equals(noDuplicateDefinitions.get(b).replaceAll("\\s", "")))
                 System.out.println("\t" + this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1) + " [" + partOfSpeech + "] : " + noDuplicateDefinitions.get(a));
        }
    }

    public void getReverse(String partOfSpeech, boolean pos, boolean distinct)
    {
        //need a whole new TreeMultiMap versus using pollLast for reversing because we need all keys to reverse as well
        TreeMultimap<String, String> reverseMMap = TreeMultimap.create(Ordering.natural().reversed(), Ordering.natural().reversed());
        reverseMMap.putAll(mmap);

        if(pos && distinct)
        {
            NavigableSet<String> navMap = reverseMMap.get(partOfSpeech);
            ArrayList<String> noDuplicateDefinitions = new ArrayList<>();   //to store all entries of navMap, so we can iterate through
            Iterator it = navMap.iterator();

            //adding all entries into arrayList
            while(it.hasNext())
            {
                noDuplicateDefinitions.add((String)it.next());
            }

            System.out.println("\t|");
            getDistinctPartWithPartOfSpeechOnly(noDuplicateDefinitions, partOfSpeech);
            System.out.println("\t|");
        }
        else if (pos) //printing part Of Speech reversed
        {
            NavigableSet<String> navMap = reverseMMap.get(partOfSpeech);
            Iterator it = navMap.iterator();

            System.out.println("\t|");
            navMap.forEach((entries) -> System.out.println("\t" + this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1) + " [" + partOfSpeech + "] : " + entries));
            System.out.println("\t|");
        }
        else if(distinct)
        {
            ArrayList<String> temp;

            System.out.println("\t|");
            for (String x : reverseMMap.keySet())
            {
                temp = new ArrayList<>(reverseMMap.get(x));
                getDistinctPartWithPartOfSpeechOnly( temp, x);
            }
            System.out.println("\t|");
        }
        else  //printing all reverse
        {
            System.out.println("\t|");
            for (Map.Entry<String, String> x : reverseMMap.entries())
            {                            //this == enum name with capital first letter
                System.out.println("\t" + this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1) + " [" + x.getKey() + "] : " + x.getValue());
            }
            System.out.println("\t|");
        }
    }
}

