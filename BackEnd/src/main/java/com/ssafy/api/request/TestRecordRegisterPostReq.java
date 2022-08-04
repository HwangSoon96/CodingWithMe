package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("TestRecordRegisterPostReq")
public class TestRecordRegisterPostReq {
    @ApiModelProperty(name = "사용자 식별자", example = "2")
    Long userId;

    @ApiModelProperty(name = "반 식별자", example = "1")
    Long classId;

    @ApiModelProperty(name = "시험 이름", example = "1차시험")
    String testName;

    @ApiModelProperty(name = "소스코드", example = "[ { \"qno\" : 1, \"code\" : \"import java.util.Scanner;\n \npublic class Main {\n \n\tpublic static void main(String[] args) {\n \n\t\tScanner in = new Scanner(System.in);\n\t\tint A = in.nextInt();\n\t\tint B = in.nextInt();\n\t\t\n\t\tSystem.out.println(A+B);\n \n\t\tin.close();\n\t}\n}\" }," +
            " { \"qno\" : 2, \"code\" : \"import java.util.Scanner;\n \npublic class Main {\n \n\tpublic static void main(String[] args) {\n \n\t\tScanner in = new Scanner(System.in);\n\t\tint A = in.nextInt();\n\t\tint B = in.nextInt();\n\t\t\n\t\tSystem.out.println(A+B);\n \n\t\tin.close();\n\t}\n}\" } ]")
    List<Answer> answers;

    @ApiModelProperty(name = "언어정보", example = "java/py/c ..............")
    String lang;



}
