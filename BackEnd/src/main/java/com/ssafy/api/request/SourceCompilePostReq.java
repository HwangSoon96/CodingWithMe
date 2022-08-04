package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("SourceCompilePostReq")
public class SourceCompilePostReq {
    String lang;
    String code;
    InputOutput testcase;
}