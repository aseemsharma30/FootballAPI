package com.football.api.service;

import com.football.api.models.CountryLeaguesResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryLeagueService {

    private final RestTemplate restTemplate;

    public CountryLeagueService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CountryLeaguesResponse> getCountryLeaguesByContinent(String continent, String subscriptionKey) throws ParseException {
        String sportDataURL =
                "https://app.sportdataapi.com/api/v1/soccer/countries?apikey=" + subscriptionKey + "&continent=" + continent;

        ResponseEntity<String> response = restTemplate.getForEntity(sportDataURL, String.class);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
        List<CountryLeaguesResponse> countryLeaguesResponseList = new ArrayList<>();

        JSONObject data = (JSONObject) jsonObject.get("data");
        data.values().stream().forEach(o -> {
            JSONObject jsonObjectO = null;
            try {
                jsonObjectO = (JSONObject) parser.parse(o.toString());
            } catch (ParseException e) {
                CountryLeaguesResponse countryLeaguesResponse = new CountryLeaguesResponse();
                countryLeaguesResponse.setName("FAILED TO PARSE");
                countryLeaguesResponseList.add(countryLeaguesResponse);
            }
            CountryLeaguesResponse countryLeaguesResponse = new CountryLeaguesResponse();
            countryLeaguesResponse.setName(jsonObjectO.get("name").toString());
            countryLeaguesResponseList.add(countryLeaguesResponse);
        });

        return countryLeaguesResponseList;
    }

}
