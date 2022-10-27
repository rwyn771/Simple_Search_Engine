
class Problem {
    public static void main(String[] args) {
        for (int j = 0; j < args.length; j+=2)
        {
            System.out.println(args[j] + "=" + args[j +1]);
        }
    }
}