package com.ssafy.api.response;

import com.ssafy.api.request.UserIdEmailReq;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("MailSender")
public class MailSenderRes{
    @ApiModelProperty(name="User ID")
    String id;
    String mail;

    public static MailSenderRes of(UserIdEmailReq user) {
        MailSenderRes res = new MailSenderRes();
        res.setId(user.getId());
        res.setMail(user.getMail());
        return res;
    }
}
