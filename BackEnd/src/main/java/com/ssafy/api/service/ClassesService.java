package com.ssafy.api.service;

//import com.ssafy.api.request.ClassesModifyPostReq;
import com.ssafy.api.request.ClassesAddStudentPostReq;
import com.ssafy.api.request.ClassesModifyPostReq;
import com.ssafy.api.request.ClassesRegisterPostReq;
import com.ssafy.db.entity.Classes;


/**
 *	반 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ClassesService {
    //User createClass(UserRegisterPostReq userRegisterInfo);

    Classes createClass(ClassesRegisterPostReq classesRegisterInfo);

    boolean addStudent(ClassesAddStudentPostReq classesAddStudentPostReq);

   Classes modifyClass(ClassesModifyPostReq classesModifyPostReq);

}
