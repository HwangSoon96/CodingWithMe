package com.ssafy.api.response;

import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 반 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class ClassesRes{
    @ApiModelProperty(name="class ID")
    Long classId;

    public static ClassesRes of(Classes classes) {
        ClassesRes res = new ClassesRes();

        res.setClassId(classes.getClassId());

        return res;
    }
}