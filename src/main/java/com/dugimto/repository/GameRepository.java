package com.dugimto.repository;

import com.dugimto.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByExternalIdIn(List<String> externalIds);
}
