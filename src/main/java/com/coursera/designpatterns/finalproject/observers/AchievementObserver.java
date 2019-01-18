package com.coursera.designpatterns.finalproject.observers;

import com.coursera.designpatterns.finalproject.domain.Achievement;

public interface AchievementObserver {

    void achievementUpdate(String user, Achievement achievement);
}
