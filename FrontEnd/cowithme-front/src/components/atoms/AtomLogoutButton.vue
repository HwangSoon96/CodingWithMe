<template>
	<div class="q-py-xs q-gutter-sm">
		<q-btn
			size="24px"
			class="btn-size"
			align="left"
			flat
			label="로그아웃"
			icon="logout"
		></q-btn>
	</div>
</template>

<script>
export default {
	name: 'AtomButton',
	setup() {
		return {};
	},
};
</script>

<style>
.btn-size {
	width: 299px;
}
</style>
