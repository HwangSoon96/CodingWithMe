package com.ssafy.api.service;


import com.ssafy.api.request.TestRegisterPostReq;
import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.Test;
import com.ssafy.db.repository.ClassesRepository;
import com.ssafy.db.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testService")
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService{

    @Autowired
    TestRepository testRepository;

    @Autowired
    ClassesRepository classesRepository;

    @Override
    @Transactional
    public Test createTest(TestRegisterPostReq testRegisterInfo) {
        Test test =new Test();
        test.setTestName(testRegisterInfo.getTestName());
        test.setTestQno(testRegisterInfo.getTestQno());
        test.setTestPath(testRegisterInfo.getTestPath());
        test.setTestcase(testRegisterInfo.getTestcase());

        Classes classes = classesRepository.findByClassId(testRegisterInfo.getClassId()).get();
        test.setClasses(classes);


        return testRepository.save(test);
    }
}
