package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TestPostReq")
public class TestRegisterPostReq {
    @ApiModelProperty(name = "반 식별자", example = "1")
    Long classId;
    @ApiModelProperty(name = "제목", example = "다익스트라 응용문제")
    String testName;
    @ApiModelProperty(name = "문항 수", example = "3")
    int testQno;
    @ApiModelProperty(name="경로", example= "/path/..")
    String testPath;
    @ApiModelProperty(name="테스트케이스",example="..")
    String testcase;
}