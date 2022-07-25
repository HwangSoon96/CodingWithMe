<template>
	<div>
		<ClassInfo></ClassInfo>
		<div class="row">
			<CalendarInfo></CalendarInfo>
			<div class="col-8">
				<LectureTimeHistory></LectureTimeHistory>
			</div>
		</div>
	</div>
</template>

<script>
import { defineComponent } from 'vue';
import CalendarInfo from 'src/components/organisms/home/CalendarInfo.vue';
import ClassInfo from 'src/components/organisms/home/ClassInfo.vue';
import LectureTimeHistory from 'src/components/organisms/home/LectureTimeHistory.vue';
// import AtomLogo from 'src/components/atoms/AtomLogo.vue';

export default defineComponent({
	name: 'IndexPage',
	components: { CalendarInfo, ClassInfo, LectureTimeHistory },
});
</script>

<style>
/* LoginPage {
	height: 100vh;
	width: 100vw;
	background-color: red;
	background-size: cover;
	background-position: center;
	margin: 0;
} */
</style>
