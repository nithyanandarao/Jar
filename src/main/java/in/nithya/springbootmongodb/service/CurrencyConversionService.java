package in.nithya.springbootmongodb.service;

import in.nithya.springbootmongodb.model.ConversionResponse;
import in.nithya.springbootmongodb.model.TodoDTO;
import in.nithya.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyConversionService {

    @Autowired
    private TodoRepository todoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public double getAmountById(String id) {
        Optional<TodoDTO> optionalCurrencyData = todoRepository.findById(id);
        if (optionalCurrencyData.isPresent()) {
            return Double.parseDouble(optionalCurrencyData.get().getPayment());
        } else {
            throw new IllegalArgumentException("Currency data not found for ID: " + id);
        }
    }

    public String convertCurrency(String id,String baseCurrency, String targetCurrency) {
        double baseAmount = getAmountById(id);
        String API_URL = "https://api.fxratesapi.com/latest";
        String url = API_URL + "?base=" + baseCurrency;
        // Make a REST call to the external API
        ConversionResponse response = restTemplate.getForObject(url, ConversionResponse.class);

        // Extract the exchange rate for the target currency from the response
        assert response != null;
        double exchangeRate = response.getRates().get(targetCurrency);

        // Calculate the converted amount
        double convertedAmount = baseAmount * exchangeRate;

        // Construct and return the response string

        return " "+baseCurrency+"-"+baseAmount+","+targetCurrency+"-"+convertedAmount;

    }


    public double convertToINR(String currency, double amount) {
        String API_URL = "https://api.fxratesapi.com/latest";
        String url = API_URL + "?base=" + currency + "&symbols=INR";
        ConversionResponse response = restTemplate.getForObject(url, ConversionResponse.class);

        // Extract the exchange rate for INR from the response
        assert response != null;
        double exchangeRate = response.getRates().get("INR");

        // Convert the amount to INR
        return amount * exchangeRate;
    }
}

