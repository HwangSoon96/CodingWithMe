package com.ssafy.api.service;

import com.ssafy.api.request.AttendanceRegisterPostReq;
import com.ssafy.api.response.AttendanceRes;
import com.ssafy.db.entity.AttendanceRecord;

public interface AttendanceService {
    AttendanceRecord createAttendance(AttendanceRegisterPostReq attendanceRegisterPostReq);

    AttendanceRecord updateAttendance(AttendanceRegisterPostReq attendanceRegisterPostReq);
    AttendanceRes getAttendances(Long userId, int year, int month);
}
