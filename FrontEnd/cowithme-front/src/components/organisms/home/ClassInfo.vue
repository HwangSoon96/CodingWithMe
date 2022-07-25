<template>
	<div class="q-pa-md">
		<div class="q-gutter-md row items-start">
			<div class="class-info flex row">
				<div class="col q-ma-lg">
					<!-- 반, 강의제목, 날짜 -->
					<p>개나리반의 강의</p>
					<div class="info">
						<p>제목: 프로젝트 1</p>
						<p>날짜: 2022.07.25</p>
					</div>
				</div>
				<div class="col">
					<AtomBasic1Button class="button" label="강의 입장"></AtomBasic1Button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import AtomBasic1Button from 'src/components/atoms/AtomBasic1Button.vue';
export default {
	name: 'ClassInfo',
	setup() {
		return {};
	},
	components: { AtomBasic1Button },
};
</script>

<style lang="scss" scoped>
.class-info {
	height: 300px;
	width: 700px;
	background-color: white;
	border-radius: 10px;
	box-shadow: 3px 3px 3px 0px rgba(0, 0, 0, 0.2);
}

.button {
	margin-top: 230px;
	margin-left: 200px;
}

.info {
	margin-top: 150px;
}
</style>
