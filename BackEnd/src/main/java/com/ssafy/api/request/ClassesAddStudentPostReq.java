package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ClassesAddStudentPostRequest")
public class ClassesAddStudentPostReq {

    @ApiModelProperty(name = "담당 강사", example = "1")
    Long tutorId;
    @ApiModelProperty(name = "반 이름", example = "서울 3반")
    String className;
    @ApiModelProperty(name = "학생 식별자", example = "2")
    Long studentId;

}