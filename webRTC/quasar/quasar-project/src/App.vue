<template>
<div>
	<main-header />
	<router-view />
</div>

</template>

<script>
import MainHeader from './layouts/MainHeader.vue';

export default {
	name: 'App',

	components : {
		MainHeader
	}
};
</script>
