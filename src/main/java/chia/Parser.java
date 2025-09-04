package chia;

/**
 * class for parsing user input commands.
 */
public class Parser {
    /**
      * Extracts the command word from user input
      * Takes the first word of the input as the command
     */
    public static String getCommand(String input) {
        String[] words = input.split(" ");
        return words[0];
    }

    /**
     * Extracts the details from user input after the command word.
     * Returns everything after the first space, or empty string if no details.
     */
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
