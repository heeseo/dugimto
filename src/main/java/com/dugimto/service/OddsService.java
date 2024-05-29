package com.dugimto.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class OddsService {

    @Value("${odds.api.key}")
    private String apiKey;

    public String fetchUpcomingAndLiveOdds() {
        String url = "https://api.the-odds-api.com/v4/sports/upcoming/odds/";

        //v4/sports/{sport}/odds/?apiKey={apiKey}&regions={regions}&markets={markets}

        WebClient webClient = WebClient.create(url);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apiKey", apiKey)
                        .queryParam("regions","eu")
                        .queryParam("markets", "h2h")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
