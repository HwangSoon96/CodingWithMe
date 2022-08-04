package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.api.request.SourceCode;
import com.ssafy.api.service.SourceCodeJsonConverter;
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
public class TestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long testRecordId;
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    User user;
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "test_id")
    Test test;

    @Column
    int correctCount;

    @Column(length = 1000)
    @Convert( converter = SourceCodeJsonConverter.class)
    SourceCode sourceCode;

    @Column
    String lang;

    @Temporal(TemporalType.DATE)
    Date date;

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getTestRecords().contains(this)){
            user.getTestRecords().add(this);
        }
    }

    public void setTest(Test test){
        this.test =test;
        //무한 루프 주의
        if(!test.getTestRecords().contains(this)){
            test.getTestRecords().add(this);
        }
    }
}
