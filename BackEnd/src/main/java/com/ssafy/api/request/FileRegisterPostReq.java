package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FileRegisterPostReq")
public class FileRegisterPostReq {
    Long testId;
}
