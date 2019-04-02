package waddup;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PostService {

    public String postDrink() {

        final String uri = "http://localhost:8080/drink/create";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Drink> entity = new HttpEntity<>(new Drink(5,"Beck's",12.50), headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        return result.toString();
    }
}
