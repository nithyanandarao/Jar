package in.nithya.springbootmongodb.controller;

import in.nithya.springbootmongodb.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

    private final CurrencyConversionService conversionService;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/convert")
    public String convertCurrency(
            @RequestParam String id,
            @RequestParam String baseCurrency,
            @RequestParam String targetCurrency
            ) {
        return conversionService.convertCurrency(id, baseCurrency, targetCurrency);

    }


}

