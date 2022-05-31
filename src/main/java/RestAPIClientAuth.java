import com.google.gson.Gson;
import gson.price.Userprice;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class RestAPIClientAuth {

    private static final String MAIN_URL = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api";


    final String url7 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/inv check
    final String url8 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/deposit --> double amount
    final String url9 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/withdraw --> double amount
    final String url10 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; //{token}/balance check
    final String url11 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user/"; ///{token}/offers/show check
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

        System.out.println("Option(1): 'logout'");

        System.out.println("Option(2): 'Market ressources'");

        System.out.println("Option(3): 'User account ressources'");
        String option = scanner.nextLine();

        if("logout".equalsIgnoreCase(option)){
            logout();

        }else if("Market ressources".equalsIgnoreCase(option)){
            System.out.println("You have selected 'Market Ressources'");
            System.out.println("Please type in the name of further options to move on");
            System.out.println("Option(1)Type: 'offers' for showing the offers from the marketplace\n");
            System.out.println("Option(2)Type: 'price' for listing the prices for products\n");
            System.out.println("Option(3)Type: 'accept' for accepting an product from the market\n");
            System.out.println("Option(3)Type: 'go back' for going back to the main menu\n");
            option = scanner.nextLine();
            if ("offers".equalsIgnoreCase(option)) {
                String url = MAIN_URL + "/market" + "/query" + "/offers";
                RestAPIClientRessource.getresponse(url);
            }else if ("price".equalsIgnoreCase(option)) {
                String url = MAIN_URL + "/market" + "/query" + "/values";
                String response = RestAPIClientRessource.getresponse(url);


                System.out.println(response);
                Gson gson = new Gson();
                Userprice[] userprice = gson.fromJson(response, Userprice[].class );

                System.out.println(userprice);

            }else if ("accept".equalsIgnoreCase(option)) {
                RestAPIClientRessource.accept_offer();
            }else if("go back".equalsIgnoreCase(option)){
                online();
            }
        }else if("User account ressources".equalsIgnoreCase(option)){
                System.out.println("You have selected 'User account ressources'");
                System.out.println("Please type in the name of further options to move on");
                System.out.println("Option(1)Type: 'inventory' for checking your inventory\n");
                System.out.println("Option(2)Type: 'deposit' to deposit money in your account\n");
                System.out.println("Option(3)Type: 'withdraw' to withdraw money from your account\n");
                System.out.println("Option(4)Type: 'balance' for checking your balance\n");
                System.out.println("Option(5)Type: 'user offers' for checking your offers\n");
                System.out.println("Option(6)Type: 'create' to create an offer\n");
                System.out.println("Option(7)Type: 'delete' to delete an offer\n");
                System.out.println("Option(3)Type: 'go back' for going back to the main menu\n");

            option = scanner.nextLine();

                if ("inventory".equalsIgnoreCase(option)) {
                String url = MAIN_URL + "/user/" + data + "/inv";
                String response =  RestAPIClientRessource.getresponse(url);
                System.out.println(response);

                } else if ("deposit".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data + "/deposit";  //needs different url
                    RestAPIClientUser.deposit_withdraw(url, option);

                } else if ("wtihdraw".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data + "/withdraw";  //needs different url
                    RestAPIClientUser.deposit_withdraw(url, option);

                } else if ("balance".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data + "/balance";
                    RestAPIClientRessource.getresponse(url);

                } else if ("user offers".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data + "/offers" + "/show";
                    RestAPIClientRessource.getresponse(url);

                } else if ("create".equalsIgnoreCase(option)) {
                    RestAPIClientUser.createoffer();

                } else if ("delete".equalsIgnoreCase(option)){
                    RestAPIClientUser.deleteoffer();

                }else if("go back".equalsIgnoreCase(option)){
                    online();
                }
        } else{
            System.out.println("You must have entered wrong keywords. Please try again.\n");
            online();
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


        String result = authentication(url, postData);
        JSONObject obj = new JSONObject(result);
        String token = obj.getString("token");

        System.out.println("Your personal token is: " + token + "\n");

        data = token;


        System.out.println("Thanks for using ebay_wishcom_not_a_scam_edition\n");
        online();

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
        startUp();

    }
    public static void logout() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/auth/logout/"+ data).openConnection();
        connection.setDoOutput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while((line = in.readLine()) != null){
            response.append(line).append("\n");
        }
        String result = response.toString();
        System.out.println(result);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Logout was successful");   //wird warum auch immer nicht geprintet
        } else if (responseCode == 401) {
            System.out.println("Invalid token");
        }

    }

    public static String authentication(String url, String postData) throws IOException {
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
        String result = response.toString();


        in.close();


        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Request was successful\n");
            return result;

        }
        else{
            System.out.println("Something didnt work as expected.");
            startUp();
        }

        return result;
    }


    public static void main(String[] args) throws IOException {
        startUp();
    }

}


