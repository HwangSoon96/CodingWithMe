package com.ssafy.api.controller;

import com.ssafy.api.request.ConferenceModifyPostReq;
import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "강의 API", tags = {"Conference"})
@RestController
@RequestMapping("/api/v1/conferences")
public class ConferenceController {
    @Autowired
    ConferenceService conferenceService;

    @PostMapping
    @ApiOperation(value = "강의실 개설", notes = "<strong>강사가 </strong> 강의실을 개설한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> registerTutor(
            @RequestBody @ApiParam(value="강의실 등록 정보", required = true) ConferenceRegisterPostReq registerInfo) {

        //임의로 리턴된 Conference 인스턴스.
        conferenceService.createConference(registerInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping
    @ApiOperation(value = "강의 정보 수정", notes = "강의에 대한 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> modifyConferenceInfo(
            @RequestBody @ApiParam(value="강의실 정보", required = true) ConferenceModifyPostReq modifyInfo) {

        //임의로 리턴된 Conference 인스턴스.
        conferenceService.modifyConference(modifyInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
    @PutMapping("/end")
    @ApiOperation(value = "강의 정보 수정", notes = "강의가 종료되고 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> finishConferenceInfo(
            @RequestBody @ApiParam(value="강의실 정보", required = true) ConferenceModifyPostReq modifyInfo) {

        //임의로 리턴된 Conference 인스턴스.
        conferenceService.finishConference(modifyInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{classId}/active")
    @ApiOperation(value = "강의실 정보 조회", notes = "반 식별자를 통해 현재 강의 중인 강의실 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> getActiveConferenceByClassId(
            @PathVariable @ApiParam(value="반 식별자", required = true) Long classId) {

        //임의로 리턴된 Conference 인스턴스.
        Conference conference = conferenceService.getActiveConference(classId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", BaseResponseBody.of(200, "Success"));
        map.put("conference", conference);

        return ResponseEntity.status(200).body(map);
    }

    @GetMapping("/{classId}/not-active")
    @ApiOperation(value = "강의실 정보 조회", notes = "반 식별자를 통해 강의실 이력을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> getNotActiveConferenceByClassId(
            @PathVariable @ApiParam(value="반 식별자", required = true) Long classId) {

        //임의로 리턴된 Conference 인스턴스.
        List<Conference> conferences = conferenceService.getNotActiveConference(classId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", BaseResponseBody.of(200, "Success"));
        map.put("conference", conferences);

        return ResponseEntity.status(200).body(map);
    }

    @GetMapping("/{classId}/all")
    @ApiOperation(value = "강의실 정보 조회", notes = "반 식별자를 통해 현재 강의 중인 강의실 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> getAllConferenceByClassId(
            @PathVariable @ApiParam(value="반 식별자", required = true) Long classId) {

        //임의로 리턴된 Conference 인스턴스.
        List<Conference> conferences = conferenceService.getAllConferenceByClassId(classId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", BaseResponseBody.of(200, "Success"));
        map.put("conference", conferences);

        return ResponseEntity.status(200).body(map);
    }

    @GetMapping("/{conferenceName}/name")
    @ApiOperation(value = "강의실 정보 조회", notes = "방제를 통해 열려있는 강의들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> getConferenceByName(
            @PathVariable @ApiParam(value="강의실 방제", required = true) String conferenceName) {

        //임의로 리턴된 Conference 인스턴스.
        List<Conference> conferences = conferenceService.getConferencesByConferenceName(conferenceName);
        Map<String, Object> map = new HashMap<>();
        map.put("message", BaseResponseBody.of(200, "Success"));
        map.put("conference", conferences);

        return ResponseEntity.status(200).body(map);
    }

    @GetMapping("/{ownerId}/id")
    @ApiOperation(value = "강의실 정보 조회", notes = "방장 아이디를 통해 열려있는 강의들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> getConferenceById(
            @PathVariable @ApiParam(value="사용자 아이디", required = true) String ownerId) {

        //임의로 리턴된 Conference 인스턴스.
        Conference conference = conferenceService.getConferencesById(ownerId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", BaseResponseBody.of(200, "Success"));
        map.put("conference", conference);

        return ResponseEntity.status(200).body(map);
    }
}
