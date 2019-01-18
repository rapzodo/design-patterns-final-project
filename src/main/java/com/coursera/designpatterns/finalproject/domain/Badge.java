package com.coursera.designpatterns.finalproject.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Badge extends Achievement {

    public Badge(String name) {
        super(name);
    }

    @Override
    public Achievement merge(Achievement previousPoints) {
        return previousPoints;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        return ((Achievement)obj).getName().equals(getName());
    }

}
