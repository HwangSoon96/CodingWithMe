package com.ssafy.api.controller;

import com.ssafy.api.request.AttendanceRegisterPostReq;
import com.ssafy.api.response.AttendanceRes;
import com.ssafy.api.service.AttendanceService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.AttendanceRecord;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "출입기록 API", tags = {"Attendance"})
@RestController
@RequestMapping("/api/v1/records/attendances")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @PostMapping()
    @ApiOperation(value = "강의실 입장시 출입 기록 생성", notes = "<strong>강의실에 입장할 때 </strong> 출입기록을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="출입 정보", required = true) AttendanceRegisterPostReq attendanceRegisterPostReq) {

        //임의로 리턴된 Classes 인스턴스.
        attendanceService.createAttendance(attendanceRegisterPostReq);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping
    @ApiOperation(value = "강의실 퇴장시 출입기록 수정", notes = "<strong>강의실에 퇴장할 때 </strong> 출입기록을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> endCall(
            @RequestBody @ApiParam(value="출입 정보", required = true) AttendanceRegisterPostReq attendanceRegisterPostReq) {

        //임의로 리턴된 Classes 인스턴스.
        attendanceService.updateAttendance(attendanceRegisterPostReq);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{userId}/{year}/{month}")
    @ApiOperation(value = "월별 출석 여부 호출", notes = "학생의 월 출입 기록을 호출한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<AttendanceRes> getAttendances(
            @PathVariable @ApiParam(value="사용자 식별자", required = true) Long userId,
            @PathVariable @ApiParam(value="연도", required = true) int year,
            @PathVariable @ApiParam(value="월", required = true) int month) {

        AttendanceRes res = attendanceService.getAttendances(userId, year, month);
        return ResponseEntity.status(200).body(res);
    }
}
