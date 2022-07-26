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
import com.ssafy.db.entity.UserClass;
import com.ssafy.db.repository.ClassesRepository;
import com.ssafy.db.repository.UserRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 반 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "반 API", tags = {"Classes"})
@RestController
@RequestMapping("/api/v1/tutor")
public class ClassesController {
    @Autowired
    ClassesService classesService;

    @Autowired
    UserRepository userRepository;
    @PostMapping("/classes")
    @ApiOperation(value = "반 개설", notes = "<strong>반 이름과 설명을 입력하여</strong> 반을 개설한다..")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="반 정보", required = true) ClassesRegisterPostReq registerInfo) {

        //임의로 리턴된 Classes 인스턴스.
        Classes classes = classesService.createClass(registerInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/classes")
    @ApiOperation(value = "반 정보 수정", notes = "반 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> ModifyClassesInfo(
            @RequestBody @ApiParam(value="반 정보", required = true) ClassesModifyPostReq classesModifyPostReq) {

        Classes classes = classesService.modifyClass(classesModifyPostReq);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/classes")
    @ApiOperation(value = "반 삭제", notes = "반을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteUser(
            @RequestBody @ApiParam(value="반 정보", required = true) ClassesRegisterPostReq registerInfo) {

        boolean success = classesService.deleteClass(registerInfo);

        if(success){
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }else{
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
        }

    }

    @PostMapping("/classes/student")
    @ApiOperation(value = "반 학생 추가", notes = "반에 학생을 추가한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
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
    @DeleteMapping("/classes/student")
    @ApiOperation(value = "반 학생 삭제", notes = "반에 학생을 삭제시킨다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteUser(
            @RequestBody @ApiParam(value="반 정보", required = true) ClassesAddStudentPostReq classesAddStudentPostReq) {

        boolean success = classesService.deleteStudent(classesAddStudentPostReq);

        if(success){
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }else{
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
        }

    }

    @GetMapping("/{user_id}/classes")
    @ApiOperation(value = "전체 반 조회", notes = "사용자가 개설한 반(방)을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getAllClassInfo(@PathVariable("user_id") Long userId){

        List<Classes> classes = classesService.getAllClassesInfo(userId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, String.valueOf(classes)));
    }

    @GetMapping("/{userId}/classes/{className}")
    @ApiOperation(value = "반 정보 조회", notes = "반에 있는 학생들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String,Object>> getClassInfo(
            @PathVariable("userId") Long userId, @PathVariable("className") String className){

        List<User> userList =  new ArrayList<>();

        List<UserClass> userClasses = classesService.getClassesInfo(userId, className);

        for (UserClass userClass : userClasses) {
            User user = userRepository.findByUserId(userClass.getStudentId()).get();
            userList.add(user);
        }

        Map<String,Object> map =new HashMap<>();
        map.put("students",userList);
        return ResponseEntity.status(200).body(map);
    }


}
