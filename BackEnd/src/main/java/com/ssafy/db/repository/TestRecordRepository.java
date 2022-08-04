package com.ssafy.db.repository;

import com.ssafy.db.entity.TestRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestRecordRepository extends JpaRepository <TestRecord,Long> {
    Optional<List<TestRecord>> findByUserUserId(Long userId);

    Optional<TestRecord> findByTestTestIdAndUserUserId(Long testId, Long userId);


}
