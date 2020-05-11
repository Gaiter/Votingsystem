package com.example.votingsystem.repository;

import com.example.votingsystem.model.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface VoiceRepository extends JpaRepository<Voice, Integer> {

    Voice getByUserIdAndDate(Integer userId, LocalDate localDate);

    @Transactional
    Voice save(Voice voice);
}
