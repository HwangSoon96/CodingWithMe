package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("ProblemCompilePostReq")
public class CodeCompilePostReq {
    @ApiModelProperty(name = "시험 식별자", example = "1")
    Long testId;
    @ApiModelProperty(name = "문제 번호", example = "1")
    int qno;
    @ApiModelProperty(name = "컴파일 언어", example = "c/java/py................")
    String lang;
    @ApiModelProperty(name="소스코드", example= "import java.util.Scanner;\n \npublic class Main {\n \n\tpublic static void main(String[] args) {\n \n\t\tScanner in = new Scanner(System.in);\n\t\tint A = in.nextInt();\n\t\tint B = in.nextInt();\n\t\t\n\t\tSystem.out.println(A+B);\n \n\t\tin.close();\n\t}\n}")
    String code;
}
