<template>
	<div>
		<AtomBasicInput
			label="Name"
			placeholder="이름을 입력해주세요"
			v-model="text"
		></AtomBasicInput>
	</div>
</template>

<script>
import { ref } from 'vue';
import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'NameInput',
	components: { AtomBasicInput },
	setup() {
		return { text: ref('') };
	},
};
</script>

<style></style>
