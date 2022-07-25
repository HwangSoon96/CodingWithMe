<template>
	<div id="main-container" class="container">
		<div id="join" v-if="state.session === undefined">
			<div id="img-div"><img src="resources/images/openvidu_grey_bg_transp_cropped.png" /></div>
			<div id="join-dialog" class="jumbotron vertical-center">
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
						<button class="btn btn-lg btn-success" @click="joinSession()">Join!</button>
					</p>
				</div>
			</div>
		</div>
		
		<div id="session" v-if="state.session !== undefined">
			<div id="session-header">
				<h1 id="session-title">{{ state.mySessionId }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
			</div>

			<div id="session-header-screenShare">
				<input v-if = "!state.screenOV" class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="startScreenShare" value="Start Screen Share">
				<input v-if =  "state.screenOV" class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="stopScreenShare" value="End Screen Share">
			</div>
			<div id="main-video" class="col-md-6">
				<user-video v-if="state.mainStreamManager" :stream-manager="state.mainStreamManager"/>
			</div>
			<div id="video-container" class="col-md-6">
				<div v-for="sub in state.subscribers" :key="sub.stream.connection.connectionId">{{ sub }}</div>
				<user-video v-for="sub in state.subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)"/>
				<user-video v-if="state.publisher" :stream-manager="state.publisher" @click="updateMainVideoStreamManager(state.publisher)"/>
			</div>

			<div id="screenShare-container" class="col-md-6">

			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from './components/UserVideo';
import {reactive, ref, computed, onUpdated} from 'vue';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + "localhost:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'App',

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

		const leaveSession = () => {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (state.session) state.session.disconnect();
			if (state.screenSession) state.screenSession.disconnect();
			state.session = undefined;
			state.screenSession = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
			state.OV = undefined;
			state.screenOV = undefined;

			window.removeEventListener('beforeunload', leaveSession);
		};

		const joinSession = () => {
			// --- Get an OpenVidu object ---
			state.OV = new OpenVidu();

			// --- Init a session ---
			state.session = state.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			state.session.on('streamCreated', ({ stream }) => {
				// stream.typeOfVideo == "SCREEN"
				const subscriber = state.session.subscribe(stream);
				state.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			state.session.on('streamDestroyed', ({ stream }) => {
				const index = state.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					state.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			state.session.on('exception', ({ exception }) => {
				console.error(exception);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
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
		};

		onUpdated(() => {
			console.log("updated");
		});
		const startScreenShare = () => {
			state.screenOV = new OpenVidu();
			state.screenSession = state.screenOV.initSession();

			state.getToken(state.mySessionId).then(token =>{
				state.screenSession.connect(token, { clientData: state.screenShareName }).then(()=>{
						let publisher = state.screenOV.initPublisher("html-element-id", { videoSource: "screen", publishAudio: false  });

						try {
							publisher.once('accessAllowed', () => {
							let test = publisher.stream.getMediaStream().getVideoTracks();
							console.log(test);
							publisher.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
								console.log('User pressed the "Stop sharing" button');
								state.stopScreenShare();
							});
								state.screenSession.publish(publisher);
							});

							publisher.once('accessDenied', (event) => {
								console.error(event, 'ScreenShare: Access Denied');
								this.states.stopScreenShare();
							});
						} catch (error) {
							console.log(error);
						}
						
				})
			}).catch(error => {
				console.error(error);
				state.screenOV = undefined;
				state.screenSession = undefined;
			})
		};

		const stopScreenShare = () => {
			state.screenSession.disconnect();
			state.screenOV = undefined;
			state.screenSession = undefined;
		};

		const updateMainVideoStreamManager = (stream) => {
			if (state.mainStreamManager === stream) 
				return;
			state.mainStreamManager = stream;
		}

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */
		const getToken = (mySessionId) => {
			return createSession(mySessionId).then(sessionId => createToken(sessionId));
		};

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

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
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

		return {
			state,
			joinSession,
			leaveSession,
			startScreenShare,
			stopScreenShare,
			updateMainVideoStreamManager,
			getToken,
			createSession,
			createToken
		}
	},
}
</script>
