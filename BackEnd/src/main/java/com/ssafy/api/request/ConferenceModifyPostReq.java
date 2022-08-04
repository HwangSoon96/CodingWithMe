package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ConferenceModifyPostReq")
public class ConferenceModifyPostReq {
    @ApiModelProperty(name = "반 식별자", example = "1")
    Long classId;

    @ApiModelProperty(name = "방장 사용자 식별자", example = "1")
    Long ownerId;

    @ApiModelProperty(name = "방제", example = "1주차 알고리즘 수업")
    String conferenceName;

}