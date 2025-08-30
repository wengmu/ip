package chia;

public class Parser {
    public static String getCommand(String input) {
        String[] words = input.split(" ");
        return words[0];
    }

    public static String getDetails(String input) {
        int spaceIndex = input.indexOf(" ");
        if (spaceIndex == -1) {
            return "";
        }
        return input.substring(spaceIndex + 1);
    }

    public static int getIndex(String input) {
        String[] words = input.split(" ");
        return Integer.parseInt(words[1]) - 1;
    }
}
