## compiler 서버

url : http://152.67.201.184:4443/exec
위 주소로 body와 함께 post 요청을 보내면 됩니다.

```
request body
// 요청 보낼때 json에 주석은 지워야 됩니다.
{
    "lang": "c",
    "code": "#include <stdio.h>\n#include<assert.h>\nint main(){\n\tint a,b;\n\tscanf(\"%d\",&a);\n\tscanf(\"%d\",&b);\n\tprintf(\"%d\\n\",a+b);\n\treturn 0;\n}",
    "testcase" :[
        {
            "input" : "5\n10",
            "output" : "15"
        },
        {
            "input" : "1\n3",
            "output" : "4"
        },
        {
            "input" :  "1\n0",
            "output" : "1"
        }
    ]
}


response body(정상 처리)
{
    "status": "success",
    "result": [
        {
            "time": 4,
            "output": "15\n",
            "success": true
        },
        {
            "time": 4,
            "output": "4\n",
            "success": true
        },
        {
            "time": 3,
            "output": "1\n",
            "success": true
        }
    ]
}

response body(서버 오류)
{
      status : "invalid request",
      result : []
}
```
## 설치방법

sudo apt update

sudo apt install nodejs
sudo apt install npm

npm install


OS에 상관없이 컴파일 및 실행을 원하는 언어의 컴파일러나 언어 자체가 설치되어 있어야 한다.


### Windows
C언어 : mingw가 설치 및 환경변수 설정이 되어있어야 한다.
python : python이 설치 및 환경변수 성정이 되어있어야 한다.

### Linux

sudo apt install gcc


## 실행방법

node index.js


## 참고
- VSCode editor : monaco-editor, https://microsoft.github.io/monaco-editor/
- layout : golden-layout, https://golden-layout.com/
