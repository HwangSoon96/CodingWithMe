package com.ssafy.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer implements Comparable<Answer>{
    int qno;
    String code;

    @Override
    public int compareTo(Answer o) {
        return this.qno - o.getQno();
    }
}
