<template>
    <div>
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-base.css" />
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-light-theme.css" />

        <div ref ="test"></div>
    </div>
</template>

<script>
import { createApp, ref, onMounted, onUnmounted, getCurrentInstance, markRaw, defineAsyncComponent, compile, h } from "vue";
import { GoldenLayout } from "golden-layout/src/index";
import WebEditor from "components/WebEditor.vue";
import TestCom from "components/TestCom.vue"
export default {
    name: 'App',

    components: {
    },

    setup(props) {
        const VueApp = { 
            data: {
                title: 'Vue + Golden Layout',
                somevalue: 'Hello Vue :-)'
            },
            methods:{
                resetLayout(){ 
                    localStorage.removeItem('savedState'); 
                    window.location.reload(true);              
                }
            }
        }
        let c = () => h(WebEditor, {
            code : "import java.util.*;\nimport java.io.*;\n\npublic class Main{\n    public static void main(String[] args) throws IOException {\n        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));\n       \n        int a = Integer.parseInt(re.readLine());\n        int b = Integer.parseInt(re.readLine());\n\n        System.out.println(a+b);\n        re.close();\n    }\n}",
            language : "java",
            readonly : false
        });
        let d = () => h(TestCom, {});
        const test = ref(undefined);
        let goldenLayout;
        const config = {
            content:
            [
                { 
                    type: 'row',
                    content:
                    [
                        {
                            type: 'component',
                            componentName: 'WebEditor',
                            componentType : 'WebEditor'
                        },
                        {
                            type: 'component',
                            componentName: 'TestCom',
                            componentType : 'TestCom'
                            
                        }      
                    ]          
                }
            ]
        } 

        onMounted(() => {
            console.log(c());
            goldenLayout = new GoldenLayout(test);
            goldenLayout.registerComponent('WebEditor', c);
            goldenLayout.registerComponent('TestCom', d);
            // goldenLayout.registerComponent('template', function(container, state){
            //     const uniqueID = `${Math.ceil(Math.random() * Date.now())}`;
            //     const html = `<div id="${uniqueID}"><h2>ffafd</h2></div>`;
            //     container.element.innerHtml = html;          
            //     setTimeout(() => {  
            //         WebEditor.el =  `#${uniqueID}`; 
            //         WebEditor.render = h => h(state.vueTemplate);
            //         createApp(WebEditor); 
            //     });
            // });
            goldenLayout.init();
            goldenLayout.loadLayout(config);

            const glc = markRaw(defineAsyncComponent(() => import("../components/WebEditor.vue")));


            // goldenLayout.registerComponent('MyComponent', MyComponent);

            // goldenLayout.registerComponent('chat', function(container){
            //     this.rootElement = container.element;
            //     this.rootElement.innerHTML = '<img src="src/assets/chat.png" alt="">';
            //     this.resizeWithContainerAutomatically = true;
            // });

            

            // goldenLayout.registerComponent('others', function(container){
            //     this.rootElement = container.element;
            //     this.rootElement.innerHTML = '<img src="src/assets/others.png" alt="">';
            //     this.resizeWithContainerAutomatically = true;
            // });
            
            // goldenLayout.on('stateChanged',function(some){
                
            // });
            
            
        });

        onUnmounted(() => {
            goldenLayout.destroy();
        });

        return {
            test
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