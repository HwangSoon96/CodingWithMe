package com.ssafy.api.service;

import com.ssafy.api.request.AttendanceRegisterPostReq;
import com.ssafy.api.response.AttendanceRes;
import com.ssafy.api.response.AttInfo;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("attendanceService")
@Transactional(readOnly = true)
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    AttendanceRepositorySupport attendanceRepositorySupport;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceRepositorySupport conferenceRepositorySupport;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserClassRepository userClassRepository;

    @Override
    @Transactional
    public AttendanceRecord createAttendance(AttendanceRegisterPostReq attendanceRegisterPostReq) {
        AttendanceRecord attendance = new AttendanceRecord();

        Long userId = attendanceRegisterPostReq.getUserId();
        Long conferenceId = attendanceRegisterPostReq.getConferenceId();

        Conference conference = conferenceRepository.findByConferenceId(conferenceId).get();
        User user = userRepository.findByUserId(userId).get();
        Date date = new Date();


        attendance.setConference(conference); //현재 살아있는 강의를 가져와야 한다. 클래스 아이디가 필요
        attendance.setUser(user);
        attendance.setDate(date);
        attendance.setAttStartTime(date);

        return attendanceRepository.save(attendance);
    }

    @Override
    @Transactional
    public AttendanceRecord updateAttendance(AttendanceRegisterPostReq attendanceRegisterPostReq) {
        Long userId = attendanceRegisterPostReq.getUserId();
        Long conferenceId = attendanceRegisterPostReq.getConferenceId();
        AttendanceRecord attendanceRecord = attendanceRepositorySupport.findByUserIdAndConferenceIdLast(userId, conferenceId).get();
        Date date = new Date();
        attendanceRecord.setAttEndTime(date);

        return attendanceRepository.save(attendanceRecord);
    }

    @Override
    public AttendanceRes getAttendances(Long userId, int year, int month) {
        AttendanceRes res = new AttendanceRes();

        Calendar cal = new GregorianCalendar(year, month, 1);
        int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

       Date from = getDate(year, month, 1);
       Date to = getDate(year, month, daysOfMonth);

        List<AttendanceRecord> attendances = attendanceRepositorySupport.findAttendances(userId, from, to).get();

        res.setUserId(userId);
        List<AttInfo> attInfos = new ArrayList<>();


        Long classId = userClassRepository.findByStudentId(userId).get().getClasses().getClassId();

        List<Conference> conferences = conferenceRepositorySupport.findByClassesClassIdNotActiveMonth(classId, from, to).get();

        //하루에 강의가 몇 시간 진행되었는지
        // userId - classId - 강의들 조회 가능
        // Map<String, Long> - 날짜, 시간
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Map<String, Long> conferenceTimeRecord = new TreeMap<>();

        for (Conference conference : conferences) {
            long startTime = conference.getConfStartTime().getTime();
            long endTime = conference.getConfEndTime().getTime();

            long minute = (endTime - startTime) / 60000;
            System.out.println(minute + "분 차이");

            String day = dateFormat.format(conference.getDate());
            System.out.println("day : " + day);
            conferenceTimeRecord.put(day, conferenceTimeRecord.getOrDefault(day, 0L) + minute);


        }
        Map<String, Long> studentTimeRecord = new TreeMap<>();

        for (AttendanceRecord attendance : attendances) {
            long startTime = attendance.getAttStartTime().getTime();
            long endTime = attendance.getAttEndTime().getTime();

            long minute = (endTime - startTime) / 60000;
            System.out.println(minute + "분 차이");

            String day = dateFormat.format(attendance.getDate());
            studentTimeRecord.put(day, studentTimeRecord.getOrDefault(day, 0L) + minute);

        }
        // 하루에 그 강의를 몇분 들었는지. // 2/3이상 출석했을 시에 Yes 아니면 No
        for (String key : conferenceTimeRecord.keySet()) {
            long conferenceTime = conferenceTimeRecord.get(key);
            AttInfo attInfo = new AttInfo();

            if (studentTimeRecord.containsKey(key)) {

                long studentTime = studentTimeRecord.get(key);

                if ((double) conferenceTime / 3 * 2 <= (double) studentTime) {
                    attInfo.setAttendance("Yes");
                } else {
                    attInfo.setAttendance("No");
                }

            } else {
                attInfo.setAttendance("No");
            }

            System.out.println("key : " + key);
            attInfo.setDate(key);
            attInfos.add(attInfo);
        }

        res.setAttInfos(attInfos);
        return res;
    }

    public static Date getDate(int year, int month, int date) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date);
        return new Date(cal.getTimeInMillis());
    }
}
