package com.dugimto.dto.oddsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiOutcomeDto {

    public String name;
    public double price;
}
