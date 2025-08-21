import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
        List<String> work_list = new ArrayList<>();

        while(true) {
            String user_input = sc.nextLine().trim();
            if (user_input.equals("bye")){
                System.out.println("Goodbye . Hope you have a great day ahead!\n");
                break;
            } else if(user_input.equals("list")) {
                if (work_list.isEmpty()) {
                    System.out.println("There is no work to do. Congrats!");
                } else {
                    for(int i = 0; i < work_list.size(); i++) {
                        System.out.printf("%d. %s\n", i+1, work_list.get(i));
                    }
                }
            }else if(user_input.equals("sing")) {
                System.out.println("I get money, I'm a star\n" +
                        "Star, star, star, star, star, star");
            } else if(!user_input.isEmpty()){
                work_list.add(user_input);
                System.out.println("added: " + user_input);
            }
        }
    }
}
