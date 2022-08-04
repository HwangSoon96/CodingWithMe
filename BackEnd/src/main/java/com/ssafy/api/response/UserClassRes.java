package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserClassRes")
public class UserClassRes {
    @ApiModelProperty(name="사용자 식별자", example="2")
    Long userId;

    @ApiModelProperty(name="학생 아이디", example="hong")
    String id;

    @ApiModelProperty(name="학생 이름", example="1")
    String name;

    @ApiModelProperty(name="반 식별자", example="1")
    Long classId;

    @ApiModelProperty(name="반 이름", example="서울 3반")
    String className;

    @ApiModelProperty(name="강사 이름", example="장길산")
    String tutorName;

    @ApiModelProperty(name="사용자 식별자", example="1")
    String tutorId;


}
