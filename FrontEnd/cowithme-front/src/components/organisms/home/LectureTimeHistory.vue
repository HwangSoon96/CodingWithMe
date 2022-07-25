<template>
	<div class="q-pa-md">
		<q-table
			title="강의 참여 시간"
			:rows="rows"
			:columns="columns"
			row-key="name"
		></q-table>
	</div>
</template>

<script>
const columns = [
	{
		name: 'index',
		required: true,
		label: '번호',
		align: 'left',
		field: row => row.index,
		format: val => `${val}`,
	},
	{ name: 'start', align: 'center', label: '강의 시작 시간', field: 'start' },
	{ name: 'end', align: 'center', label: '강의 종료 시간', field: 'end' },
];

const rows = [
	{
		index: 1,
		start: '2022-09-01 09:00:00',
		end: '2022-09-01 18:00:00',
	},
	{
		index: 2,
		start: '2022-09-02 09:00:00',
		end: '2022-09-02 18:00:00',
	},
	{
		index: 3,
		start: '2022-09-03 09:00:00',
		end: '2022-09-03 18:00:00',
	},
	{
		index: 4,
		start: '2022-09-04 09:00:00',
		end: '2022-09-04 18:00:00',
	},
	{
		index: 5,
		start: '2022-09-05 09:00:00',
		end: '2022-09-05 18:00:00',
	},
	{
		index: 6,
		start: '2022-09-06 09:00:00',
		end: '2022-09-06 18:00:00',
	},
];

export default {
	setup() {
		return {
			columns,
			rows,
		};
	},
};
</script>

<style></style>
