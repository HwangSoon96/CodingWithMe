package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@ApiModel("AttendanceRes")
public class AttendanceRes {

    @ApiModelProperty(name="사용자 식별자", example="1")
    Long userId;

    List<AttInfo> attInfos;

}
