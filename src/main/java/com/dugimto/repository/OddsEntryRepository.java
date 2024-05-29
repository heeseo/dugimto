package com.dugimto.repository;

import com.dugimto.domain.OddsEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OddsEntryRepository extends JpaRepository<OddsEntry, Long> {
}
