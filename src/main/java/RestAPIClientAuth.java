import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RestAPIClientAuth {

    private static final String MAIN_URL = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api";

    final String url4 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market/query/offers";
    final String url5 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market/query/values";
    final String url6 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market/"; //{token}/{id}/accept

    final String url7 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/inv
    final String url8 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/deposit --> double amount
    final String url9 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/withdraw --> double amount
    final String url10 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/balance
    final String url11 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; ///{token}/offers/show
    final String url12 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; ///{token}/offers/delete/{id}
    final String url13 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; ///{token}/offers/create --> long id, int amount, double price_per_unit
    final String url14 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; ///{token}/offers/update/{id} --> double new_price_per_unit

    public static String data;


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

    }
    public static void online() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("From here you can choose from the listed options for this marketplace.\n");
        System.out.println("Just type the name of the option");
        System.out.println("(1)logout\n");
        String option = scanner.nextLine();
        if("logout".equalsIgnoreCase(option)){
            logout();
        }

    }
    public static void loginaccount() throws IOException {
        String url = MAIN_URL + "/auth" + "/login";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your Email address");
        String usermail = scanner.nextLine();

        System.out.println("Please enter your password");
        String password = scanner.nextLine();

        String postData = "mail=" + URLEncoder.encode(usermail, StandardCharsets.UTF_8);
        postData += "&pass=" + URLEncoder.encode(password, StandardCharsets.UTF_8);


        authentication(url, postData);

    }
    public static void registeraccount() throws IOException {
        String url = MAIN_URL + "/auth" + "/register";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter an email address you want to register");
        String usermail = scanner.nextLine();

        System.out.println("Please enter a paswort to set");
        String password = scanner.nextLine();

        String postData = "mail=" + URLEncoder.encode(usermail, StandardCharsets.UTF_8);
        postData += "&pass=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

        authentication(url, postData);

    }
    public static void logout() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/auth/logout/"+ data).openConnection();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Logout was successful");   //wird warum auch immer nicht geprintet
        } else if (responseCode == 401) {
            System.out.println("Invalid token");
        }

    }

    public static void authentication(String url, String postData) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while((line = in.readLine()) != null){
            response.append(line).append("\n");
        }
        in.close();

        data = response.toString();
        System.out.println(data);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Request was successful\n");
            System.out.println("Thanks for using ebay_wishcom_not_a_scam_edition\n");
            online();
        }
        else{
            System.out.println("Something didnt work as expected.");
        }

    }


    public static void post_method (String url, String postData) throws IOException {


        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while((line = in.readLine()) != null){
            response.append(line).append("\n");
        }
        in.close();

        response.toString();
        System.out.println(response);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Request was successful");
        }
        else{
            System.out.println("Something didnt work as expected.");
        }

    }

    public static void main(String[] args) throws IOException {
        startUp();
    }

}

