import com.google.gson.*;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class RestAPIClientAuth {

    private static final String MAIN_URL = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api";
    public static String data;

    /**
     * opening method to ask user for logging in or registration
     * @throws IOException
     */
    public static void startUp() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ebay_wishcom_not_a_scam_edition");
        System.out.println("Do you want to login or register a new account?");
        System.out.println("(Type 'login' or 'register')");
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

    /**
     * if login was succesful the user has further options to move on
     * @throws IOException
     */
    public static void online() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("From here you can choose from the listed options for this marketplace.\n");
        System.out.println("Just type the number for further options");
        System.out.println("Option(1): '1' if you would like to log out");
        System.out.println("Option(2): '2'for market ressources");
        System.out.println("Option(3): '3'for user market ressources");
        String option = scanner.nextLine();

        if("1".equalsIgnoreCase(option)){
            logout();

        }else if("2".equalsIgnoreCase(option)){
            System.out.println("You have selected 'Market Ressources'");
            System.out.println("Please type in the number for further options\n");
            System.out.println("Option(1)Type: '1' for showing the offers from the marketplace\n");
            System.out.println("Option(2)Type: '2' for listing the prices for products\n");
            System.out.println("Option(3)Type: '3' for accepting an product from the market\n");
            System.out.println("Option(3)Type: '4' for going back to the main menu\n");
            option = scanner.nextLine();
            if ("1".equalsIgnoreCase(option)) {
                String url = MAIN_URL + "/market" + "/query" + "/offers";
                String response = RestAPIClientRessource.getresponse(url);
                JsonArray jsonObject =  JsonParser.parseString(response).getAsJsonArray();
                for(int i = 0; i < jsonObject.size(); i++){
                    JsonObject element = jsonObject.get(i).getAsJsonObject();
                    JsonObject itm = element.get("itm").getAsJsonObject();
                    System.out.println("Name: "+ itm.get("name").getAsString());
                    System.out.println("Amount: "+ element.get("amount").getAsInt());
                    System.out.println("Price: "+ element.get("price").getAsDouble());
                    System.out.println("Offer id: "+ element.get("id").getAsLong());
                    System.out.println("Product id: "+ itm.get("id").getAsInt());
                    System.out.print(("\n"));
                }
                online();
            }else if ("2".equalsIgnoreCase(option)) {
                String url = MAIN_URL + "/market" + "/query" + "/values";
                String response = RestAPIClientRessource.getresponse(url);
                JsonArray jsonObject =  JsonParser.parseString(response).getAsJsonArray();
                for(int i = 0; i < jsonObject.size(); i++){
                    JsonObject element = jsonObject.get(i).getAsJsonObject();
                    System.out.println("Name: "+ element.get("name").getAsString());
                    System.out.println("Product id: "+ element.get("id").getAsLong());
                    System.out.println("Price: "+ element.get("price").getAsDouble());
                    System.out.print(("\n"));
                }
                online();
            }else if ("3".equalsIgnoreCase(option)) {
                RestAPIClientRessource.accept_offer();
                online();
            }else if("4".equalsIgnoreCase(option)){
                online();
            }else{
                online();
            }

        }else if("3".equalsIgnoreCase(option)){
                System.out.println("You have selected 'User account ressources'");
                System.out.println("Please type in the number for further options");
                System.out.println("Option(1)Type: '1' for checking your inventory\n");
                System.out.println("Option(2)Type: '2' to deposit money in your account\n");
                System.out.println("Option(3)Type: '3' to withdraw money from your account\n");
                System.out.println("Option(4)Type: '4' for checking your balance\n");
                System.out.println("Option(5)Type: '5' for checking your offers\n");
                System.out.println("Option(6)Type: '6' to create an offer\n");
                System.out.println("Option(7)Type: '7' to delete an offer\n");
                System.out.println("Option(7)Type: '8' to updating an offer\n");
                System.out.println("Option(3)Type: '9' for going back to the main menu\n");

            option = scanner.nextLine();

                if ("1".equalsIgnoreCase(option)) {
                String url = MAIN_URL + "/user/" +data+ "/inv";
                String response =  RestAPIClientRessource.getresponse(url);
                JsonArray jsonObject =  JsonParser.parseString(response).getAsJsonArray();
                for(int i = 0; i < jsonObject.size(); i++){
                    JsonObject element = jsonObject.get(i).getAsJsonObject();
                    System.out.println("Name: "+ element.get("name").getAsString());
                    System.out.println("Product id: "+ element.get("id").getAsLong());
                    System.out.println("Amount: "+ element.get("amount").getAsInt());
                    System.out.print(("\n"));
                    }
                online();

                } else if ("2".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data+ "/deposit";  //needs different url
                    RestAPIClientUser.deposit_withdraw(url, option);

                } else if ("3".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data+ "/withdraw";  //needs different url
                    RestAPIClientUser.deposit_withdraw(url, option);

                } else if ("4".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data+ "/balance";
                    String response = RestAPIClientRessource.getresponse(url);
                    JSONObject obj = new JSONObject(response);
                    String balance = obj.getString("balance");
                    System.out.println(balance);

                    online();

                } else if ("5".equalsIgnoreCase(option)) {
                    String url = MAIN_URL + "/user/" +data+ "/offers" + "/show";
                    String response = RestAPIClientRessource.getresponse(url);
                    JsonArray jsonObject =  JsonParser.parseString(response).getAsJsonArray();
                    for(int i = 0; i < jsonObject.size(); i++) {
                        JsonObject element = jsonObject.get(i).getAsJsonObject();
                        JsonObject itm = element.get("itm").getAsJsonObject();
                        System.out.println("Name: " + itm.get("name").getAsString());
                        System.out.println("Amount: " + element.get("amount").getAsInt());
                        System.out.println("Price: " + element.get("price").getAsDouble());
                        System.out.println("Offer id: " + element.get("id").getAsLong());
                        System.out.println("Product id: " + itm.get("id").getAsInt());
                        System.out.print(("\n"));

                    }
                    online();

                } else if ("6".equalsIgnoreCase(option)) {
                    RestAPIClientUser.createoffer();
                    online();

                } else if ("7".equalsIgnoreCase(option)){
                    RestAPIClientUser.deleteoffer();
                    online();

                }else if("8".equalsIgnoreCase(option)){
                    RestAPIClientUser.updateoffer();
                    online();
                }
                else if("9".equalsIgnoreCase(option)){
                    online();
                }
                else{
                    System.out.println("You must have entered wrong keywords. Please try again.\n");
                    online();
                }
        } else{
            System.out.println("You must have entered wrong key. Please try again.\n");
            online();
        }


    }

    /**
     * login mehthod which asks the user for an email and password. The server response from the authentication method is an individual token which is needed later
     * @throws IOException
     */
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

    /**
     * registration method which asks the user for an email and password to begin with. The data is send to the authentication method
     * @throws IOException
     */
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

    /**
     * method for logging out, sends the token via url to the server
     * @throws IOException
     */
    public static void logout() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/auth/logout/"+ data).openConnection();
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Logout was successful");
        } else if (responseCode == 401) {
            System.out.println("Invalid token");
        }
    }

    /**
     * posts authentication data to server
     * @param url
     * @param postData
     * @return
     * @throws IOException
     */
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


