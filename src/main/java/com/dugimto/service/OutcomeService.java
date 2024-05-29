package com.dugimto.service;

import com.dugimto.domain.Outcome;
import com.dugimto.repository.OutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OutcomeService {

    private final OutcomeRepository outcomeRepository;
    public Outcome findOutcomeById(Long id) {
        return outcomeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:" + id));
    }
}
