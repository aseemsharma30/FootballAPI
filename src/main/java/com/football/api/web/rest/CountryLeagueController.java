package com.football.api.web.rest;

import com.football.api.config.Constants;
import com.football.api.models.CountryLeaguesResponse;
import com.football.api.service.CountryLeagueService;
import org.json.simple.parser.ParseException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@Validated
public class CountryLeagueController {

    private final CountryLeagueService countryLeagueService;

    public CountryLeagueController(CountryLeagueService countryLeagueService) {
        this.countryLeagueService = countryLeagueService;
    }

    @GetMapping("/leagues-in-continent")
    public List<CountryLeaguesResponse> getLeaguesByContinent(@Pattern(regexp = Constants.VALID_CONTINENT_VALUES, message = "Valid Continents Values are europe,asia,africa,world")
                                                                 @Valid @RequestParam String continent,
                                                              @RequestHeader("subscription-key") String subscriptionKey) throws ParseException {
        return countryLeagueService.getCountryLeaguesByContinent(continent, subscriptionKey);
    }
}
