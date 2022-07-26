<template>
	<div>
		<q-input
			v-model="email"
			type="email"
			filled
			color="brand"
			bg-color="white"
			label="Email"
		></q-input>
	</div>
</template>

<script>
import { ref } from 'vue';
// import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'EmailInput',
	// components: { AtomBasicInput },
	setup() {
		return { email: ref('') };
	},
};
</script>

<style></style>
