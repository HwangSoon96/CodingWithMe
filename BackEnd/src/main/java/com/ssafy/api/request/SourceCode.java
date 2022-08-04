package com.ssafy.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SourceCode {
    List<Answer> answers;
}
