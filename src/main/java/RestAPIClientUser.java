import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;




public class RestAPIClientUser {
    private static final String MAIN_URL = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user";

    public static void createoffer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String url = MAIN_URL + "/" + RestAPIClientAuth.data + "/offers" + "/create";
        System.out.println("Please type in the id for the product you want to create");
        long id = Long.parseLong(scanner.nextLine());

        System.out.println("Please type in the amount you want to sell");
        int amount = Integer.parseInt(scanner.nextLine());

        System.out.println("Please type in the price for what you want to sell your product per unit");
        double price_per_unit = Double.parseDouble(scanner.nextLine());

        String postData = "id=" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        postData += "&amount=" + URLEncoder.encode(String.valueOf(amount), StandardCharsets.UTF_8);
        postData += "&price_per_unit=" + URLEncoder.encode(String.valueOf(price_per_unit), StandardCharsets.UTF_8);

        userressource(url, postData);

    }

    public static void deposit_withdraw(String url, String option) throws IOException {
        double amount = 0;
        Scanner scanner = new Scanner(System.in);
        if ("deposit".equalsIgnoreCase(option)) {
            System.out.println("Please type in the amount of money you want to deposit");
            amount = Double.parseDouble(scanner.nextLine());
        } else if ("withdraw".equalsIgnoreCase(option)) {
            System.out.println("Please type in the amount of money you want to withdraw");
            amount = Double.parseDouble(scanner.nextLine());
        }

        String postData = "amount=" + URLEncoder.encode(String.valueOf(amount), StandardCharsets.UTF_8);

        userressource(url, postData);

    }

    public static void userressource(String url, String postData) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        connection.disconnect();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Request was successful\n");
            RestAPIClientAuth.online();
        }else if(responseCode == 500){
            System.out.println("Error on serverside");
        }
        else if(responseCode == 401){
            System.out.println("invalid token");
        }
        else if(responseCode == 403){
            System.out.println("Error precheck failed");
        }
        else {
            System.out.println("Something didnt work as expected.");
        }
    }
    public static void deleteoffer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        long id = Long.parseLong(scanner.nextLine());
        String url = MAIN_URL + "/user/" +RestAPIClientAuth.data + "/offers" + "delete" + id;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Request was successful\n");
            RestAPIClientAuth.online();
        }else if(responseCode == 401){
            System.out.println("invalid token");
        }else if(responseCode == 500){
            System.out.println("Error on serverside");
        }else if(responseCode == 404){
            System.out.println("offer not found");
        }
        else {
            System.out.println("Something didnt work as expected.");
        }
    }
}
