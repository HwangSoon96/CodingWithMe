package com.ssafy.db.repository;

import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassesRepository extends JpaRepository <Classes, Long>{
    @Query("select c from Classes c join fetch c.userClassList where c.className=:className and c.user.userId = :userId")
    Optional<Classes> findFetchJoin(@Param("userId") Long userId, @Param("className") String className);

    @Query("select c from Classes c join fetch c.testList where c.classId=:classId")
    Optional<Classes> findByClassId(@Param("classId")Long classId);

    Optional<List<Classes>> findByUserUserId(Long user);


}