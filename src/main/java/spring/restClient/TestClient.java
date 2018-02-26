package spring.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.dto.MessageDto;

import javax.inject.Named;

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
        //try {
            String uri = getSocialHostAddress() + "/test";
            if (message != null) {
                uri += "?message=" + message;
            }


            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(uri);

            RestTemplate restTemplate = new RestTemplate();
            MessageDto response = restTemplate.getForObject(builder.toUriString(), MessageDto.class);

            return response.toString();
    }

    public String getGameMessage(){
        String uri = getSocialHostAddress() + "/game";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(uri);

        RestTemplate restTemplate = new RestTemplate();
        MessageDto response = restTemplate.getForObject(builder.toUriString(), MessageDto.class);

        return response.toString();
    }

    public String getSocialHostAddress(){
        return "http://" + "localhost" + ":" + "8090";
    }
}
