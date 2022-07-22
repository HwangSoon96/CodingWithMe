package com.ssafy.api.controller;


//import com.ssafy.api.request.ClassesModifyPostReq;
import com.ssafy.api.request.ClassesAddStudentPostReq;
import com.ssafy.api.request.ClassesModifyPostReq;
import com.ssafy.api.request.ClassesRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.service.ClassesService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ClassesRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 반 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "반 API", tags = {"Classes"})
@RestController
@RequestMapping("/api/v1/tutor")
public class ClassesController {
    @Autowired
    ClassesService classesService;

    @PostMapping("classes")
    @ApiOperation(value = "반 개설", notes = "<strong>반 이름과 설명을 입력하여</strong> 반을 개설한다..")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="반 정보", required = true) ClassesRegisterPostReq registerInfo) {

        //임의로 리턴된 Classes 인스턴스.
        Classes classes = classesService.createClass(registerInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("classes")
    @ApiOperation(value = "반 정보 수정", notes = "반 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> ModifyClassesInfo(
            @RequestBody @ApiParam(value="반 정보", required = true) ClassesModifyPostReq classesModifyPostReq) {

        //임의로 리턴된 classes 인스턴스.
        Classes classes = classesService.modifyClass(classesModifyPostReq);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/classes/student")
    @ApiOperation(value = "반 학생 추가", notes = "반에 학생을 추가한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> addStudent(
            @RequestBody @ApiParam(value="반 정보", required = true) ClassesAddStudentPostReq classesAddStudentPostReq) {

        //임의로 리턴된 classes 인스턴스.
        boolean success = classesService.addStudent(classesAddStudentPostReq);

        if(success){
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

        }else{
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));

        }
    }
}
