package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("ClassesRegisterPostRequest")
public class ClassesRegisterPostReq {
    @ApiModelProperty(name = "담당 강사", example = "1")
    Long tutorId;
    @ApiModelProperty(name = "반 이름", example = "서울 3반")
    String className;
    @ApiModelProperty(name = "반 설명", example = "자바수업입니다.")
    String classDescription;
}
