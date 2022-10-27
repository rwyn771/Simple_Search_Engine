class Problem {
    public static void main(String[] args) {

        int result = findIndex(args);
        System.out.println(result);
    }

    public static int findIndex(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals("test"))
                return i;
        }

        return -1;
    }
}