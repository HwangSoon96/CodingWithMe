<template>
	<div>
		<AtomBasicInput
			label="Nickname"
			placeholder="닉네임을 입력해주세요"
			v-model="nickname"
		></AtomBasicInput>
	</div>
</template>

<script>
import { ref } from 'vue';
import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'NicknameInput',
	components: { AtomBasicInput },
	setup() {
		return { nickname: ref('') };
	},
};
</script>

<style></style>
