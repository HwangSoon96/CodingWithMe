package com.ssafy.common.Rest;

import com.ssafy.api.request.InputOutput;
import com.ssafy.api.request.TestRegisterPostReq;
import com.ssafy.api.response.CompileRes;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestAPI {

    public CompileRes callAPI(String lang, String code, List<InputOutput> ios) {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);

        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
//        map.put("lang", "java");
        map.put("lang", lang);
//        map.put("code", "import java.util.*;\nimport java.io.*;\n\npublic class Main{\n    public static void main(String[] args) throws IOException {\n        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));\n       \n        int a = Integer.parseInt(re.readLine());\n        int b = Integer.parseInt(re.readLine());\n\n        System.out.println(a+b);\n        re.close();\n    }\n}");
        map.put("code",code);

//        ios = new ArrayList<>();
//        InputOutput io1 = new InputOutput();
//        io1.setInput("5\n10");
//        io1.setOutput("15");
//        InputOutput io2 = new InputOutput();
//        io2.setInput("1\n5");
//        io2.setOutput("6");
//
//        ios.add(io1);
//        ios.add(io2);

        map.put("testcase", ios);

        CompileRes response = restTemplate.postForObject("http://152.67.201.184:4443/exec", map, CompileRes.class);


        return response;
    }

}
