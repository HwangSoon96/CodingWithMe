package com.ssafy.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long testRecordId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "test_id")
    Test test;

    int correctCount;
    String sourceCode;

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
