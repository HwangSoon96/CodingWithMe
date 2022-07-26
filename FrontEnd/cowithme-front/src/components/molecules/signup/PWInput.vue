<template>
	<div>
		<q-input
			v-model="password"
			filled
			color="brand"
			bg-color="white"
			label="PW"
			placeholder="비밀번호를 입력해주세요"
			type="password"
			hint="Password"
		></q-input>
	</div>
</template>

<script>
import { ref } from 'vue';

// import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'PWInput',
	// components: { AtomBasicInput },
	setup() {
		return { password: ref('') };
	},
};
</script>

<style></style>
