<template>
<div v-if="streamManager">
	<video ref="videoStream" autoplay/>
	<div><p>{{ clientData }}</p></div>
</div>
</template>

<script>

export default {
	name: 'UserVideo',

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
	
	mounted : function () {
		console.log(this.$refs.videoStream);
		this.streamManager.addVideoElement(this.$refs.videoStream);
	},
	updated : function () {
		console.log(this.$refs.videoStream);
		this.streamManager.addVideoElement(this.$refs.videoStream);
	}
};
</script>
