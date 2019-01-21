package com.coursera.designpatterns.finalproject.observers;

import com.coursera.designpatterns.finalproject.domain.Achievement;
import com.coursera.designpatterns.finalproject.domain.AchievementStorageFactory;
import com.coursera.designpatterns.finalproject.domain.Badge;
import com.coursera.designpatterns.finalproject.domain.Points;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.CREATION;
import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.INVENTOR;

@Data
@AllArgsConstructor
public class CreationAchievementObserver implements AchievementObserver {

    @Override
    public void achievementUpdate(String user, Achievement achievement) {
        if (CREATION.equals(achievement.getName())) {
            if (((Points) achievement).getScore() > 100) {
                AchievementStorageFactory.getInstance().addAchievement(user, new Badge(INVENTOR));
            }
        }
    }
}
