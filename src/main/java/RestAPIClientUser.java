import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;




public class RestAPIClientUser {
    private static final String MAIN_URL = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/user";

    /**
     * method for creating an offer, asks for paramter(id, amount and price) and sends to userressource() method
     * @throws IOException
     */
    public static void createoffer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String url = MAIN_URL +"/" +RestAPIClientAuth.data +"/offers" +"/create";
        System.out.println("Please type in the id for the product you want to create");
        long id = scanner.nextLong();

        System.out.println("Please type in the amount you want to sell");
        int amount = scanner.nextInt();

        System.out.println("Please type in the price for what you want to sell your product per unit");
        double price_per_unit = scanner.nextDouble();

        String postData = "id=" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        postData += "&amount=" + URLEncoder.encode(String.valueOf(amount), StandardCharsets.UTF_8);
        postData += "&price=" + URLEncoder.encode(String.valueOf(price_per_unit), StandardCharsets.UTF_8);

        userressource(url, postData);
    }
    public static void updateoffer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type in the id for the product you want to create");
        long id = scanner.nextLong();

        System.out.println("Please type in the new price for what you want to sell your product per unit");
        double new_price_per_unit = scanner.nextDouble();

        String url = MAIN_URL +"/" +RestAPIClientAuth.data +"/offers" +"/update/" + id;
        String postData = "id=" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        postData += "&price=" + URLEncoder.encode(String.valueOf(new_price_per_unit), StandardCharsets.UTF_8);

        userressource(url, postData);

    }


    /**
     * differs between the option deposit and withdraw and asks the user to input an amount to either desposit or withdraw
     * sends input data to userressource() method
     * @param url
     * @param option
     * @throws IOException
     */

    public static void deposit_withdraw(String url, String option) throws IOException {
        double amount = 0;
        Scanner scanner = new Scanner(System.in);
        if ("2".equalsIgnoreCase(option)) {
            System.out.println("Please type in the amount of money you want to deposit");
            amount = Double.parseDouble(scanner.nextLine());
        } else if ("3".equalsIgnoreCase(option)) {
            System.out.println("Please type in the amount of money you want to withdraw");
            amount = Double.parseDouble(scanner.nextLine());
        }

        String postData = "amount=" + URLEncoder.encode(String.valueOf(amount), StandardCharsets.UTF_8);

        userressource(url, postData );

    }

    /**
     * posts data to given url and checks for responce code
     * @param url
     * @param postData
     * @throws IOException
     */

    public static void userressource(String url, String postData) throws IOException {    //posts user ressources data
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
            System.out.println("invalid token or price");
        }
        else if(responseCode == 403){
            System.out.println("Error precheck failed");
        }
        else {
            System.out.println("Something didnt work as expected.");
        }
    }

    /**
     * connection for delete request
     * @throws IOException
     */
    public static void deleteoffer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type in the id for the product you want to delete");
        long id = scanner.nextLong();
        String url = MAIN_URL + "/" +RestAPIClientAuth.data + "/offers" + "/delete/" + id;
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
            System.out.println("Offer not found");
        }
        else {
            System.out.println("Something didnt work as expected.");
        }
    }
}
