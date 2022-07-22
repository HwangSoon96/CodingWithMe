package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("UserEmailCodeReq")
public class UserEmailCodeReq {
    @ApiModelProperty(name="유저 Mail", example="kdw508@naver.com")
    String mail;
    @ApiModelProperty(name="인증메일 코드", example="111111")
    String code;
}
