package com.ssafy.api.controller;

import com.ssafy.api.request.*;
import com.ssafy.api.response.CompileRes;
import com.ssafy.api.service.TestService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Test;
import com.ssafy.db.repository.ClassesRepository;
import com.ssafy.db.repository.TestRepository;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "시험 API", tags = {"Tests"})
@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    TestRepository testRepository;




    @GetMapping("/{classId}")
    @ApiOperation(value = "반시험 조회", notes = "해당 반의 등록된 시험정보 모두를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> getAllTestInfo(@PathVariable("classId") Long classId){

        List<Test> test = testService.getAllTestInfo(classId);

        Map<String, Object> map = new HashMap<>();
        map.put("testList", test);
        return ResponseEntity.status(200).body(map);
    }

    @GetMapping("/{classId}/{testName}")
    @ApiOperation(value = "시험 조회", notes = "해당 반의 해당 시험정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getTestInfo(@PathVariable("classId") Long classId,
                                                                     @PathVariable("testName") String testName){

        Test test = testService.getTestInfo(classId,testName);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, String.valueOf(test)));
    }


    @PostMapping()
    @ApiOperation(value = "시험 생성", notes = "<strong>시험 정보를 입력하여</strong> 시험을 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="시험 정보", required = true) TestRegisterPostReq testRegisterinfo) {
        //임의로 리턴된 Classes 인스턴스.


        testService.createTest(testRegisterinfo);



        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping
    @ApiOperation(value = "시험 정보 수정", notes = "시험 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> ModifyTestInfo(
            @RequestBody @ApiParam(value="수정 된 시험 정보", required = true) TestModifyPostReq testModifyPostReq) {

        testService.modifyTest(testModifyPostReq);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping
    @ApiOperation(value = "시험 삭제", notes = "시험을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteUser(
            @RequestBody @ApiParam(value="시험 정보", required = true) TestRegisterPostReq testRegisterInfo) {


        boolean success =testService.deleteTest(testRegisterInfo);

        if(success){
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }else{
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
        }

    }

    @GetMapping("/idcheck/{testName}/{classId}")
    @ApiOperation(value = "시험 명 중복 체크", notes = "시험 생성 시 시험 명 중복 체크 검사")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> testNameCheck(@PathVariable("testName") String testName,
                                                                    @PathVariable("classId") Long classId) {

        boolean temp = testService.checkTestName(testName,classId);
        System.out.println("Test ========="+temp);
        if (temp == true) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "fail"));
        }
    }

    @PostMapping("/compile")
    @ApiOperation(value = "문제 채점", notes = "문제에 대한 학생의 소스코드를 실행하고 결과를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CompileRes> problemCompile(
            @RequestBody @ApiParam(value="시험 정보", required = true) CodeCompilePostReq codeCompilePostReq) {
        CompileRes res = testService.problemCompile(codeCompilePostReq);
        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/compile/test")
    @ApiOperation(value = "시험 전체 채점", notes = "시험의 전체 문제에 대한 학생의 소스코드를 실행하고 결과를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> testCompile(
            @RequestBody @ApiParam(value="시험 정보", required = true) TestCompilePostReq testCompilePostReq) {
        testService.testCompile(testCompilePostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}
