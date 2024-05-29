package com.dugimto.dto;

import com.dugimto.domain.*;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class BetDto {

    private Long gameId;

    @Enumerated(EnumType.STRING)
    private MarketType marketType;

    private String prediction;
    private Long stake;
    private Double odds;

}
