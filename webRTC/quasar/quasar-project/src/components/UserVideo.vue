<template>
    <div v-if="streamManager">
	<video ref="videoStream" autoplay/>
	<div><p>{{ clientData }}</p></div>
    </div>
</template>


<script>

export default {
    name : 'UserVideo',

    props: {
		streamManager: Object,
	},

    setup(props) {
        const state = reactive({
            clientData : computed(() =>{
                const { clientData } = getConnectionData();
                return clientData;
            })
        });

        getConnectionData = () => {
            const { connection } = props.streamManager.stream;
			return JSON.parse(connection.data);
        }

        onMounted(() => {
            console.log(this.$refs.videoStream);
		    props.streamManager.addVideoElement(this.$refs.videoStream);
        });

        onUpdated(() => {
            console.log(this.$refs.videoStream);
		    props.streamManager.addVideoElement(this.$refs.videoStream);
        });
        

        return {
            state,
            getConnectionData
        }
    }
}

</script>