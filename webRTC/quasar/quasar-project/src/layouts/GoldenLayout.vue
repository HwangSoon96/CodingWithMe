<template>
    <div>
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-base.css" />
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-dark-theme.css" />

        <div ref ="test"></div>
    </div>
</template>

<script>

import { ref, onMounted, onUnmounted, getCurrentInstance } from "vue";
import { GoldenLayout } from "golden-layout/src/index";
import * as monaco from 'monaco-editor';
class MyComponent {
    constructor(container, state){
        console.log("test : ", state);
    this.rootElement = container.element; 
    this.rootElement.innerHTML = '<h2>' + 'Component Type: MyComponent' + '</h2>';
    this.resizeWithContainerAutomatically = true;
  }
}

export default {
    name: 'App',

    components: {
        },

    setup(props) {
        const test = ref(undefined);
        const editor = ref(undefined);
        let goldenLayout;
        let myIde;
        let teacherIde;
        let code = "import java.util.*;\nimport java.io.*;\n\npublic class Main{\n    public static void main(String[] args) throws IOException {\n        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));\n       \n        int a = Integer.parseInt(re.readLine());\n\n        System.out.println(a+b);\n        re.close();\n    }\n}";
        let teacherCode = "import java.util.*;\nimport java.io.*;\n\npublic class Main{\n    public static void main(String[] args) throws IOException {\n        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));\n       \n        int a = Integer.parseInt(re.readLine());\n        int b = Integer.parseInt(re.readLine());\n\n        System.out.println(a+b);\n        re.close();\n    }\n}"
        
        const config = {
            root: {
                type: 'row',
                content: [
                    {
                        type: 'column',
                        content : [
                            {
                                title: 'others',
                                type: 'component',
                                componentType: 'others',
                                width: 60,
                                height: 10,
                                componentState: { text: 'others' }
                            },
                            {
                                title: 'my video',
                                type: 'component',
                                componentType: 'me',
                                width: 60,
                                componentState: { text: 'my video' }
                            }
                        ]
                    },
                    {
                        title: 'Teacher Code',
                        type: 'component',
                        componentType: 'teacherEditor',
                        width: 40,
                    },
                    {
                        title: 'My Code',
                        type: 'component',
                        componentType: 'myEditor',
                        width: 40,
                    },
                    {
                        type : 'column',
                        content : [
                            {
                                title: 'chat',
                                type: 'component',
                                componentType: 'chat',
                                // componentState: { text: 'Component 2' }
                            },
                        ],
                        width: 30
                    }
                ]
            }
        };


        onMounted(() => {
            goldenLayout = new GoldenLayout(test);

            // goldenLayout.registerComponent('MyComponent', MyComponent);
            goldenLayout.registerComponent('MyComponent', function(container, state){
                console.log(state.text);
                this.rootElement = container.element;
                if(state.text)
                    this.rootElement.innerHTML = '<h2>' + state.text + '</h2>';
                else
                    this.rootElement.innerHTML = '<h2>' + 'Component Type: MyComponent' + '</h2>';
                this.resizeWithContainerAutomatically = true;
            });

            goldenLayout.registerComponent('chat', function(container){
                this.rootElement = container.element;
                this.rootElement.innerHTML = '<img src="src/assets/chat.png" alt="">';
                this.resizeWithContainerAutomatically = true;
            });
            goldenLayout.registerComponent('me', function(container){
                this.rootElement = container.element;
                this.rootElement.innerHTML = '<img src="src/assets/me.png" alt="" style="width:100% height:100%">';
                this.resizeWithContainerAutomatically = true;
            });

            goldenLayout.registerComponent('others', function(container){
                this.rootElement = container.element;
                this.rootElement.innerHTML = '<img src="src/assets/others.png" alt="">';
                this.resizeWithContainerAutomatically = true;
            });

            goldenLayout.registerComponent('MyComponentA', function(container){
                this.rootElement = container.element;
                this.rootElement.innerHTML = '<h2>' + 'CTest' + '</h2>';
                this.resizeWithContainerAutomatically = true;
            });

            goldenLayout.registerComponent( 'myEditor',function(container,state){
                this.rootElement = container.element;
                this.rootElement.innerHTML = '<div id="myEditor" style="height: 100%; width:100%;"></div>';
                this.resizeWithContainerAutomatically = true;
            });

            goldenLayout.registerComponent( 'teacherEditor',function(container,state){
                this.rootElement = container.element;
                this.rootElement.innerHTML = '<div id="teacherEditor" style="height: 100%; width:100%;"></div>';
                this.resizeWithContainerAutomatically = true;
            });
            

            
            goldenLayout.init();
            goldenLayout.loadLayout(config);


            // console.log(editor);
            // console.log(getCurrentInstance().refs.editor);

            // ay = monaco.editor.create(document.getElementById('editor'),{
            //     value: 'import java.util.*;\nimport java.io.*;\n\npublic class Main{\n    public static void main(String[] args) throws IOException {\n        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));\n       \n        int a = Integer.parseInt(re.readLine());\n        int b = Integer.parseInt(re.readLine());\n\n        System.out.println(a+b);\n        re.close();\n    }\n}',
            //     // abap,aes,apex,azcli,bat,c,cameligo,clojure,coffeescript,cpp,csharp,csp,css,
            //     // dart,dockerfile,fsharp,go,graphql,handlebars,hcl,html,ini,java,javascript,
            //     // json,julia,kotlin,less,lexon,lua,markdown,mips,msdax,mysql,objective-c,pascal,
            //     // pascaligo,perl,pgsql,php,plaintext,postiats,powerquery,powershell,pug,python,
            //     // r,razor,redis,redshift,restructuredtext,ruby,rust,sb,scala,scheme,scss,shell,
            //     // sol,sql,st,swift,systemverilog,tcl,twig,typescript,vb,verilog,xml,yaml
            //     language: 'java',
            //     // theme: 'vs', //light version
            //     theme: 'vs-dark',
            //     tabSize: 2,
            //     fontFamily: "Consolas",
            //     // fontFamily: 'D2Coding',
            //     // fontFamily: 'IBM Plex Mono',
            //     fontSize: 12,
            // });

            
            goldenLayout.on('stateChanged',function(some){
                
                // var editorValue = ay.getValue();
                // console.log(ay.getModel().getValue());
                if(myIde){
                    code = myIde.getValue();
                    myIde.dispose();
                }
                myIde = monaco.editor.create(document.getElementById('myEditor'),{
                    // model: null,
                    readOnly: false,
                    value: code,
                    // abap,aes,apex,azcli,bat,c,cameligo,clojure,coffeescript,cpp,csharp,csp,css,
                    // dart,dockerfile,fsharp,go,graphql,handlebars,hcl,html,ini,java,javascript,
                    // json,julia,kotlin,less,lexon,lua,markdown,mips,msdax,mysql,objective-c,pascal,
                    // pascaligo,perl,pgsql,php,plaintext,postiats,powerquery,powershell,pug,python,
                    // r,razor,redis,redshift,restructuredtext,ruby,rust,sb,scala,scheme,scss,shell,
                    // sol,sql,st,swift,systemverilog,tcl,twig,typescript,vb,verilog,xml,yaml
                    language: 'java',
                    // theme: 'vs', //light version
                    theme: 'vs-dark',
                    tabSize: 2,
                    fontFamily: "Consolas",
                    // fontFamily: 'D2Coding',
                    // fontFamily: 'IBM Plex Mono',
                    fontSize: 12,
                });

                if(teacherIde){
                    teacherCode = teacherIde.getValue();
                    teacherIde.dispose();
                }

                teacherIde = monaco.editor.create(document.getElementById('teacherEditor'),{
                    // model: null,
                    readOnly: true,
                    value: teacherCode,
                    // abap,aes,apex,azcli,bat,c,cameligo,clojure,coffeescript,cpp,csharp,csp,css,
                    // dart,dockerfile,fsharp,go,graphql,handlebars,hcl,html,ini,java,javascript,
                    // json,julia,kotlin,less,lexon,lua,markdown,mips,msdax,mysql,objective-c,pascal,
                    // pascaligo,perl,pgsql,php,plaintext,postiats,powerquery,powershell,pug,python,
                    // r,razor,redis,redshift,restructuredtext,ruby,rust,sb,scala,scheme,scss,shell,
                    // sol,sql,st,swift,systemverilog,tcl,twig,typescript,vb,verilog,xml,yaml
                    language: 'java',
                    // theme: 'vs', //light version
                    theme: 'vs-dark',
                    tabSize: 2,
                    fontFamily: "Consolas",
                    // fontFamily: 'D2Coding',
                    // fontFamily: 'IBM Plex Mono',
                    fontSize: 12,
                });
            });
            
            // myLayout = new GoldenLayout( config, test );
            // myLayout.registerComponent( 'testComponent', function( container, componentState ){
            //     console.log(container);
            //     container.element.innerHTML = '<h2>' + componentState.label + '</h2>';
            // });

            // myLayout.init();
            
        });

        onUnmounted(() => {
            goldenLayout.destroy();
        });

        return {
            test,
            editor,
            myIde
        };
    }
};
</script>

<style scoped>
@import "https://golden-layout.com/assets/css/goldenlayout-dark-theme.css";
@import "../css/goldenLayout.css";

img {
    border: 5px solid #FFFF00;
    text-align: center;
}
</style>