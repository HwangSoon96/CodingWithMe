<template>
	<div>
		<AtomBasicInput
			v-model="number"
			label="Phone Number"
			placeholder="전화번호를 입력해주세요"
		></AtomBasicInput>
	</div>
</template>

<script>
import { ref } from 'vue';
import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'PhoneInput',
	components: { AtomBasicInput },
	setup() {
		return { number: ref('') };
	},
};
</script>

<style></style>
