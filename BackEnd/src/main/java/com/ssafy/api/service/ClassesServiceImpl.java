package com.ssafy.api.service;

//import com.ssafy.api.request.ClassesModifyPostReq;
import com.ssafy.api.request.ClassesAddStudentPostReq;
import com.ssafy.api.request.ClassesModifyPostReq;
import com.ssafy.api.request.ClassesRegisterPostReq;

import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserClass;
import com.ssafy.db.repository.ClassesRepository;
import com.ssafy.db.repository.UserClassRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("classesService")
public class ClassesServiceImpl implements ClassesService {

    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Classes createClass(ClassesRegisterPostReq classesRegisterInfo) {
        Classes classes =new Classes();
        classes.setClassName(classesRegisterInfo.getClassName());
        classes.setClassDescription(classesRegisterInfo.getClassDescription());
        User user= userRepository.findByUserId(classesRegisterInfo.getTutorId()).get();
        classes.setUser(user);

        return classesRepository.save(classes);
    }

    @Autowired
    UserClassRepository userClassRepository;
    @Override
    public boolean addStudent(ClassesAddStudentPostReq classesAddStudentPostReq) {
        try{
            Classes classes = classesRepository.findByUserUserIdAndClassName(classesAddStudentPostReq.getTutorId(), classesAddStudentPostReq.getClassName()).get();

            UserClass uc = new UserClass();

            User student = userRepository.findByUserId(classesAddStudentPostReq.getStudentId()).get();

            uc.setClasses(classes);
            uc.setStudentId(student.getUserId());

            classes.getUserClassList().add(uc);
            userClassRepository.save(uc);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    @Override
    public Classes modifyClass(ClassesModifyPostReq classesModifyPostReq) {
        Classes classes = classesRepository.findByUserUserIdAndClassName(classesModifyPostReq.getTutorId(), classesModifyPostReq.getClassName()).get();

        classes.setClassName(classesModifyPostReq.getNewclassName());
        classes.setClassDescription(classesModifyPostReq.getClassDescription());

        return classesRepository.save(classes);
    }


}
