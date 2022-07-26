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
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classId;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "tutor_id")
    private User user;

    @Column
    private String className;

    @Column
    private String classDescription;
    @JsonIgnore
    @OneToMany(mappedBy = "classes")
    private List<Test> testList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "classes")
    private List<Conference> conferences = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "classes")
    private List<UserClass> userClassList = new ArrayList<>();

    public void setUser(User user){
        this.user =user;
        //무한 루프 주의
        if(!user.getClasslist().contains(this)){
            user.getClasslist().add(this);
        }
    }
    public void addTest(Test test){
        this.testList.add(test);

        if(test.getClasses() !=this) { //무한루프 방지
            test.setClasses(this);
        }

    }

    public void addConference(Conference conference){
        this.conferences.add(conference);

        if(conference.getClasses() !=this) { //무한루프 방지
            conference.setClasses(this);
        }

    }

    public void addUserClass(UserClass userClass){
        this.userClassList.add(userClass);

        if(userClass.getClasses() !=this) { //무한루프 방지
            userClass.setClasses(this);
        }

    }
    
}
