<template>
    <div>
        <gl-componet>
            <h1>test</h1>
        </gl-componet>
    </div>
</template>

<script>
import { defineComponent } from 'vue';
// import { glComponet, glRow, glCol, glStack} from "vue-golden-layout/src/index.ts";
import "golden-layout";

export default defineComponent({
	name: 'App',

    components: {
        glComponet
    },
});
</script>

<style scoped>
@import 'golden-layout/src/css/goldenlayout-light-theme.css'
/* @import 'golden-layout/src/css/goldenlayout-dark-theme.css' */
/* @import 'golden-layout/src/css/goldenlayout-soda-theme.css' */
/* @import 'golden-layout/src/css/goldenlayout-translucent-theme.css' */
</style>