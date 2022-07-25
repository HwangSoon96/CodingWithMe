<template>
	<div class="container base">
		<AtomLogo class="atom-logo"></AtomLogo>
		<div class="form-container">
			<RegisterText class="register-text"></RegisterText>
			<div class="container flex row">
				<div class="left-container col">
					<NameInput class="component"></NameInput>
					<div class="flex row">
						<IDInput class="component col-9"></IDInput>
						<div class="col-1"></div>
						<IDChkButton class="component col-2"></IDChkButton>
					</div>
					<PWInput class="component"></PWInput>
					<PWConfirmInput class="component"></PWConfirmInput>
				</div>
				<div class="right-container col">
					<div class="flex row">
						<EmailInput class="component col-9"></EmailInput>
						<div class="col-1"></div>
						<EmailChkButton
							@click="chkEmail"
							class="component col-2"
						></EmailChkButton>
					</div>
					<PhoneInput class="component"></PhoneInput>
					<NicknameInput class="component"></NicknameInput>
					<BirthInput class="component"></BirthInput>
				</div>
			</div>
		</div>
		<AtomRadio class="bottom"></AtomRadio>
		<RegisterButton class="bottom"></RegisterButton>
	</div>
</template>

<script>
import RegisterText from 'src/components/molecules/signup/RegisterText.vue';
import NameInput from 'src/components/molecules/signup/NameInput.vue';
import IDInput from 'src/components/molecules/signup/IDInput.vue';
import PWInput from 'src/components/molecules/signup/PWInput.vue';
import PWConfirmInput from 'src/components/molecules/signup/PWConfirmInput.vue';
import EmailInput from '../../molecules/signup/EmailInput.vue';
import PhoneInput from 'src/components/molecules/signup/PhoneInput.vue';
import NicknameInput from 'src/components/molecules/signup/NicknameInput.vue';
import BirthInput from '../../molecules/signup/BirthInput.vue';
import RegisterButton from 'src/components/molecules/signup/RegisterButton.vue';
import AtomRadio from 'src/components/atoms/AtomRadio.vue';
import IDChkButton from 'src/components/molecules/signup/IDChkButton.vue';
import EmailChkButton from 'src/components/molecules/signup/EmailChkButton.vue';
import AtomLogo from 'src/components/atoms/AtomLogo.vue';
import { useQuasar } from 'quasar';

export default {
	name: 'BaseSquare',
	components: {
		RegisterText,
		NameInput,
		IDInput,
		PWInput,
		PWConfirmInput,
		EmailInput,
		PhoneInput,
		NicknameInput,
		BirthInput,
		RegisterButton,
		AtomRadio,
		IDChkButton,
		EmailChkButton,
		AtomLogo,
	},
	setup() {
		const $q = useQuasar();
		function chkEmail() {
			$q.dialog({
				title: '이메일 인증',
				message: '인증번호를 입력해주세요',
				prompt: {
					model: '',
					type: 'text', // optional
				},
				cancel: true,
				persistent: true,
			});
		}
		return { chkEmail };
	},
};
</script>

<style lang="scss" scoped>
.base {
	height: 800px;
	width: 1200px;
	background-color: #303841;
	box-shadow: 0px 0px 20px #303841;
	border-radius: 20px;
	display: flex;
	flex-direction: column;
	padding: 20px;
}
.atom-logo {
	width: 20%;
}

.register-text {
	font-size: 2.5rem;
}

.bottom {
	display: flex;
	justify-content: center;
	align-items: center;
}

.form-container {
	padding: 0 50px 50px 50px;
}

.left-container {
	margin-right: 20px;
}

.right-container {
	margin-left: 20px;
}

.component {
	margin-bottom: 20px;
}
</style>
