package com.ssafy.api.service;

import com.ssafy.api.request.*;
import com.ssafy.api.response.CompileRes;
import com.ssafy.common.Rest.RestAPI;
import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.Test;
import com.ssafy.db.entity.TestRecord;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ClassesRepository;
import com.ssafy.db.repository.TestRecordRepository;
import com.ssafy.db.repository.TestRepository;
import com.ssafy.db.repository.TestRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("testService")
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestRepositorySupport testRepositorySupport;

    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    TestRecordRepository testRecordRepository;

    @Override
    @Transactional
    public Test createTest(TestRegisterPostReq testRegisterInfo) {
        Test test = new Test();
        test.setTestName(testRegisterInfo.getTestName());
        test.setTestQno(testRegisterInfo.getTestQno());
        TestCase tc = new TestCase();
        tc.setTestcaseList(testRegisterInfo.getTestcaseList());
        test.setTestcase(tc);

        Classes classes = classesRepository.findByClassId(testRegisterInfo.getClassId()).get();
        test.setClasses(classes);


        return testRepository.save(test);
    }

    @Override
    @Transactional
    public Test modifyTest(TestModifyPostReq testModifyPostReq) {
        Test test = testRepository.findByClassesClassIdAndTestName(testModifyPostReq.getClassId(), testModifyPostReq.getTestName()).get();
        test.setTestName(testModifyPostReq.getNewtestName());
        test.setTestQno(testModifyPostReq.getTestQno());

        return testRepository.save(test);
    }

    @Override
    @Transactional
    public boolean deleteTest(TestRegisterPostReq testRegisterPostReq) {
        boolean success = false;
        try {
            Test test = testRepository.findByClassesClassIdAndTestName(testRegisterPostReq.getClassId(), testRegisterPostReq.getTestName()).get();

            testRepository.delete(test);

            success = true;
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    @Override
    public Test getTestInfo(Long classId, String testName) {

        Test test = testRepository.findByClassesClassIdAndTestName(classId,testName).get();

        return test;
    }

    @Override
    public List<Test> getAllTestInfo(Long classId) {

        Classes classes = classesRepository.findByClassId(classId).get();
        List<Test> test = classes.getTestList();
        return test;
    }

    @Override
    public boolean checkTestName(String testName,Long classId) {
        boolean result = testRepositorySupport.findByTestNameEquals(testName,classId);
        return result;
    }
    @Override
    public CompileRes problemCompile(CodeCompilePostReq codeCompilePostReq) {

        String lang = codeCompilePostReq.getLang();
        String code = codeCompilePostReq.getCode();

        Test test = testRepository.findById(codeCompilePostReq.getTestId()).get();
        TestCase testCase = test.getTestcase();
        List<InputOutput> ios = testCase.getTestcaseList().get(codeCompilePostReq.getQno()-1).getTestcase();

        RestAPI api = new RestAPI();
        CompileRes res = api.callAPI(lang, code, ios);

        return res;
    }

    @Override
    @Transactional
    public boolean testCompile(TestCompilePostReq testCompilePostReq) {

        Long testId = testCompilePostReq.getTestId();
        Long userId = testCompilePostReq.getUserId();
        Test test = testRepository.findById(testId).get();
        TestCase testcase = test.getTestcase();
        List<TC> testcaseList = testcase.getTestcaseList();


        TestRecord testRecord = testRecordRepository.findByTestTestIdAndUserUserId(testId, userId).get();

        String lang = testRecord.getLang();

        RestAPI api = new RestAPI();

        List<Answer> answers = testRecord.getSourceCode().getAnswers();
        Collections.sort(answers);
        Collections.sort(testcaseList);

        int count = 0;

        for (int qno = 0; qno < test.getTestQno(); qno++){

            Answer answer = answers.get(qno);
            String code = answer.getCode();
            List<InputOutput> ios = testcaseList.get(qno).getTestcase();
            CompileRes res = api.callAPI(lang,code, ios);

            List<Comp> results = res.getResult();

            boolean success = true;

            for (Comp result : results) {
                if(!result.isSuccess()){
                    success = false;
                }
            }

            if(success){
                count++;
            }

        }

        testRecord.setCorrectCount(count);

        testRecordRepository.save(testRecord);

        return true;
    }

}