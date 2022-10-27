package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main
{
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
    private ArrayList<String> data = new ArrayList<>();
    private HashMap<String, List<Integer>> map = new HashMap<>();

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

    public void exit()
    {
        System.out.println("\nBye!");
    }

}
//-----------------------------------------------------------------------------------------------------------------------
interface SearchStrategy
{
    SortedSet<Integer> searchStrategy(String query, HashMap<String, List<Integer>> map);

}
//------------------------------------------------------------------------------------------------------------------------
class AnyMatchStrategy implements SearchStrategy
{
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