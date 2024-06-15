package com.dugimto.dto.oddsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGameDto {

    public String id;

    @JsonProperty("sport_key")
    public String sportKey;
    @JsonProperty("sport_title")
    public String sportTitle;
    @JsonProperty("commence_time")
    public String commenceTime;
    @JsonProperty("home_team")
    public String homeTeam;
    @JsonProperty("away_team")
    public String awayTeam;
    public List<ApiBookmakerDto> bookmakers;

}
