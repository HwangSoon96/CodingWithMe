package com.ssafy.db.repository;

import com.ssafy.db.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository  extends JpaRepository<AttendanceRecord, Long> {

}
