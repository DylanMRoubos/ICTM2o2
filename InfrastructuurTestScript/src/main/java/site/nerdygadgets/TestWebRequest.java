package site.nerdygadgets;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TestWebRequest {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.nerdy-gadgets.site/status.php");
            HttpsURLConnection con;
            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            int retry = 0;
            while (status != 200 && retry < 6) {
                con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                status = con.getResponseCode();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            System.out.println(content);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
