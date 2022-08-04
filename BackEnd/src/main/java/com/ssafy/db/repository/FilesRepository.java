package com.ssafy.db.repository;

import com.ssafy.db.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilesRepository extends JpaRepository<Files,Long> {
    Optional<Files> findByTestTestId(Long testId);
}