package com.ssafy.api.service;

import com.ssafy.api.request.SourceCode;
import com.ssafy.api.request.TestRecordRegisterPostReq;
import com.ssafy.api.request.TestRegisterPostReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("testRecordService")
@Transactional(readOnly = true)
public class TestRecordServiceImpl implements TestRecordService {

    @Autowired
    TestRecordRepository testRecordRepository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    UserClassRepository userClassRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ConferenceRepositorySupport conferenceRepositorySupport;

    @Override
    @Transactional
    public TestRecord createOrUpdateTestRecord(TestRecordRegisterPostReq testRecordRegisterInfo) {
        Test test=testRepository.findByClassesClassIdAndTestName(testRecordRegisterInfo.getClassId(),testRecordRegisterInfo.getTestName()).get();

        Long testId = test.getTestId();
        Long userId = testRecordRegisterInfo.getUserId();

        Optional<TestRecord> tr = testRecordRepository.findByTestTestIdAndUserUserId(testId, userId);

        TestRecord testRecord = new TestRecord();

        if(tr.isPresent()){

            testRecord = tr.get();

            SourceCode sourceCode = new SourceCode();
            sourceCode.setAnswers(testRecordRegisterInfo.getAnswers());

            testRecord.setSourceCode(sourceCode);

        }else{

            testRecord = new TestRecord();
            SourceCode sourceCode = new SourceCode();

            sourceCode.setAnswers(testRecordRegisterInfo.getAnswers());
            testRecord.setSourceCode(sourceCode);

            User user = userRepository.findByUserId(testRecordRegisterInfo.getUserId()).get();

            UserClass userClass = userClassRepository.findByStudentId(user.getUserId()).get();

            Conference conference = conferenceRepositorySupport.findByClassesClassIdActive(userClass.getUserClassId()).get();


            testRecord.setUser(user);
            testRecord.setTest(test);
            testRecord.setLang(testRecordRegisterInfo.getLang());
            testRecord.setDate(conference.getDate());
        }

        System.out.println("test======================="+testRecord.getDate());
        return testRecordRepository.save(testRecord);
    }


    @Override
    public List<TestRecord> getAllTestRecordByUserId(Long userId) {

        List<TestRecord> testRecords = testRecordRepository.findByUserUserId(userId).get();

        return testRecords;
    }

}
