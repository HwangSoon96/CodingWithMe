package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long fileId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="test_id")
    Test test;

    String filename;

    String fileOriName;

    String fileUrl;
}