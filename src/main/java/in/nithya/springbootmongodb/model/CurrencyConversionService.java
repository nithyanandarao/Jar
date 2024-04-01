package in.nithya.springbootmongodb.model;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConversionService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String convertCurrency(String baseCurrency, String targetCurrency, double amount) {
        String API_URL = "https://api.fxratesapi.com/latest";
        String url = API_URL + "?base=" + baseCurrency + "&symbols=" + targetCurrency;
        // Make a REST call to the external API
        String response = restTemplate.getForObject(url, String.class);
        // Parse the response and extract the exchange rate
        // Here you would need to parse the response JSON and extract the exchange rate
        // For simplicity, let's assume the response contains the exchange rate directly
        return "Exchange rate from " + baseCurrency + " to " + targetCurrency + ": " + response;
    }
}

