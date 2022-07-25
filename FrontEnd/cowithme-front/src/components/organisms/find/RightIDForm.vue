<template>
	<div class="right">
		<div class="container">
			<FindIDPWText></FindIDPWText>
			<div class="form-container">
				<NameInput class="component"></NameInput>
				<BirthInput class="component"></BirthInput>
			</div>
			<FindButton class="find"></FindButton>
		</div>
	</div>
</template>

<script>
import FindIDPWText from 'src/components/molecules/find/FindIDPWText.vue';
import NameInput from 'src/components/molecules/find/NameInput.vue';
import BirthInput from 'src/components/molecules/find/BirthInput.vue';
import FindButton from 'src/components/molecules/find/FindButton.vue';
export default {
	name: 'RightIDForm',
	setup() {
		return {};
	},
	components: { FindIDPWText, NameInput, BirthInput, FindButton },
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
