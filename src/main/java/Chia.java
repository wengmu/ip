import java.util.Scanner;

public class Chia {
    public static void main(String[] args) {
        String logo =
                          "   ____ _     _       \n"
                        + "  / ___| |__ (_) __ _ \n"
                        + " | |   | '_ \\| |/ _` |\n"
                        + " | |___| | | | | (_| |\n"
                        + "  \\____|_| |_|_|\\__,_|\n";
        System.out.println("Hello Sir, I am \n" + logo);
        System.out.println("What can I help you with? \n");

        Scanner sc = new Scanner(System.in);

        while(true) {
            String user_input = sc.nextLine().trim();
            if (user_input.equals("bye")){
                System.out.println("Goodbye . Hope you have a great day ahead!\n");
                break;
            } else if(user_input.equals("sing")) {
                System.out.println("I get money, I'm a star\n" +
                        "Star, star, star, star, star, star");
            } else {
                System.out.println(user_input);
            }
        }
    }
}
