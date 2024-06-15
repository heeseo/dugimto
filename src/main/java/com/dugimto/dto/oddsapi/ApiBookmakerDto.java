package com.dugimto.dto.oddsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiBookmakerDto {

    public String key;
    public String title;
    @JsonProperty("last_update")
    public String lastUpdate;
    public List<ApiMarketDto> markets;
}
