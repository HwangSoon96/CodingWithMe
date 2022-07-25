<template>
<div v-if="streamManager">
	<video ref="videoStream" autoplay/>
	<div><p>{{ clientData }}</p></div>
</div>
</template>

<script>
import {computed, onMounted, onUpdated, ref} from 'vue';
export default {
	name: 'UserVideo',

	props: {
		streamManager: Object,
	},

	setup(props){
		const videoStream = ref(null);
		onMounted(() => {
			console.log(props);
			console.log(props.streamManager);
			console.log(videoStream);
			props.streamManager.addVideoElement(videoStream);
		});

		onUpdated(() => {
			props.streamManager.addVideoElement(videoStream);
		});


		const getConnectionData = () => {
			const { connection } = props.streamManager.stream;
			return JSON.parse(connection.data);
		};

		const clientData = computed(()=>{
			const { clientData } = getConnectionData();
			return clientData;
		});

		return {
			getConnectionData,
			clientData,
			videoStream
		};

	}
};
</script>
