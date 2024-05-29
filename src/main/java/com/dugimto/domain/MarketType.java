package com.dugimto.domain;

public enum MarketType {
    MATCH_RESULT, HANDICAP, TOTAL;

    public static MarketType fromString(String marketTypeStr) {
        for (MarketType marketType : MarketType.values()) {
            if (marketType.name().equalsIgnoreCase(marketTypeStr)) {
                return marketType;
            }
        }
        throw new IllegalArgumentException("No enum constant found for string: " + marketTypeStr);
    }
}
