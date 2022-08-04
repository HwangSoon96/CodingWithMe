package com.ssafy.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TC implements Comparable<TC>{
    int qno;
    List<InputOutput> testcase;

    @Override
    public int compareTo(TC tc) {
        return this.qno - tc.getQno();
    }
}