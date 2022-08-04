package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceModifyPostReq;
import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.Conference;

import java.sql.Connection;
import java.util.List;

public interface ConferenceService {
    Conference createConference(ConferenceRegisterPostReq conferenceRegisterPostReq);

    Conference modifyConference(ConferenceModifyPostReq conferenceModifyPostReq);

    Conference finishConference(ConferenceModifyPostReq conferenceModifyPostReq);

    List<Conference> getAllConferenceByClassId(Long classId);

    Conference getActiveConference(Long classId);

    List<Conference> getNotActiveConference(Long classId);

    List<Conference> getConferencesByConferenceName(String conferenceName);

    Conference getConferencesById(String id);
}
