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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("classesService")
@Transactional(readOnly = true)
@Slf4j
public class ClassesServiceImpl implements ClassesService {

    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Classes createClass(ClassesRegisterPostReq classesRegisterInfo) {
        Classes classes =new Classes();
        classes.setClassName(classesRegisterInfo.getClassName());
        classes.setClassDescription(classesRegisterInfo.getClassDescription());
        User user= userRepository.findByUserId(classesRegisterInfo.getTutorId()).get();
        classes.setUser(user);

        return classesRepository.save(classes);
    }

    @Override
    @Transactional
    public boolean deleteClass(ClassesRegisterPostReq classesRegisterInfo) {
        boolean success = false;

        try {
            Classes classes = classesRepository.findFetchJoin(classesRegisterInfo.getTutorId(), classesRegisterInfo.getClassName()).get();
            classesRepository.delete(classes);
            success = true;
        }catch (Exception e){
            success = false;
        }

        return success;
    }

    @Autowired
    UserClassRepository userClassRepository;
    @Override
    @Transactional
    public boolean addStudent(ClassesAddStudentPostReq classesAddStudentPostReq) {
        try{
            Optional<UserClass> OuserClass = userClassRepository.findByStudentId(classesAddStudentPostReq.getStudentId());

            if(OuserClass.isPresent()){
                return false;
            }

            Classes classes = classesRepository.findFetchJoin(classesAddStudentPostReq.getTutorId(), classesAddStudentPostReq.getClassName()).get();

            UserClass uc = new UserClass();

            User student = userRepository.findByUserId(classesAddStudentPostReq.getStudentId()).get();

            uc.setClasses(classes);
            uc.setStudentId(student.getUserId());

            classes.getUserClassList().add(uc);

            userClassRepository.save(uc);
        }catch (NoSuchElementException e){
            Optional<UserClass> OuserClass = userClassRepository.findByStudentId(classesAddStudentPostReq.getStudentId());

            if(OuserClass.isPresent()){
                return false;
            }
            Classes classes = classesRepository.findByUserUserIdAndClassName(classesAddStudentPostReq.getTutorId(), classesAddStudentPostReq.getClassName()).get();
            UserClass uc = new UserClass();

            User student = userRepository.findByUserId(classesAddStudentPostReq.getStudentId()).get();

            uc.setClasses(classes);
            uc.setStudentId(student.getUserId());

            classes.getUserClassList().add(uc);

            userClassRepository.save(uc);
            return true;
        }catch (Exception e){
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public Classes modifyClass(ClassesModifyPostReq classesModifyPostReq) {
        Classes classes = classesRepository.findFetchJoin(classesModifyPostReq.getTutorId(), classesModifyPostReq.getClassName()).get();

        classes.setClassName(classesModifyPostReq.getNewclassName());
        classes.setClassDescription(classesModifyPostReq.getClassDescription());

        return classesRepository.save(classes);
    }

    @Override
    @Transactional
    public boolean deleteStudent(ClassesAddStudentPostReq classesAddStudentPostReq) {
        boolean success = false;

        try {
            //User user = userRepository.findById(classesAddStudentPostReq.getStudentId()).get();
            Classes classes = classesRepository.findFetchJoin(classesAddStudentPostReq.getTutorId(), classesAddStudentPostReq.getClassName()).get();
            User student = userRepository.findByUserId(classesAddStudentPostReq.getStudentId()).get();

            UserClass uc = userClassRepository.findByStudentId(student.getUserId()).get();
//            userClassRepository.delete(uc);
            //classes-UserClass
            classes.getUserClassList().remove(uc);
            userClassRepository.delete(uc);
            success = true;
        }catch (Exception e){
            success = false;
        }

        return success;
    }

    @Override
    @Transactional
    public List<Classes> getAllClassesInfo(Long userid) {

        User user = userRepository.findByUserId(userid).get();
        List<Classes> classes = classesRepository.findByUserUserId(user.getUserId()).get();

        return classes;
    }

    @Override
    @Transactional
    public List<UserClass> getClassesInfo(Long userid, String classname) {

        Classes classes = classesRepository.findFetchJoin(userid,classname).get();

        //classes-UserClass
        List<UserClass> uc =classes.getUserClassList();

        return uc;
    }

    @Override
    public List<UserClass> getClassInfo(Long userid) {
        //Classes classes =classesRepository.findByClassId(classId).get();
        UserClass classes = userClassRepository.findByStudentId(userid).get();


        List<UserClass> uc = classes.getClasses().getUserClassList();
        return uc;
    }

}
