package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ClassesModifyPostRequest")
public class ClassesModifyPostReq {
    @ApiModelProperty(name = "담당 강사", example = "1")
    Long tutorId;
    @ApiModelProperty(name = "반 이름", example = "서울 3반")
    String className;
    @ApiModelProperty(name = "반 설명", example = "자바수업입니다.")
    String classDescription;

    String newclassName;



}
