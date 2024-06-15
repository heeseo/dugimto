package com.dugimto.dto.oddsapi;

import com.dugimto.domain.MarketType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiMarketDto {
    @JsonDeserialize(using = MarketTypeDeserializer.class)
    public MarketType key;
    public List<ApiOutcomeDto> outcomes;
}
