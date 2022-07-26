package com.ssafy.api.controller;

import com.ssafy.api.request.ClassesRegisterPostReq;
import com.ssafy.api.request.TestRegisterPostReq;
import com.ssafy.api.service.TestService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.Test;
import com.ssafy.db.repository.ClassesRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "시험 API", tags = {"Tests"})
@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    ClassesRepository classesRepository;

    @PostMapping()
    @ApiOperation(value = "시험 생성", notes = "<strong>시험 정보를 입력하여</strong> 시험을 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="시험 정보", required = true)TestRegisterPostReq testRegisterInfo) {

        //임의로 리턴된 Classes 인스턴스.
        Test test = testService.createTest(testRegisterInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}
