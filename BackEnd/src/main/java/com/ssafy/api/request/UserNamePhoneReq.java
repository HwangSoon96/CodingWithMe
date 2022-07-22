package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserNamePhoneReq")
public class UserNamePhoneReq {
    @ApiModelProperty(name="유저 이름", example="김싸피")
    String name;
    @ApiModelProperty(name="유저 전화번호", example="010-1111-2222")
    String phone;
}
