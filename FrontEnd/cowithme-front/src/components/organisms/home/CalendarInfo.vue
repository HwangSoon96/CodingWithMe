<template>
	<div class="q-pa-md">
		<div class="q-gutter-md row items-start">
			<q-date class="calendar" v-model="date" minimal></q-date>
		</div>
	</div>
</template>

<script>
export default {
	name: 'CalendarInfo',
	setup() {
		return {};
	},
};
</script>

<style>
.calendar {
	width: 500px;
	height: 392px;
}
</style>
