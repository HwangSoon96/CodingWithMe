<template>
    <div>
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-base.css" />
        <link type="text/css" rel="stylesheet" href="//golden-layout.com/assets/css/goldenlayout-dark-theme.css" />
        <div ref ="test"></div>
    </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from "vue";
import { GoldenLayout } from "golden-layout/src/index";

class MyComponent {

  constructor(container){
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
        const config = {
            root: {
                type: 'row',
                content: [
                {
                    title: 'My Component 1',
                    type: 'component',
                    componentType: 'MyComponent',
                    width: 50,
                },
                {
                    title: 'My Component 2',
                    type: 'component',
                    componentType: 'MyComponent',
                    // componentState: { text: 'Component 2' }
                }
                ]
            }
        };

        var goldenLayout;

        onMounted(() => {
            goldenLayout = new GoldenLayout(test);

            goldenLayout.registerComponent('MyComponent', MyComponent);

            goldenLayout.init();
            goldenLayout.loadLayout(config);
            
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
    }
};
</script>

<style scoped>
@import "https://golden-layout.com/assets/css/goldenlayout-dark-theme.css";
@import "../css/goldenLayout.css";
</style>