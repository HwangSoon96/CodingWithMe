package com.ssafy.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long conferenceId;


    @ManyToOne (fetch = LAZY)
    @JoinColumn(name="class_id")
    Classes classes;

    @Temporal(TemporalType.DATE)
    Date date;
    String ownerId;
    @Temporal(TemporalType.TIME)
    Date confStartTime;
    @Temporal(TemporalType.TIME)
    Date confEndTime;
    String thumbnailPath;
    String conferenceName;
    boolean isActive;

    String rtc_token;

    @OneToMany(mappedBy = "conference")
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    public void addAttendance(AttendanceRecord record){
        this.attendanceRecords.add(record);

        if(record.getConference()!=this) { //무한루프 방지
            record.setConference(this);
        }
    }
}
