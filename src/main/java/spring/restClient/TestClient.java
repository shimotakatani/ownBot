package spring.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * create time 22.02.2018
 *
 * @author nponosov
 */
@Named
public class TestClient {

    @Value("${social.Host.Url}")
    private String socialUrl;

    @Value("${social.Host.port}")
    private String socialPort;

    private HttpClient client = new DefaultHttpClient();

    private ObjectMapper mapper = new ObjectMapper();

    public void initTestClient() {

    }

    public String getTestMessage(String message){
        try {
            String uri = getSocialHostAddress() + "/test";
            if (message != null) {
                uri += "?message=" + message;
            }
            HttpGet request = new HttpGet(uri);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200){

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            if ((line = rd.readLine()) != null) {
                System.out.println(line);
                return line;
            }
            return line;
            }
            return "ПРОБЛЕМА";


        } catch (IOException e){
            return "ОШИБКА";
        }
    }

    public String getSocialHostAddress(){
        return "http://" + "localhost" + ":" + "8090";
    }
}
