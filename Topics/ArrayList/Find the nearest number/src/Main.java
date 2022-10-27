import java.util.*;

        public class Main {
            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);

                String[] items = scanner.nextLine().split("\\s+");
                List<Integer> numbers = new ArrayList<>();

                for (String item : items) {
                    numbers.add(Integer.parseInt(item));
                }

                int n = scanner.nextInt();
                ArrayList<Integer> result = new ArrayList<>();

                int minDist = Integer.MAX_VALUE;
                for (int i : numbers) {
                    if (Math.abs(i - n) < minDist) {
                        minDist = Math.abs(i - n);
                        result.clear();
                        result.add(i);
                    } else if (Math.abs(i - n) == minDist) {
                        result.add(i);
                    }
                }

                Collections.sort(result);

                for (int item : result) {
                    System.out.print(item + " ");
                }
            }
        }
