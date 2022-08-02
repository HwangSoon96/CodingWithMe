<template>
    <div>
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-base.css" />
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-light-theme.css" />

        <div ref ="test"></div>
    </div>
</template>

<script>
import {shallowRef , ref, onMounted, onUnmounted, h } from "vue";
import { GoldenLayout } from "golden-layout/src/index";
import WebEditor from "components/WebEditor.vue";
import TestCom from "components/TestCom.vue"
export default {
    name: 'App',

    components: {
    },

    setup(props) {
        let c = () => h(WebEditor, {
            code : "import java.util.*;\nimport java.io.*;\n\npublic class Main{\n    public static void main(String[] args) throws IOException {\n        BufferedReader re = new BufferedReader(new InputStreamReader(System.in));\n       \n        int a = Integer.parseInt(re.readLine());\n        int b = Integer.parseInt(re.readLine());\n\n        System.out.println(a+b);\n        re.close();\n    }\n}",
            language : "java",
            readonly : false
        });
        let d = () => h(TestCom);

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
            goldenLayout = new GoldenLayout(test);
            
            goldenLayout.registerComponent('WebEditor', c);
            goldenLayout.registerComponent('TestCom', d);

            goldenLayout.init();
            goldenLayout.loadLayout(config);
            
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
</style>