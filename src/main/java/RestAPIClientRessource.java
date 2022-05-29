import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestAPIClientRessource {

    final String url4 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market/query/offers";
    final String url5 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market/query/values";
    final String url6 = "http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market/"; //{token}/{id}/accept

    public static void message() {
        System.out.println(RestAPIClientAuth.data);
    }

    public static void offers_prices(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you would like to see all available offers please type 'offers'\n");
        System.out.println("You can also see the current item prices when typing 'price'\n");
        System.out.println("(Type 'login' or 'register' now.)");
        String offersOrprice = scanner.nextLine();
        if("offers".equalsIgnoreCase(offersOrprice)){
            loginaccount();
        }else if("price".equalsIgnoreCase(offersOrprice)){
            registeraccount();
        }else{
            System.out.println("You must have entered wrong keywords. Please try again.");

        }
    }



    public static void getoffers() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/OOSE_Ebay_WishCom_Edition-1.0-SNAPSHOT/api/market/query/offers").openConnection();

        connection.setRequestMethod("GET");

        BufferedReader buf = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = buf.readLine()) != null) {
            System.out.println(output);
        }
        buf.close();
        connection.disconnect();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Logout was successful");
        } else if (responseCode == 401) {
            System.out.println("Invalid token");
        }

    }
}

