package com.ssafy.api.controller;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.api.request.TestRecordRegisterPostReq;
import com.ssafy.api.service.TestRecordService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.TestRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "시험 이력 API", tags = {"TestRecord"})
@RestController
@RequestMapping("/api/v1/records")
public class TestRecordController {

    @Autowired
    TestRecordService testRecordService;

    @Autowired
    TestRepository testRepository;
    @PostMapping("/tests")
    @ApiOperation(value = "시험이력 개설/수정", notes = "<strong>시험에 대한</strong> 이력을 만든다./수정한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerTutor(
            @RequestBody @ApiParam(value="시험 이력 정보", required = true) TestRecordRegisterPostReq testRecordRegisterInfo) {

        //임의로 리턴된 testRecord 인스턴스.
        testRecordService.createOrUpdateTestRecord(testRecordRegisterInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{userId}/tests")
    @ApiOperation(value = "시험 이력 조회", notes = "사용자의 시험 이력을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String,Object>> getTestRecordInfo(
            @PathVariable("userId") Long userId){


        List<TestRecord> testRecordsList = testRecordService.getAllTestRecordByUserId(userId);

        Map<String,Object> map=new HashMap<>();
        map.put("records",testRecordsList);

        return ResponseEntity.status(200).body(map);
    }
}
