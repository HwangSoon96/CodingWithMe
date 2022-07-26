<template>
    <div v-if="streamManager">
        <div><p>{{ clientData }}</p></div>
        <div><video ref="videoStream" autoplay/></div>
    </div>
</template>

<script>

import { reactive, ref, computed, onMounted, onUpdated } from 'vue';

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

        const getConnectionData = () => {
            const { connection } = props.streamManager.stream;
			return JSON.parse(connection.data);
        }

        const videoStream = ref(null);
        onMounted(() => {
            console.log(videoStream);
            console.log(videoStream.value);
            
		    props.streamManager.addVideoElement(videoStream.value);
        });

        onUpdated(() => {
            console.log(videoStream.value);
		    props.streamManager.addVideoElement(videoStream.value);
        });
        

        return {
            videoStream,
            state,
            getConnectionData
        }
    }
}
</script>
