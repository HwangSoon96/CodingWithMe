package com.ssafy.db.repository;



import com.ssafy.db.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByClassesClassIdAndTestName(Long Id,String testName);

    Optional<List<Test>> findByClassesClassId(Long classes);

    Optional<Test> findByTestId(Long testId);


}
