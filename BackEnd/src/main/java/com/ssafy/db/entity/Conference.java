package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne (fetch = LAZY)
    @JoinColumn(name="class_id")
    Classes classes;

    @Temporal(TemporalType.DATE)
    Date date;

    @Column
    String ownerId;

    @Temporal(TemporalType.TIME)
    Date confStartTime;

    @Temporal(TemporalType.TIME)
    Date confEndTime;

    @Column
    String thumbnailPath;

    @Column
    String conferenceName;

    @Column
    boolean isActive;

    @Column
    String rtc_token;
    @JsonIgnore
    @OneToMany(mappedBy = "conference")
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    public void addAttendance(AttendanceRecord record){
        this.attendanceRecords.add(record);

        if(record.getConference()!=this) { //무한루프 방지
            record.setConference(this);
        }
    }

    public void setClasses(Classes classes){
        this.classes=classes;

        if(!classes.getConferences().contains(this)){
            classes.getConferences().add(this);
        }
    }
}
