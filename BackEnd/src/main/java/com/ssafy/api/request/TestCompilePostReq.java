package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TestCompilePostReq")
public class TestCompilePostReq {
    @ApiModelProperty(name = "사용자 식별자", example = "1")
    Long userId;
    @ApiModelProperty(name = "시험 식별자", example = "1")
    Long testId;
}
