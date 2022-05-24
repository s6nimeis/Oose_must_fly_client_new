import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    public static void login (String name, String password) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/auth/login" + name).openConnection();

        connection.setRequestMethod("POST");

        String postData = "username=" + URLEncoder.encode(name, StandardCharsets.UTF_8);
        postData += "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);


        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        InputStreamReader rw = new InputStreamReader(connection.getInputStream());


        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Login was successful");
        } else if (responseCode == 403) {
            System.out.println("Invalid credentials");
        }
    }
    public static void register (String name, String password) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/auth/register" + name).openConnection();

        connection.setRequestMethod("POST");

        String postData = "username=" + URLEncoder.encode(name, StandardCharsets.UTF_8);
        postData += "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);


        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        BufferedReader buf = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));
        String output;
        System.out.println("Output from Server .... \n");
        while ((output = buf.readLine()) != null) {
            System.out.println(output);
        }
        buf.close();

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            System.out.println("registration was successful");
        }
        else if(responseCode == 403){
            System.out.println("User already exists");
        }
    }

    public static void main(String[] args) throws IOException {
        startUp();
    }

}

