<template>
	<div class="right">
		<div class="container">
			<FindIDPWText></FindIDPWText>
			<div class="form-container">
				<IDInput class="component"></IDInput>
				<EmailInput class="component"></EmailInput>
			</div>
			<FindButton class="find"></FindButton>
		</div>
	</div>
</template>

<script>
import FindIDPWText from 'src/components/molecules/find/FindIDPWText.vue';
import IDInput from 'src/components/molecules/find/IDInput.vue';
import EmailInput from 'src/components/molecules/find/EmailInput.vue';
import FindButton from 'src/components/molecules/find/FindButton.vue';
export default {
	name: 'RightPWForm',
	setup() {
		return {};
	},
	components: { FindIDPWText, IDInput, EmailInput, FindButton },
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
	margin-right: 100px;
}

.component {
	margin-bottom: 20px;
}

.find {
	margin-top: 20px;
}
</style>
