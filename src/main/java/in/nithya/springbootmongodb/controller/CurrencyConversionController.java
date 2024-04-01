package in.nithya.springbootmongodb.controller;

import in.nithya.springbootmongodb.model.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService conversionService;

    @GetMapping("/convert")
    public String convertCurrency(
            @RequestParam String baseCurrency,
            @RequestParam String targetCurrency,
            @RequestParam double amount) {
        return conversionService.convertCurrency(baseCurrency, targetCurrency, amount);

    }
}

