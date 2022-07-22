package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserIdEmailReq")
public class UserIdEmailReq {
    @ApiModelProperty(name="유저 ID", example="ssafy")
    String id;
    @ApiModelProperty(name="유저 Password", example="ssafy@ssafy.com")
    String mail;
}
