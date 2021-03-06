package spring.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.dto.MessageDto;

import javax.inject.Named;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

    public MessageDto getGameMessage(Long chatId) {
//        String uri = getSocialHostAddress() + "/game";
//
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromUriString(uri);
//
//        RestTemplate restTemplate = new RestTemplate();
//        MessageDto response = restTemplate.getForObject(builder.toUriString(), MessageDto.class);
        return getMessageDto(getSocialHostAddress() + "/game?chatId=" + chatId);

    }

    public MessageDto getStartMessage(Long chatId){
        return getMessageDto(getSocialHostAddress() + "/start?chatId=" + chatId);
    }

    public String getNameMessage(Long chatId, String name){
        String uri = getSocialHostAddress() + "/name?chatId=" + chatId;
        if (name != null && name.length() > 0) uri = uri + "&name=" + name;
        return getMessageDto(uri).message;
    }

    public String getScoreMessage(Long chatId){
        return getMessageDto(getSocialHostAddress() + "/score?chatId=" + chatId).message;
    }

    private MessageDto getMessageDto(String uri){
        try {
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//            HttpGet httpGet = new HttpGet(uri);
//            CloseableHttpResponse response1 = httpclient.execute(httpGet);
//            String result = IOUtils.toString(response1.getEntity().getContent(), StandardCharsets.UTF_8);
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(result, MessageDto.class);

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
            RestTemplate restTemplate = new RestTemplate();
            MessageDto response = restTemplate.getForObject(builder.toUriString(), MessageDto.class);
            response.message = URLDecoder.decode(response.message, "UTF-8");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageDto();
        }
    }

    public String getSocialHostAddress(){
        return "http://" + "localhost" + ":" + "8090";
    }
}
