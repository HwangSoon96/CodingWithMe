package com.ssafy.db.repository;

import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserClassRepository extends JpaRepository<UserClass, Long> {
    Optional<UserClass> findByStudentId(Long user);
}
