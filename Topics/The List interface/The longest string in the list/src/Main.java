import java.util.*;

public class Main {

    static void changeList(List<String> list) {
        // write your code here
        int max = Integer.MIN_VALUE;
        String result = "";
        for (String i:
             list)
        {
            int len = i.length();
            if (len > max)
            {
                max = len;
                result = i;
            }
        }

        for (int i = 0; i < list.size(); i++)
        {
            list.set(i, result);
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        List<String> lst = Arrays.asList(s.split(" "));
        changeList(lst);
        lst.forEach(e -> System.out.print(e + " "));
    }
}