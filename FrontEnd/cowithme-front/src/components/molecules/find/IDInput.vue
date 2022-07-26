<template>
	<div>
		<AtomBasicInput
			label="ID"
			placeholder="아이디를 입력해주세요"
			v-model="text"
		></AtomBasicInput>
	</div>
</template>

<script>
import { ref } from 'vue';

import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'IDInput',
	components: { AtomBasicInput },
	setup() {
		return { text: ref('') };
	},
};
</script>

<style></style>
