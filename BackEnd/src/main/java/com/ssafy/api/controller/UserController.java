package com.ssafy.api.controller;

import com.ssafy.api.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<Map<String, Object>> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserById(userId);
		Map<String, Object> map = new HashMap();
		map.put("user", user);

		return ResponseEntity.status(200).body(map);
	}

	@PutMapping
	@ApiOperation(value = "회원 본인 정보 수정", notes = "로그인한 회원 본인의 정보를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> ModifyUserInfo(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

		User user =userService.modifyUser(registerInfo);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@DeleteMapping
	@ApiOperation(value = "회원 탈퇴", notes = "로그인한 회원 계정을 탈퇴시킨다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> deleteUser(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

		boolean success = userService.deleteUser(registerInfo);

		if(success){
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else{
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
		}

	}

	@PostMapping("/id")
	@ApiOperation(value = "아이디 찾기", notes = "<strong>이름과 전화번호</strong>를 통해 아이디를 검색 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "user_id"),
			@ApiResponse(code = 404, message = "Fail"),
	})
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true)UserNamePhoneReq userNamePhoneReq) {

		User user = userService.getUserByNameAndPhone(userNamePhoneReq.getName(), userNamePhoneReq.getPhone());
		if(user != null){
			String id = user.getId();
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, id));
		}else{
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Fail"));
		}
	}

	@GetMapping("/idcheck/{user_id}")
	@ApiOperation(value = "회원 아이디 중복 체크", notes = "회원가입 시 회원 아이디 중복 체크 검사")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> idCheck(@PathVariable("user_id") String userId){

		boolean temp = userService.checkUserId(userId);

		if(temp == true){
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "fail"));
		}
	}

	@GetMapping("/emailcheck/{email}")
	@ApiOperation(value = "회원 이메일 중복 체크", notes = "회원가입 시 회원 이메일 중복 체크 검사")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> emailCheck(@PathVariable("email") String email){

		boolean temp = userService.checkUserEmail(email);

		if(temp == true){
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else {

			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "fail"));
		}
	}

	@Autowired
	JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	String sendFrom;

	@Autowired
	Environment env;

	@PostMapping("/email")
	@ApiOperation(value = "임시 비밀번호 발송", notes = "임시 비밀번호를 발송한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> sendPasswordMail(
			@RequestBody @ApiParam(value="아이디, 이메일 정보", required = true) UserIdEmailReq user) {
		boolean success = userService.sendPasswordMail(user);
		if(success){
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else{
			return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
		}
	}

	@PostMapping("/vemail")
	@ApiOperation(value = "이메일 인증 메일 발송", notes = "인증 코드를 발송한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> sendVerificationMail(
			@RequestBody @ApiParam(value="아이디, 이메일 정보", required = true) UserIdEmailReq user) {
		boolean success = userService.sendVerificationMail(user);
		if(success){
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else{
			return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
		}
	}

	@PostMapping("/vemail/code")
	@ApiOperation(value = "이메일 인증 코드 조회", notes = "인증 코드를 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> sendVerificationCode(
			@RequestBody @ApiParam(value="아이디, 이메일 정보", required = true) UserEmailCodeReq user) {
		String verificationCode = userService.sendVerificationCode(user);

		if(verificationCode.equals(user.getCode())){
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, verificationCode));
		}else{
			return ResponseEntity.status(500).body(BaseResponseBody.of(500, verificationCode));
		}
	}
}
