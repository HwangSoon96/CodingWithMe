<template>
	<div>
		<AtomBasicInput
			v-model="date"
			label="Birth Date"
			type="date"
			stack-label
		></AtomBasicInput>
	</div>
</template>

<script>
import { ref } from 'vue';
import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'BirthInput',
	components: { AtomBasicInput },
	setup() {
		return { date: ref('') };
	},
};
</script>

<style></style>
