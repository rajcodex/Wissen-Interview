package com.example.demo.controller;

import java.util.List;
import java.util.Map;

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
    
    
    @GetMapping("/api/holidays")
    public List<Map<String, Object>> getHolidays(
            @RequestParam String country,
            @RequestParam int year) throws RestClientException {

        String url = String.format("https://date.nager.at/api/v3/PublicHolidays/%d/%s", year, country);

        // Nager.Date returns a JSON array of holidays, map to List<Map>
        List<Map<String, Object>> holidays = null;
		try {
			holidays = restTemplate.getForObject(url, List.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return holidays;

}
}
