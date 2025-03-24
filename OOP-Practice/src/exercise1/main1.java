package exercise1;

public class main1 {
    public static void main(String[] args) {
        System.out.println("Аргументи командного рядка:");

        if (args.length == 0) {
            System.out.println("Немає аргументів.");
        } else {
            for (int i = 0; i < args.length; i++) {
                System.out.println("Аргумент " + (i + 1) + ": " + args[i]);
            }
        }
    }
}
