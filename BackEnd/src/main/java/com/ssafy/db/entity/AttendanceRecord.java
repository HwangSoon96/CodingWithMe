package com.ssafy.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long attendanceId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "conference_id")
    Conference conference;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Temporal(TemporalType.DATE)
    Date date;

    @Temporal(TemporalType.TIME)
    Date attStartTime;

    @Temporal(TemporalType.TIME)
    Date attEndTime;

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getAttendanceRecords().contains(this)){
            user.getAttendanceRecords().add(this);
        }
    }

    public void setConference(Conference conference){
        this.conference =conference;
        //무한 루프 주의
        if(!conference.getAttendanceRecords().contains(this)){
            conference.getAttendanceRecords().add(this);
        }
    }

}
