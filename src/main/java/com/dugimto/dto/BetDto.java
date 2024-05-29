package com.dugimto.dto;

import com.dugimto.domain.Game;
import com.dugimto.domain.MarketType;
import com.dugimto.domain.Result;
import com.dugimto.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class BetDto {

    private Long gameId;

    @Enumerated(EnumType.STRING)
    private MarketType marketType;

    private String prediction;
    private Long stake;
    private Double odds;

}
