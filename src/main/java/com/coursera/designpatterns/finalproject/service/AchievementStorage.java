package com.coursera.designpatterns.finalproject.service;

import com.coursera.designpatterns.finalproject.domain.Achievement;
import com.coursera.designpatterns.finalproject.observers.AchievementObserver;

import java.util.List;

public interface AchievementStorage {

    void addAchievement(String user, Achievement achievement);

    List<Achievement> getAchievements(String user);

    void addObserver(AchievementObserver observer);

    List<AchievementObserver> getOververs();
}
