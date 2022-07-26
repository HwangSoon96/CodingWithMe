package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long testId;
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="class_id")
    Classes classes;

    @Column
    String testName;

    @Column
    int testQno;

    @Column
    String testPath;

    @Column
    String testcase;
    @JsonIgnore
    @OneToMany(mappedBy = "test")
    private List<TestRecord> testRecords = new ArrayList<>();

    public void addTestRecord(TestRecord record){
        this.testRecords.add(record);

        if(record.getTest()!=this) { //무한루프 방지
            record.setTest(this);
        }
    }

    public void setClasses(Classes classes){
        this.classes=classes;

        if(!classes.getTestList().contains(this)){
            classes.getTestList().add(this);
        }
    }
}
