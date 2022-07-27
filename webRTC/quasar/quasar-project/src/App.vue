<template>
<div>
	<router-link to="/">main</router-link>
    <br>
    <router-link to="/layout">info</router-link>
	<router-view />
</div>

</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
	name: 'App',
});
</script>
