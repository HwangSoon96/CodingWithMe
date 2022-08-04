package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.AttendanceRecord;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.QAttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AttendanceRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QAttendanceRecord qAttendanceRecord = QAttendanceRecord.attendanceRecord;

    public Optional<AttendanceRecord> findByUserIdAndConferenceIdLast(Long userId,Long conferenceId){
        AttendanceRecord attendanceRecord = jpaQueryFactory.select(qAttendanceRecord).from(qAttendanceRecord)
                .where(qAttendanceRecord.attEndTime.isNull())
                .where(qAttendanceRecord.conference.conferenceId.eq(conferenceId))
                .where(qAttendanceRecord.user.userId.eq(userId)).fetchOne();
        if(attendanceRecord == null) return Optional.empty();
        return Optional.ofNullable(attendanceRecord);
    };

    //해당 연 월에 해당하는 모든 레코드들
    public Optional<List<AttendanceRecord>> findAttendances(Long userId, Date from, Date to){
        List<AttendanceRecord> attendances = jpaQueryFactory.select(qAttendanceRecord).from(qAttendanceRecord)
                .where(qAttendanceRecord.user.userId.eq(userId))
                .where(qAttendanceRecord.date.between(from, to)).fetch();
        if(attendances == null) return Optional.empty();
        return Optional.ofNullable(attendances);
    }

}
