<template>
	<div>
		<q-input
			:type="isPwd ? 'password' : 'text'"
			label="PW Confirm"
			placeholder="비밀번호를 한번 더 입력해주세요"
			v-model="password"
			filled
			color="brand"
			bg-color="white"
		>
			<template v-slot:append>
				<q-icon
					:name="isPwd ? 'visibility_off' : 'visibility'"
					class="cursor-pointer"
					@click="isPwd = !isPwd"
				></q-icon>
			</template>
		</q-input>
	</div>
</template>

<script>
import { ref } from 'vue';

// import AtomBasicInput from 'src/components/atoms/AtomBasicInput.vue';
export default {
	name: 'PWConfirmInput',
	// components: { AtomBasicInput },
	setup() {
		return { password: ref(''), isPwd: ref(true) };
	},
};
</script>

<style></style>
