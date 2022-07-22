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
public class UserClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userClassId;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "class_id")
    private Classes classes;

    @Column
    Long studentId;

}
