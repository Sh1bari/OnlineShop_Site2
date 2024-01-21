package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.exceptions.ApplicationNotFoundException;
import com.example.onlineshop_site2.exceptions.GoodNotFoundException;
import com.example.onlineshop_site2.exceptions.UserNotFoundException;
import com.example.onlineshop_site2.models.dtos.requests.CreateApplicationReq;
import com.example.onlineshop_site2.models.dtos.responses.ApplicationRes;
import com.example.onlineshop_site2.models.dtos.responses.BillUrlDto;
import com.example.onlineshop_site2.models.entities.Bill;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.models.enums.ApplicationStatus;
import com.example.onlineshop_site2.repositories.ApplicationRepo;
import com.example.onlineshop_site2.repositories.BillRepo;
import com.example.onlineshop_site2.repositories.GoodRepo;
import com.example.onlineshop_site2.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepo billRepo;

    private final UserRepository userRepo;
    private final RestTemplate restTemplate;
    private final ApplicationService applicationService;
    private final ApplicationRepo applicationRepo;
    private final GoodRepo goodRepo;

    public void checkBills(){
        List<Bill> bills = billRepo.findAllByStatus("pending");
        bills.forEach(o->{
            ResponseEntity<String> json = getRequest("https://api.yookassa.ru/v3/payments/" + o.getBillId(),
                    "307408",
                    "test_lhaIMhIJzkF71VMu7DL7mhX6KfU57n-viiYGc5lE4a4");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode;
            try {
                jsonNode = objectMapper.readTree(json.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            JsonNode rowsNode = jsonNode.get("status");
            if(rowsNode.asText().equals("succeeded")){
                o.setStatus("succeeded");
                o.getApplication().setStatus(ApplicationStatus.FREE);
                billRepo.save(o);
            } else if (rowsNode.asText().equals("pending")) {

            } else {
                o.setStatus(rowsNode.asText());
                billRepo.save(o);
            }
        });
    }

    public BillUrlDto createBill(String email, CreateApplicationReq req) throws JsonProcessingException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException(email));
        ApplicationRes res = applicationService.createApplication(email, req);
        AtomicReference<Integer> amount = new AtomicReference<>(0);
        res.getItems().forEach(o->{
            Good good = goodRepo.findById(o.getGoodId())
                    .orElseThrow(()-> new GoodNotFoundException(o.getGoodId()));
            amount.set((int) (amount.get() + (good.getCost() * o.getAmount())));
        });
        Bill bill = new Bill();
        bill.setApplication(applicationRepo.findById(res.getId())
                .orElseThrow(()->new ApplicationNotFoundException(res.getId())));
        bill.setCurrency("RUB");
        bill.setAmount(String.valueOf(amount.get()));
        bill.setStatus("pending");
        billRepo.save(bill);
        ResponseEntity<String> json = postRequest("https://api.yookassa.ru/v3/payments",
                String.valueOf(bill.getId()),
                body(String.valueOf(amount.get())),
                "307408",
                "test_lhaIMhIJzkF71VMu7DL7mhX6KfU57n-viiYGc5lE4a4");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json.getBody());

        // Извлекаем только массив "rows"
        JsonNode rowsNode = jsonNode.get("id");
        bill.setBillId(rowsNode.asText());
        billRepo.save(bill);

        JsonNode rowsNodePath = jsonNode.get("confirmation").get("confirmation_url");
        return new BillUrlDto(json.toString());
    }

    private String body(String amount){
        return "{\n" +
                "    \"amount\": {\n" +
                "      \"value\": \""+ amount +"\",\n" +
                "      \"currency\": \"RUB\"\n" +
                "    },\n" +
                "    \"capture\": true,\n" +
                "    \"confirmation\": {\n" +
                "      \"type\": \"redirect\",\n" +
                "      \"return_url\": \"https://aeternumeleven.com/\"\n" +
                "    },\n" +
                "    \"description\": \"Заказ на сайте aeternumeleven.com\"\n" +
                "}";
    }

    private static String encodeBase64(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public ResponseEntity<String> getRequest(String apiUrl, String credentialsAccount, String credentialsToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Connection", "keep-alive");
        headers.set("Authorization", "Basic " + encodeBase64(credentialsAccount + ":" + credentialsToken));

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        try {
            // Используйте exchange, чтобы получить ResponseEntity<String>
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
            return responseEntity;
        } catch (HttpClientErrorException e) {
            // Поймаем исключение HttpClientErrorException и вернем его информацию
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }

    public ResponseEntity<String> postRequest(String apiUrl, String key, String jsonData, String credentialsAccount, String credentialsToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Connection", "keep-alive");
        headers.set("Idempotence-Key", key);
        headers.set("Authorization", "Basic " + encodeBase64(credentialsAccount + ":" + credentialsToken));

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);

        try {
            // Используйте exchange, чтобы получить ResponseEntity<String>
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            return responseEntity;
        } catch (HttpClientErrorException e) {
            // Поймаем исключение HttpClientErrorException и вернем его информацию
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }
}
