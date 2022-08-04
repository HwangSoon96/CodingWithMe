package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("TestPostReq")
public class TestRegisterPostReq {
    @ApiModelProperty(name = "반 식별자", example = "1")
    Long classId;
    @ApiModelProperty(name = "제목", example = "다익스트라 응용문제")
    String testName;

    @ApiModelProperty(name = "문항 수", example = "2")
    int testQno;

    @ApiModelProperty(name="테스트케이스",example= "[\n" +
            "    {\n" +
            "      \"qno\": 1,\n" +
            "      \"testcase\": [\n" +
            "         {\n" +
            "            \"input\" : \"5\\n10\",\n" +
            "            \"output\" : \"15\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"input\" : \"1\\n3\",\n" +
            "            \"output\" : \"4\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"qno\": 2,\n" +
            "      \"testcase\": [\n" +
            "         {\n" +
            "            \"input\" : \"10\\n10\",\n" +
            "            \"output\" : \"20\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"input\" : \"5\\n3\",\n" +
            "            \"output\" : \"8\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]")
    List<TC> testcaseList;




}