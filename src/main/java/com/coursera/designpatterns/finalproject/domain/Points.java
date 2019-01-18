package com.coursera.designpatterns.finalproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Points extends Achievement {

    private int score;

    public Points(String name, int score) {
        super(name);
        this.score = score;
    }

    @Override
    public Achievement merge(Achievement previousPoints) {
        score += ((Points) previousPoints).score;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        return ((Achievement)obj).getName().equals(getName());
    }
}
