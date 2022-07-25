<template>
	<div class="q-pa-md">
		<div class="parent q-pa-lg">
			<AtomPlusButton class="child"></AtomPlusButton>
		</div>
		<div class="class-info flex row">
			<div class="col q-ma-lg">
				<!-- 반, 강의제목, 날짜 -->
				<p>개나리반 detail</p>
				<div class="info">
					<p>반 설명</p>
					<p>반 구성원</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import AtomPlusButton from 'src/components/atoms/AtomPlusButton.vue';
export default {
	name: 'TeacherHomePage',
	setup() {
		return {};
	},
	components: { AtomPlusButton },
};
</script>

<style lang="scss" scoped>
.parent {
	position: relative;
}
.child {
	position: absolute;
	top: 0;
	left: 0;
}

.class-info {
	height: 300px;
	width: 700px;
	background-color: white;
	border-radius: 10px;
	border: 1px solid black;
	margin-top: 50px;
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
