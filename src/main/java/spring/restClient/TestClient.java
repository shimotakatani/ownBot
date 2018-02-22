package spring.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.dto.MessageDto;

import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

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



//            HttpGet request = new HttpGet(uri);
//            HttpResponse response = client.execute(request);
//            if (response.getStatusLine().getStatusCode() == 200){
//
////            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//                HttpEntity httpEntity = response.getEntity();
//                String apiOutput = EntityUtils.toString(httpEntity);
//
//                System.out.println(apiOutput);
//
//                JAXBContext jaxbContext = JAXBContext.newInstance(MessageDto.class);
//                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//                MessageDto messageDto = (MessageDto) jaxbUnmarshaller.unmarshal(new StringReader(apiOutput));

//            String line = "";
//            if ((line = rd.readLine()) != null) {
//                System.out.println(line);
//                return user;
//            }
            return response.toString();
//            }
//            return "ПРОБЛЕМА";


//        } catch (IOException e){
//            return "ОШИБКА";
//        } catch (JAXBException e) {
//            e.printStackTrace();
//            return "ОШИБКА";
//        }
    }

    public String getSocialHostAddress(){
        return "http://" + "localhost" + ":" + "8090";
    }
}
