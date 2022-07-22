package io.openvidu.mvc.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Recording;
import io.openvidu.java.client.RecordingLayout;
import io.openvidu.java.client.RecordingMode;
import io.openvidu.java.client.RecordingProperties;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;

@Controller
public class SessionController {

	// 여기 있는 내용들은 spring component가 아님. autowired 불가
	// OpenVidu object as entrypoint of the SDK
	private OpenVidu openVidu;

	// Session 이름, Session Object
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();

	// Map<Session 이름, Map<토큰, 유저 권한(Role)>
	private Map<String, Map<String, OpenViduRole>> mapSessionTokens = new ConcurrentHashMap<>();

	// URL where our OpenVidu server is listening
	private String OPENVIDU_URL;
	// Secret shared with our OpenVidu server
	private String SECRET;

	// value는 application.properties에 정의됨.
	public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
	}

	// clientData : 사용자 이름, sessionName : 입장하려는 방 session
	@RequestMapping(value = "/session", method = RequestMethod.POST)
	public String joinSession(
		@RequestParam(name = "data") String clientData,
			@RequestParam(name = "session-name") String sessionName,
				Model model, HttpSession httpSession) {

		try {
			checkUserLogged(httpSession);
		} catch (Exception e) {
			return "index";
		}
		System.out.println("Getting sessionId and token | {sessionName}={" + sessionName + "}");

		// 이 유저의 역할을 받아옴.
		// Role은 3가지
		// OpenViduRole.MODERATOR : PUBLISHER 권한 + 다른 사람 쫓아낼 수 있음
		// OpenViduRole.PUBLISHER : SUBSCRIBER 권한 + 자기 영상 송출 가능
		// OpenViduRole.SUBSCRIBER : 다른 사람 영상을 볼 수 있음.
		OpenViduRole role = LoginController.users.get(httpSession.getAttribute("loggedUser")).role;

		// Optional data to be passed to other users when this user connects to the
		// video-call. In this case, a JSON with the value we stored in the HttpSession
		// object on login
		String serverData = "{\"serverData\": \"" + httpSession.getAttribute("loggedUser") + "\"}";

		// Build connectionProperties object with the serverData and the role
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
				.role(role).data(serverData).build();

		// 해당 방이 존재하면 정보 갱신부터.
		if (this.mapSessions.get(sessionName) != null){
			try {
				// 접속하려는 방
				Session targetSession = this.mapSessions.get(sessionName);
				targetSession.fetch();
				
				// 방은 존재하는데 아무도 없는 경우
				if(targetSession.getActiveConnections().size() == 0)
					this.mapSessions.remove(sessionName);
			} catch(OpenViduHttpException e){
				// 미디어 서버 오류 or 해당 방이 존재하지 않음.
				this.mapSessions.remove(sessionName);
			}
			catch (Exception e) {
				this.mapSessions.remove(sessionName);
			}
		}
		
		
		if (this.mapSessions.get(sessionName) != null) {
			// Session already exists
			System.out.println("Existing session " + sessionName);
			try {
				// Generate a new token with the recently create5d connectionProperties
				String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

				// Update our collection storing the new token
				this.mapSessionTokens.get(sessionName).put(token, role);

				// Add all the needed attributes to the template
				model.addAttribute("sessionName", sessionName);
				model.addAttribute("token", token);
				model.addAttribute("nickName", clientData);
				model.addAttribute("userName", httpSession.getAttribute("loggedUser"));

				// Return session.html template
				return "session";

			} catch (Exception e) {
				// If error just return dashboard.html template
				model.addAttribute("username", httpSession.getAttribute("loggedUser"));
				return "dashboard";
			}
		} else {
			// New session
			System.out.println("New session " + sessionName);
			try {

				RecordingProperties recordingProperties = new RecordingProperties.Builder()
				.outputMode(Recording.OutputMode.COMPOSED)
				.recordingLayout(RecordingLayout.BEST_FIT)
				.resolution("640x480")
				.frameRate(24)
				.build();

				SessionProperties sessionProperties = new SessionProperties.Builder()
				.recordingMode(RecordingMode.ALWAYS) // RecordingMode.ALWAYS for automatic recording
				.defaultRecordingProperties(recordingProperties)
				.build();

				
				// Create a new OpenVidu Session
				Session session = this.openVidu.createSession(sessionProperties);
				// Generate a new token with the recently created connectionProperties
				String token = session.createConnection(connectionProperties).getToken();
				// Store the session and the token in our collections
				this.mapSessions.put(sessionName, session);
				this.mapSessionTokens.put(sessionName, new ConcurrentHashMap<>());
				this.mapSessionTokens.get(sessionName).put(token, role);

				// Add all the needed attributes to the template
				model.addAttribute("sessionName", sessionName);
				model.addAttribute("token", token);
				model.addAttribute("nickName", clientData);
				model.addAttribute("userName", httpSession.getAttribute("loggedUser"));

				// Return session.html template
				return "session";

			} catch (Exception e) {
				// If error just return dashboard.html template
				model.addAttribute("username", httpSession.getAttribute("loggedUser"));
				return "dashboard";
			}
		}
	}
	@RequestMapping(value = "/leave-session", method = RequestMethod.POST)
	public String removeUser(
			@RequestParam(name = "session-name") String sessionName,
			@RequestParam(name = "token") String token, 
			Model model, HttpSession httpSession) throws Exception {

		try {
			checkUserLogged(httpSession);
		} catch (Exception e) {
			return "index";
		}
		System.out.println("Removing user | sessioName=" + sessionName + ", token=" + token);

		

		// If the session exists ("TUTORIAL" in this case)
		if (this.mapSessions.get(sessionName) != null && this.mapSessionTokens.get(sessionName) != null) {
			// If the token exists
			if (this.mapSessionTokens.get(sessionName).remove(token) != null) {
				// User left the session
				if (this.mapSessionTokens.get(sessionName).isEmpty()) {
					// Last user left: session must be removed
					this.mapSessions.remove(sessionName);
				}
				return "redirect:/dashboard";

			} else {
				// The TOKEN wasn't valid
				System.out.println("Problems in the app server: the TOKEN wasn't valid");
				return "redirect:/dashboard";
			}

		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return "redirect:/dashboard";
		}
	}

	private void checkUserLogged(HttpSession httpSession) throws Exception {
		if (httpSession == null || httpSession.getAttribute("loggedUser") == null) {
			throw new Exception("User not logged");
		}
	}
}