import java.util.*;

class MapUtils
{

    public static SortedMap<String, Integer> wordCount(String[] strings)
    {
        // write your code here
        SortedMap<String, Integer> map = new TreeMap<>();

        for (int i = 0; i < strings.length; i++)
        {
            int counter = 0;
            for (int j = i; j < strings.length; j++)
            {
                if (strings[i].equals(strings[j]))
                    counter++;
            }
            if (!map.containsKey(strings[i]))
                map.put(strings[i], counter);
        }
        return map;
    }

    public static void printMap(Map<String, Integer> map)
    {
        // write your code here
        for (var entry : map.entrySet())
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }
}

    /* Do not change code below */
    public class Main
    {
        public static void main(String[] args)
        {
            Scanner scanner = new Scanner(System.in);
            String[] words = scanner.nextLine().split(" ");
            MapUtils.printMap(MapUtils.wordCount(words));
        }
    }