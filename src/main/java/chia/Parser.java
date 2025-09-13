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
        assert input != null : "Input cannot be null";
        assert !input.trim().isEmpty() : "The input cannot be empty";

        String[] words = input.split(" ");
        return words[0];
    }

    /**
     * Extracts the details from user input after the command word.
     * Returns everything after the first space, or empty string if no details.
     */
    public static String getDetails(String input) {
        assert input != null : "Input cannot be null!!!";

        int spaceIndex = input.indexOf(" ");
        if (spaceIndex == -1) {
            return "";
        }
        return input.substring(spaceIndex + 1);
    }

    public static int getIndex(String input) {
        assert input != null : "Input cannot be null";
        assert input.split(" ").length >= 2 : "Input must contain at least 2 words to extract index";

        String[] words = input.split(" ");
        return Integer.parseInt(words[1]) - 1;
    }
}
