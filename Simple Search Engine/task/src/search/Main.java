package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main
{
    // Main method. Passes the second command line argument to the search method in the search class
    public static void main(String[] args)
    {
        if (args.length == 2)
        {
            if ("--data".equals(args[0]))
            {
                Search search = new Search();
                search.search(args[1]);
            }
        }
    }
}
//--------------------------------------------------------------------------------------------------------------------------
class Search
{
    //ArrayList representing the data line by line read from the text file.
    private ArrayList<String> data = new ArrayList<>();

    //Hashmap of indexed data from ArrayList for quick search retrieval
    private HashMap<String, List<Integer>> map = new HashMap<>();

    /*reads the input file. adds it to an ArrayList, indexes the contents of
    the ArrayList to a Hashmap and then presents a menu to the user.*/
    public void search(String inFile)
    {
        File file = new File(inFile);

        try
        {
            Scanner s = new Scanner(file);

            while (s.hasNextLine())
            {
                data.add(s.nextLine());
            }
            s.close();

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error! File not found.");
        }
        index();
        menu();
    }

    //creates an inverted index utilising data from an ArrayList
    public void index()
    {
        int lineNum = 0;

        for (String lineContent:
                data)
        {
            String[] array = (lineContent.toLowerCase().split("\\s+"));

            for (String word : array)
            {
                if (map.containsKey(word.trim()))
                    map.get(word.trim()).add(lineNum);
                else
                    map.put(word.trim(), new ArrayList<>(List.of(lineNum)));
            }
            lineNum++;
        }
    }

    //creates an instance of the desired search strategy, performs a search, and out puts the result.
    public void find()
    {
        SearchStrategy strategy = determineSearchStrategy();

        System.out.println("\nEnter a name or email to search all suitable people.");

        String query = UserInput.getStringInput().trim().toLowerCase();

        SortedSet<Integer> set = strategy.searchStrategy(query, map);

        if (Objects.equals(set, null))
            System.out.println("No matching people found.");
        else
        {
            System.out.println(set.size() + " persons found:");

            for (int line :
                    set)
            {
                System.out.println(data.get(line));
            }
        }
        System.out.println();
    }

    //creates an instance of the desired search strategy
    public SearchStrategy determineSearchStrategy()
    {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");

        while (true)
        {
            String str = UserInput.getStringInput().trim().toUpperCase();
            switch (str)
            {
                case "ANY" ->
                {
                    return new AnyMatchStrategy();
                }
                case "ALL" ->
                {
                    return new AllMatchStrategy();
                }
                case "NONE" ->
                {
                    return new NoneMatchStrategy();
                }
                default -> System.out.println("Invalid input. Try again");
            }
        }
    }

    //prints out put for all the data in the file
    public void print()
    {
        System.out.println("\n=== List of people ===");

        for (String temp :
                data)
        {
            System.out.println(temp);
        }

        System.out.println();
    }

    //main menu for user to interact with when searching
    public void menu()
    {
        while (true)
        {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            String key = UserInput.getStringInput();

            switch (key)
            {
                case "1":
                    find();
                    break;
                case "2":
                    print();
                    break;
                case "0":
                    exit();
                    return;
                default:
                    System.out.println("\nIncorrect option! Try again.\n");
                    break;
            }
        }
    }

    //exits the program
    public void exit()
    {
        System.out.println("\nBye!");
    }

}
//-----------------------------------------------------------------------------------------------------------------------
interface SearchStrategy
{
    //abstract method. invokes the concrete method of appropriate subclass.
    SortedSet<Integer> searchStrategy(String query, HashMap<String, List<Integer>> map);

}
//------------------------------------------------------------------------------------------------------------------------
class AnyMatchStrategy implements SearchStrategy
{
    /*returns a set of search results if any keyword in the users query
    matches the dataset on file (broad output)*/
    public SortedSet<Integer> searchStrategy(String query, HashMap<String, List<Integer>> map)
    {
        String[] queryArray = query.toLowerCase().split("\\s");
        SortedSet <Integer> set = new TreeSet<>();

        for (String word : queryArray)
        {
            if (map.containsKey(word))
            {
                set.addAll(map.get(word));
            }
        }
        return set;
    }

}
//------------------------------------------------------------------------------------------------------------------------
class AllMatchStrategy implements SearchStrategy
{
    //returns results that are an exact match between query and dataset (specific output)
    public SortedSet<Integer> searchStrategy(String query, HashMap<String, List<Integer>> map)
    {
        SortedSet<Integer> set = new TreeSet<>();
        String[] queryArray = query.toLowerCase().split("\\s");

        if (map.containsKey(queryArray[1]))
        {
            set.addAll(map.get(queryArray[1]));

            for (String word :
                    queryArray)
            {
               if(!Objects.equals(set, null))
                   set.retainAll(map.get(word));
            }
        }
        return set;
    }
}
//-----------------------------------------------------------------------------------------------------------------------

class NoneMatchStrategy implements SearchStrategy
{
    //returns all results that dont match the search query. Why would anyone want this feature? Idk man
    public SortedSet<Integer> searchStrategy(String query, HashMap<String, List<Integer>> map)
    {
        String[] queryArray = query.toLowerCase().split("\\s");
        SortedSet <Integer> set = new TreeSet<>();

        for (String key:
             map.keySet())
        {
            set.addAll(map.get(key));
        }

        for (String word:
             queryArray)
        {
            if (map.containsKey(word))
                set.removeAll(map.get(word));
        }

        return set;
    }
}
//-------------------------------------------------------------------------------------------------------------------------
class UserInput
{
    //gets string input from the user and returns the string
    public static String getStringInput()
    {
        String msg;
        Scanner s = new Scanner(System.in);

        while (true)
        {
            msg = s.nextLine();
            if (msg.trim().isBlank())
            {
                System.out.println("Invalid input. Try again.");
            } else
            {
                msg = msg.trim();
                break;
            }
        }
        return msg;
    }

    //gets integer input from the user and returns an integer
    public int getIntegerInput()
    {
        int input;
        Scanner s = new Scanner(System.in);

        while (true)
        {
            try
            {
                input = Integer.parseInt(s.nextLine());
                break;
            } catch (Exception e)
            {
                System.out.println("Wrong Input!");
            }
        }
        return input;
    }
}