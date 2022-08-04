package com.ssafy.api.service;

import com.ssafy.api.request.ClassesModifyPostReq;
import com.ssafy.api.request.ConferenceModifyPostReq;
import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.Classes;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ClassesRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

@Service("conferenceService")
@Transactional(readOnly = true)
public class ConferenceServiceImpl implements ConferenceService{

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceRepositorySupport conferenceRepositorySupport;
    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Conference createConference(ConferenceRegisterPostReq conferenceRegisterPostReq) {
        Conference conference =new Conference();
        Classes classes = classesRepository.findByClassId(conferenceRegisterPostReq.getClassId()).get();
        conference.setClasses(classes);
        conference.setConferenceName(conferenceRegisterPostReq.getConferenceName());

        User user = userRepository.findById(conferenceRegisterPostReq.getOwnerId()).get();
        conference.setOwnerId(user.getId());

        Date date = new Date();
        conference.setDate(date);
        conference.setConfStartTime(date);
        conference.setActive(true);

//        conference.setRtc_token();
        return conferenceRepository.save(conference);
    }

    @Override
    @Transactional
    public Conference modifyConference(ConferenceModifyPostReq conferenceModifyPostReq) {
        Conference conference = conferenceRepositorySupport.findByClassesClassIdActive(conferenceModifyPostReq.getClassId()).get();
        conference.setConferenceName(conferenceModifyPostReq.getConferenceName());
        User user = userRepository.findById(conferenceModifyPostReq.getOwnerId()).get();
        conference.setOwnerId(user.getId());
        return conferenceRepository.save(conference);
    }

    @Override
    @Transactional
    public Conference finishConference(ConferenceModifyPostReq conferenceModifyPostReq) {
        Conference conference = conferenceRepositorySupport.findByClassesClassIdActive(conferenceModifyPostReq.getClassId()).get();
        conference.setActive(false);
        Date date = new Date();
        conference.setConfEndTime(date);
        return conferenceRepository.save(conference);
    }

    @Override
    public List<Conference> getAllConferenceByClassId(Long classId) {
        List<Conference> conferences = conferenceRepository.findByClassesClassId(classId).get();

        return conferences;
    }

    @Override
    public Conference getActiveConference(Long classId) {
        Conference conference = conferenceRepositorySupport.findByClassesClassIdActive(classId).get();
        return conference;
    }

    @Override
    public List<Conference> getNotActiveConference(Long classId) {
        List<Conference> conferences = conferenceRepositorySupport.findByClassesClassIdNotActive(classId).get();
        return conferences;
    }

    @Override
    public List<Conference> getConferencesByConferenceName(String conferenceName) {
        List<Conference> conferences = conferenceRepositorySupport.findByConferenceName(conferenceName).get();
        return conferences;
    }

    @Override
    public Conference getConferencesById(String id) {
        Conference conference = conferenceRepositorySupport.findById(id).get();
        return conference;
    }

}
