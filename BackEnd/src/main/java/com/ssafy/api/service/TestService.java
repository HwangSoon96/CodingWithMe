package com.ssafy.api.service;

import com.ssafy.api.request.TestRegisterPostReq;
import com.ssafy.db.entity.Test;

public interface TestService {
    Test createTest(TestRegisterPostReq testRegisterPostReq);

}
