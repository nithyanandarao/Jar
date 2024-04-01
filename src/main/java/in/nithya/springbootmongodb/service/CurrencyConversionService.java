package in.nithya.springbootmongodb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConversionService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String convertCurrency(String baseCurrency, String targetCurrency, double amount) {
        String API_URL = "https://api.fxratesapi.com/latest";
        String url = API_URL + "?base=" + baseCurrency;
        // Make a REST call to the external API
        ConversionResponse response = restTemplate.getForObject(url, ConversionResponse.class);

        // Extract the exchange rate for the target currency from the response
        assert response != null;
        double exchangeRate = response.getRates().get(targetCurrency);

        // Calculate the converted amount
        double convertedAmount = amount * exchangeRate;

        // Construct and return the response string

        return " "+baseCurrency+"-"+amount+","+targetCurrency+"-"+convertedAmount;

    }
}

