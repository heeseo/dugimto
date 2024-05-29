package com.dugimto.dto;

import com.dugimto.domain.MarketType;
import com.dugimto.domain.OddsEntry;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class OddsEntryDto {

    private Long id;
    private MarketType marketType;
    private List<OutcomeDto> outcomes = new ArrayList<>();
}
