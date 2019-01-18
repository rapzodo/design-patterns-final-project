package com.coursera.designpatterns.finalproject.observers;

import com.coursera.designpatterns.finalproject.domain.Achievement;
import com.coursera.designpatterns.finalproject.domain.Badge;
import com.coursera.designpatterns.finalproject.domain.Points;
import com.coursera.designpatterns.finalproject.service.AchievementStorage;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.CREATION;
import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.INVENTOR;

@Data
@AllArgsConstructor
public class CreationAchievementObserver implements AchievementObserver {

    private AchievementStorage achievementStorage;

    @Override
    public void achievementUpdate(String user, Achievement achievement) {
        if (CREATION.equals(achievement.getName())) {
            if (((Points) achievement).getScore() > 100) {
                achievementStorage.addAchievement(user, new Badge(INVENTOR));
            }
        }
    }
}
