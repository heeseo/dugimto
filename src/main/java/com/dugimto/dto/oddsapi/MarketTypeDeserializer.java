package com.dugimto.dto.oddsapi;

import com.dugimto.domain.MarketType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MarketTypeDeserializer extends JsonDeserializer<MarketType> {
    private static final Map<String, MarketType> marketTypeMap = new HashMap<>();

    static {
        marketTypeMap.put("h2h", MarketType.MATCH_RESULT);
        marketTypeMap.put("headtohead", MarketType.MATCH_RESULT);
        marketTypeMap.put("moneyline", MarketType.MATCH_RESULT);
        marketTypeMap.put("spreads", MarketType.HANDICAP);
        marketTypeMap.put("spread", MarketType.HANDICAP);
        marketTypeMap.put("totals", MarketType.TOTAL);
        marketTypeMap.put("total", MarketType.TOTAL);
        // Add other mappings as needed
    }

    @Override
    public MarketType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText().toLowerCase();
        MarketType marketType = marketTypeMap.get(value);
        if (marketType == null) {
            return null; // or return a default value if preferred
        }
        return marketType;
    }
}