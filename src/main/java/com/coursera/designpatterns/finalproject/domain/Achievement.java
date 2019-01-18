package com.coursera.designpatterns.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Achievement {

    private String name;

//    public abstract Achievement merge(List<Achievement> achievements);

    public abstract Achievement merge(Achievement previousPoints);

}
