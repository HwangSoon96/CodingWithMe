<template>
	<div class="navbar">
		<div class="row">
			<div class="col"></div>
			<div class="col-10">
				<AtomLogo2></AtomLogo2>
			</div>
			<div class="col"></div>
		</div>
		<div class="row">
			<div class="col-1"></div>
			<div class="col">
				<ClassText></ClassText>
			</div>
			<div class="col-6"></div>
		</div>
		<div class="row">
			<div class="col-1"></div>
			<div class="col">
				<WelcomeText></WelcomeText>
			</div>
			<div class="col-6"></div>
		</div>
		<div class="row">
			<AtomSearchIconButton></AtomSearchIconButton>
		</div>
		<div class="row">
			<AtomScoreIconButton></AtomScoreIconButton>
		</div>
		<div class="row">
			<AtomMyPageButton></AtomMyPageButton>
		</div>
		<div class="row">
			<AtomLogoutButton></AtomLogoutButton>
		</div>
	</div>
</template>

<script>
import AtomLogo2 from 'src/components/atoms/AtomLogo2.vue';
import AtomSearchIconButton from 'src/components/atoms/AtomSearchIconButton.vue';
import AtomScoreIconButton from 'src/components/atoms/AtomScoreIconButton.vue';
import AtomMyPageButton from 'src/components/atoms/AtomMyPageButton.vue';
import AtomLogoutButton from 'src/components/atoms/AtomLogoutButton.vue';
import ClassText from 'src/components/molecules/home/ClassText.vue';
import WelcomeText from 'src/components/molecules/home/WelcomeText.vue';
export default {
	name: 'NavBar',
	setup() {
		return {};
	},
	components: {
		AtomLogo2,
		AtomSearchIconButton,
		AtomScoreIconButton,
		AtomMyPageButton,
		ClassText,
		WelcomeText,
		AtomLogoutButton,
	},
};
</script>

<style>
.navbar {
	background-color: #d9d9d9;
	height: 100vh;
}
</style>
