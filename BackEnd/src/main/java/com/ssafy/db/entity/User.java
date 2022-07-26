package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String id;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String phone;

    @Column(nullable = false)
    String nickname;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date birthDt;

    String profilePath;
    @Column(nullable = false)
    String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Classes> classlist = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<TestRecord> testRecords = new ArrayList<>();

    public void addClasses(Classes classes){
        this.classlist.add(classes);

        if(classes.getUser() !=this) { //무한루프 방지
            classes.setUser(this);

        }

    }

    public void addAttendance(AttendanceRecord record){
        this.attendanceRecords.add(record);

        if(record.getUser()!=this) { //무한루프 방지
            record.setUser(this);
        }
    }

    public void addTestRecord(TestRecord record){
        this.testRecords.add(record);

        if(record.getUser()!=this) { //무한루프 방지
            record.setUser(this);
        }
    }
}
