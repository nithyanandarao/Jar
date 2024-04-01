package in.nithya.springbootmongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversionResponse {

    // Getters and Setters
    private boolean success;
    private String terms;
    private String privacy;
    private long timestamp;
    private String date;
    private String base;
    private Map<String, Double> rates;

}

