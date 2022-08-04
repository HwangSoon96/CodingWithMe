package com.ssafy.db.repository;

import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {


    Optional<List<Conference>> findByClassesClassId(Long classId);

    @Query("select c from Conference c where c.isActive = 1")
    Optional<Conference> findByConferenceId(Long conferenceId);
}
