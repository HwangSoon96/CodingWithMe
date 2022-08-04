package com.ssafy.api.service;

import com.ssafy.api.request.TestRecordRegisterPostReq;
import com.ssafy.api.request.TestRegisterPostReq;
import com.ssafy.db.entity.TestRecord;

import java.util.List;

public interface TestRecordService {
    TestRecord createOrUpdateTestRecord(TestRecordRegisterPostReq testRecordRegisterPostReq);

    List<TestRecord> getAllTestRecordByUserId(Long userId);

}
