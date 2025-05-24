package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class DemoController {
	  private final RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/")
    public String helloWorld()
    {
        return "calender";
    }
    
    
    private static final String CALENDARIFIC_URL =
            "https://calendarific.com/api/v2/holidays";
    private String apiKey="rTggNmYKh3WCkDrWvw9QAb6WuejIUCY5";
    
    @GetMapping("/api/holidays")
    public ResponseEntity<?> getHolidays(
            @RequestParam String country,
            @RequestParam int year) {

        RestTemplate restTemplate = new RestTemplate();

        String url = CALENDARIFIC_URL +
                "?api_key=" + apiKey +
                "&country=" + country +
                "&year=" + year;

        // Call Calendarific API
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        // Return the "response" node from Calendarific's JSON
        // (which contains holidays list and metadata)
        if(response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body = response.getBody();
            if(body != null && body.containsKey("response")) {
                return ResponseEntity.ok(body.get("response"));
            } else {
                return ResponseEntity.status(500).body("Invalid API response");
            }
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch holidays");
        }
    }
}
