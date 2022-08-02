<template>
    <div>
        <div ref="editorDiv" style="height: 100%; width:100%"></div>
        <div><h2 @click="updateEditor">refresh</h2></div>
    </div>
</template>

<script>

// package.json에 의존성 추가
// "monaco-editor": "^0.33.0",
// "monaco-editor-webpack-plugin": "^7.0.1",

// quasar.confg에 아래 내용 추가 필요.
// const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
// module.exports = configure(function (/* ctx */) {
//   return {
//     plugins: [new MonacoWebpackPlugin()],

import { ref, onMounted } from "vue";

import * as monaco from 'monaco-editor';
export default {
    // example
    // 
    // <web-editor code="import java.util.*" language="java" :readOnly="false"></web-editor>
    name : 'WebEditor',
    props :{
        code : String, // "import java.util.*;\nimport java.io.*;\n\npublic class Main{\n    public static void main(String[] args) throws IOException {\n        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));\n       \n        int a = Integer.parseInt(re.readLine());\n        int b = Integer.parseInt(re.readLine());\n\n        System.out.println(a+b);\n        re.close();\n    }\n}"
        language : String, // "java", "c", "python"
        readOnly : Boolean, // "false"
    },
    setup (props) {
        const editorDiv = ref(undefined);
        let monacoEditor;

        // props 값을 복사하기 위한 꼼수
        let editorCode = JSON.parse(JSON.stringify(props.code));  
        let editorLanguage = JSON.parse(JSON.stringify(props.language));
        let editorReadOnly = JSON.parse(JSON.stringify(props.readOnly));

        onMounted(() => {
            monacoEditor = monaco.editor.create(editorDiv.value,{
                    // model: null,
                    readOnly: editorReadOnly,
                    value: editorCode,
                    // abap,aes,apex,azcli,bat,c,cameligo,clojure,coffeescript,cpp,csharp,csp,css,
                    // dart,dockerfile,fsharp,go,graphql,handlebars,hcl,html,ini,java,javascript,
                    // json,julia,kotlin,less,lexon,lua,markdown,mips,msdax,mysql,objective-c,pascal,
                    // pascaligo,perl,pgsql,php,plaintext,postiats,powerquery,powershell,pug,python,
                    // r,razor,redis,redshift,restructuredtext,ruby,rust,sb,scala,scheme,scss,shell,
                    // sol,sql,st,swift,systemverilog,tcl,twig,typescript,vb,verilog,xml,yaml
                    language: editorLanguage,
                    // theme: 'vs', //light version
                    theme: 'vs-dark',
                    tabSize: 2,
                    fontFamily: "Consolas",
                    // fontFamily: 'D2Coding',
                    // fontFamily: 'IBM Plex Mono',
                    fontSize: 12,
                });
        });
        
        // Golden Layout에서 화면이 갱신되었을 때,
        // updateEditor를 호출해 IDE를 다시 불러와야 한다.
        const updateEditor = () => {
            editorCode = monacoEditor.getValue();
            monacoEditor.dispose();
    
            monacoEditor = monaco.editor.create(editorDiv.value,{
                    // model: null,
                    readOnly: editorReadOnly,
                    value: editorCode,
                    // c,cpp,java,javascript,python
                    language: editorLanguage,
                    // theme: 'vs', //light version
                    theme: 'vs-dark',
                    tabSize: 2,
                    fontFamily: "Consolas",
                    fontSize: 12,
                });
        };

        return {
            editorDiv,
            monacoEditor,
            editorCode,
            editorLanguage,
            editorReadOnly,
            
            // Golden Layout에서 화면이 갱신되었을 때,
            // updateEditor를 호출해 IDE를 다시 불러와야 한다.
            updateEditor
        };
    }
}
</script>

<style lang="scss" scoped>

</style>