package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userClassId;
    @JsonIgnore
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "class_id")
    private Classes classes;

    @Column
    Long studentId;

    public void setClasses(Classes classes){
        this.classes=classes;

        if(!classes.getUserClassList().contains(this)){
            classes.getUserClassList().add(this);
        }
    }
}
