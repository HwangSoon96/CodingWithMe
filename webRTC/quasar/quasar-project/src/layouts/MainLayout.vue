<template>
  <div id="main-container" class="container">
		<div id="join" v-if="state.session === undefined">
			<h1>Join a video session</h1>
				<div class="form-group">
					<p>
						<label>Participant</label>
						<input v-model="state.myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<label>Session</label>
						<input v-model="state.mySessionId" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession">Join!</button>
					</p>
				</div>
		</div>
		
	<div id="session" v-if="state.session !== undefined">
			<div id="session-header">
				<h1 id="session-title">{{ state.mySessionId }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
			</div>
			<div id="session-header-screenShare">
				<input v-if ="!state.screenOV" class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="startScreenShare" value="Start Screen Share">
				<input v-if ="state.screenOV" class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="stopScreenShare" value="End Screen Share">
			</div>

			<div id="main-video" class="col-md-6">
				<user-video v-if="state.mainStreamManager" :stream-manager="state.mainStreamManager"/>
			</div>
		
		<div id="video-container" class="col-md-3">
			<user-video v-if="state.publisher" :stream-manager="state.publisher" @click="updateMainVideoStreamManager(state.publisher)"/>
			<user-video v-for="sub in state.subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
		</div>
	</div>
    
  </div>
</template>

<script>

import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import { reactive, ref, computed, onUpdated } from 'vue';
import UserVideo from '../components/UserVideo.vue';

axios.defaults.headers.post['Content-Type'] = 'application/json';
const OPENVIDU_SERVER_URL = "https://" + "localhost:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: 'MainLayout',

  components: {
      UserVideo,
  },

  setup() {
    const state = reactive({
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

			mySessionId: ref('SessionA'),
			myUserName: ref('Participant' + Math.floor(Math.random() * 100)),

			// 화면 공유할 때 사용할 session
			screenOV: undefined,
			screenSession: undefined,
			screenShareName : computed(()=>{
				return state.myUserName + "'s Screen";
			})
    });

    const joinSession = () => {
		state.OV = new OpenVidu();
		state.session = state.OV.initSession();

		console.log(state.session);
		console.log(state.session.value);

		state.session.on('streamCreated', ({ stream }) => {
				const subscriber = state.session.subscribe(stream);
				state.subscribers.push(subscriber);
			});

		state.session.on('streamDestroyed', ({ stream }) => {
				const index = state.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					state.subscribers.splice(index, 1);
				}
			});

		state.session.on('exception', ({ exception }) => {
				console.error(exception);
			});

		getToken(state.mySessionId).then(token => {
				console.log(token);
				state.session.connect(token, { clientData: state.myUserName })
					.then(() => {
						
						state.OV.getDevices().then(() =>{
								// let videoDevices = devices.filter(device => device.kind === 'videoinput');

								let publisher = state.OV.initPublisher(undefined, {
								audioSource: undefined, // The source of audio. If undefined default microphone
								videoSource: undefined, // The source of video. If undefined default webcam
								publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
								publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
								resolution: '640x480',  // The resolution of your video
								frameRate: 30,			// The frame rate of your video
								insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
								mirror: false       	// Whether to mirror your local video or not
							});

							state.mainStreamManager = publisher;
							state.publisher = publisher;

							// --- Publish your stream ---

							state.session.publish(state.publisher);
						})
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', leaveSession);
    }
    const leaveSession = () => {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (state.session) state.session.disconnect();
			if (state.screenSession) state.screenSession.disconnect();
			state.session = ref(undefined);
			state.screenSession = ref(undefined);
			state.mainStreamManager = ref(undefined);
			state.publisher = ref(undefined);
			state.subscribers = [];
			state.OV = ref(undefined);
			state.screenOV = ref(undefined);

			window.removeEventListener('beforeunload', leaveSession);
		};

	onUpdated(() => {
		console.log(state.subscribers);
	});
	

    // serverSide
    const getToken = async (mySessionId) => {
      const id = await createSession(mySessionId);
      return await createToken(id);
    }

    const createSession = (sessionId) => {
      return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
    };

    const createToken = (sessionId) => {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		}; 
	
	const startScreenShare = () => {
		state.screenOV = new OpenVidu();
		state.screenSession = state.screenOV.initSession();

		getToken(state.mySessionId).then(token =>{
			state.screenSession.connect(token, { clientData: state.screenShareName }).then(()=>{
					let publisher = state.screenOV.initPublisher("html-element-id", { videoSource: "screen", publishAudio: false  });

					try {
						publisher.once('accessAllowed', () => {
						let test = publisher.stream.getMediaStream().getVideoTracks();
						console.log(test);
						publisher.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
							console.log('User pressed the "Stop sharing" button');
							stopScreenShare();
						});
							state.screenSession.publish(publisher);
						});

						publisher.once('accessDenied', (event) => {
							console.error(event, 'ScreenShare: Access Denied');
							stopScreenShare();
						});
					} catch (error) {
						console.log(error);
					}
					
			})
		}).catch(error => {
			console.error(error);
			state.screenOV = ref(undefined);
			state.screenSession = ref(undefined);
		})
	};

	const stopScreenShare = () => {
		state.screenSession.disconnect();
		state.screenOV = ref(undefined);
		state.screenSession = ref(undefined);
	};

	const updateMainVideoStreamManager = (stream) => {
		if (state.mainStreamManager === stream)
			return;
		state.mainStreamManager = stream;
	};

	return {
		joinSession,
		leaveSession,
		state,
		startScreenShare,
		stopScreenShare,
		updateMainVideoStreamManager,

		getToken,
		createSession,
		createToken
    }
  }

  
}
</script>
