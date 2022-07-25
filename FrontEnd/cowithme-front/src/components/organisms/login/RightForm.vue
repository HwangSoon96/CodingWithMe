<template>
	<div class="right">
		<div class="container">
			<LoginText class="component"></LoginText>
			<div class="form-container">
				<IDInput class="component"></IDInput>
				<PWInput class="component"></PWInput>
			</div>
			<FindIDPW class="find"></FindIDPW>
			<LoginButton class="login-button"></LoginButton>
			<SelectRoleButton class="role-button"></SelectRoleButton>
		</div>
	</div>
</template>

<script>
import LoginText from 'src/components/molecules/login/LoginText.vue';
import IDInput from 'src/components/molecules/login/IDInput.vue';
import PWInput from 'src/components/molecules/login/PWInput.vue';
import FindIDPW from 'src/components/molecules/login/FindIDPW.vue';
import LoginButton from 'src/components/molecules/login/LoginButton.vue';
import SelectRoleButton from 'src/components/molecules/login/SelectRoleButton.vue';

export default {
	name: 'RightForm',
	setup() {
		return {};
	},
	components: {
		LoginText,
		IDInput,
		PWInput,
		FindIDPW,
		LoginButton,
		SelectRoleButton,
	},
};
</script>

<style lang="scss" scoped>
.right {
	height: 800px;
	width: 500px;
	background-color: white;
}

.container {
	margin-top: 100px;
	margin-left: 50px;
}

.form-container {
	margin-top: 50px;
	margin-right: 50px;
}

.component {
	margin-bottom: 20px;
}

.find {
	margin-top: 20px;
}

.login-button {
	margin-top: 50px;
}

.role-button {
	margin-top: 30px;
}
</style>
