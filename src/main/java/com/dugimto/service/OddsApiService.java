package com.dugimto.service;

import com.dugimto.domain.*;
import com.dugimto.dto.oddsapi.ApiBookmakerDto;
import com.dugimto.dto.oddsapi.ApiGameDto;
import com.dugimto.dto.oddsapi.ApiMarketDto;
import com.dugimto.dto.oddsapi.ApiOutcomeDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class OddsApiService {

    @Value("${odds.api.key}")
    private String apiKey;

    public List<Game> fetchUpcomingAndLiveOdds() {
        String url = "https://api.the-odds-api.com/v4/sports/upcoming/odds/";

        //v4/sports/{sport}/odds/;
        //        ?apiKey={apiKey}&regions={regions}&markets={markets}

        WebClient webClient = WebClient.create(url);

        String jsonResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apiKey", apiKey)
                        .queryParam("regions", "eu")
                        .queryParam("markets", "h2h")
                        .queryParam("bookmakers","betway")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Convert JSON response to JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        List<ApiGameDto> apiGameDTOs = null;

        try {
            apiGameDTOs = objectMapper.readValue(jsonResponse, new TypeReference<List<ApiGameDto>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Map List<ApiGameDTO> to List<Game>
        List<Game> games = new ArrayList<>();
        if (apiGameDTOs != null) {
            for (ApiGameDto apiGameDTO : apiGameDTOs) {
                Game game = mapApiGameDTOToGame(apiGameDTO);
                games.add(game);
            }
        }

        return games;
    }

    private Game mapApiGameDTOToGame(ApiGameDto apiGameDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime startTime = LocalDateTime.parse(apiGameDto.commenceTime, formatter);

        Game game = Game.builder()
                .gameType(parseGameType(apiGameDto.getSportKey()))
                .gameTitle(apiGameDto.sportTitle)
                .homeTeam(apiGameDto.homeTeam)
                .awayTeam(apiGameDto.awayTeam)
                .startTime(startTime)
                .externalId(apiGameDto.getId())
                .build();

        // Set oddsEntries based on bookmakers and markets
        for (ApiBookmakerDto bookmaker : apiGameDto.getBookmakers()) {
            for (ApiMarketDto market : bookmaker.getMarkets()) {
                // Handle potential null MarketType
                MarketType marketType = market.getKey();
                if (marketType != null) {
                    OddsEntry oddsEntry = new OddsEntry(marketType, game);
                    //game.addOddsEntry(oddsEntry);

                    for (ApiOutcomeDto outcomeDto : market.getOutcomes()) {
                        Outcome outcome = new Outcome(oddsEntry, outcomeDto.getName(), outcomeDto.getPrice());
                        //oddsEntry.addOutcome(outcome);
                    }
                }
            }
        }

        return game;
    }

    private GameType parseGameType(String sportKey) {
        if (sportKey == null || sportKey.isEmpty()) {
            return GameType.ETC; // Default fallback value if sportKey is null or empty
        }

        // Assuming sportKey format: "sport_type_league"
        String[] parts = sportKey.split("_");

        if (parts.length < 2) {
            return null; // or handle appropriately based on your application's logic
        }

        String sportType = parts[0].toUpperCase(); // Convert to uppercase to match enum case

        try {
            return GameType.valueOf(sportType);
        } catch (IllegalArgumentException e) {
            return GameType.ETC; // Fallback to GameType.ETC if no matching enum constant found
        }
    }
}
