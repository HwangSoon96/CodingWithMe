package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AttendanceRegisterPostReq")
public class AttendanceRegisterPostReq {
    @ApiModelProperty(name = "강의 식별자", example = "1")
    Long conferenceId;

    @ApiModelProperty(name = "사용자 식별자", example = "1")
    Long userId;

}