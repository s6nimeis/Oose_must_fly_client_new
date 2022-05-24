import java.io.IOException;
import java.util.Scanner;

public class RestAPIClient {

    public static void startUp() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ebay_wishcom_not_a_scam_edition");
        System.out.println("Do you want to login or register a new account");
        System.out.println("(Type 'login' or 'register' now.)");
        String loginOrregister = scanner.nextLine();
        if("login".equalsIgnoreCase(loginOrregister)){
            loginaccount();
        }else if("register".equalsIgnoreCase(loginOrregister)){
            registeraccount();
        }else{
            System.out.println("You must have entered wrong keywords. Please try again.");
            startUp();
        }
        scanner.close();
    }
    public static void loginaccount() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username");
        String username = scanner.nextLine();

        System.out.println("Please enter your password");
        String password = scanner.nextLine();

        login(username, password);

        scanner.close();

        System.out.println("Thanks for using ebay_wishcom_not_a_scam_edition");

    }
    public static void registeraccount() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a username you want to register");
        String username = scanner.nextLine();

        System.out.println("Please enter a paswort to set");
        String password = scanner.nextLine();

        register(username, password);

    }
}