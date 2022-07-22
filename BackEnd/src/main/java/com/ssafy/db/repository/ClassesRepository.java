package com.ssafy.db.repository;

import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassesRepository extends JpaRepository <Classes, Long>{
    Optional<Classes> findByUserUserIdAndClassName(Long user, String classname);
}
