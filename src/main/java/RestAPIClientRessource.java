import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RestAPIClientRessource {

    private static final String MAIN_URL = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market";

    /**
     * method for accepting an offer from the marketplace, asks for paramter (id and amount) and posts data to given url
     * @throws IOException
     */
    public static void accept_offer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Before you can accept an offer please type in the id of the offer");
        long offer_id = scanner.nextLong();

        System.out.println("Please also enter the needed amount");
        int amount = scanner.nextInt();

        String url = MAIN_URL + "/" + RestAPIClientAuth.data + "/" + offer_id + "/accept";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        String amountStr = "amount=" + URLEncoder.encode(String.valueOf(amount), StandardCharsets.UTF_8);;
        wr.write(amountStr);
        wr.flush();

        connection.disconnect();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Success!");
            RestAPIClientAuth.online();
        }else if (responseCode == 401) {
            System.out.println("Invalid token");
            accept_offer();
        }else if (responseCode == 500) {
            System.out.println("There has been an error on server side");
        }else if (responseCode == 403) {
            System.out.println("Error precheck has failed");
        }else {
            System.out.println("Something didnt work out as planned");
        }

    }

    /**
     * gets any kind of response for different methods like (offers, prices and inventory)
     * @param url
     * @return
     * @throws IOException
     */
    public static String getresponse(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();  //opens connection to url

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line).append("\n");
        }
        in.close();

        String result = response.toString();


        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Request was successful");
            return result;
        } else {
            System.out.println("Something didnt work as expected");
        }

        return result;
    }
}

