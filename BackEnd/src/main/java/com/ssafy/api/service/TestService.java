package com.ssafy.api.service;

import com.ssafy.api.request.CodeCompilePostReq;
import com.ssafy.api.request.TestCompilePostReq;
import com.ssafy.api.request.TestModifyPostReq;
import com.ssafy.api.request.TestRegisterPostReq;
import com.ssafy.api.response.CompileRes;
import com.ssafy.db.entity.Test;

import java.util.List;

public interface TestService {
    Test createTest(TestRegisterPostReq testRegisterPostReq);

    Test modifyTest(TestModifyPostReq testModifyPostReq);

    boolean deleteTest(TestRegisterPostReq testRegisterPostReq);

    Test getTestInfo(Long classId, String testName);
    List<Test> getAllTestInfo(Long classId);
    boolean checkTestName(String testName,Long classId);
    CompileRes problemCompile(CodeCompilePostReq codeCompilePostReq);

    boolean testCompile(TestCompilePostReq testCompilePostReq);
}
