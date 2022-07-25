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
		</div>
	</div>
</template>

<script>

import { OpenVidu } from 'openvidu-browser';
import {reactive, ref, computed} from 'vue';


export default {
  name: 'IndexPage',

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
			// --- Get an OpenVidu object ---
			state.OV = new OpenVidu();

			// --- Init a session ---
			state.session = state.OV.initSession();

    }
    const leaveSession = () => {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (state.session) state.session.disconnect();
			// if (state.screenSession) state.screenSession.disconnect();
			state.session = undefined;
			state.screenSession = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
			state.OV = undefined;
			state.screenOV = undefined;

			window.removeEventListener('beforeunload', leaveSession);
		};

    return {
      joinSession,
      leaveSession,
      state
    }
  }
}
</script>
