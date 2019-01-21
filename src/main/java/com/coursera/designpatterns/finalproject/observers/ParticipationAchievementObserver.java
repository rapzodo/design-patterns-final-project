package com.coursera.designpatterns.finalproject.observers;

import com.coursera.designpatterns.finalproject.domain.Achievement;
import com.coursera.designpatterns.finalproject.domain.AchievementStorageFactory;
import com.coursera.designpatterns.finalproject.domain.Badge;
import com.coursera.designpatterns.finalproject.domain.Points;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.PARTICIPATION;
import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.PART_OF_THE_COMMUNITY;

@Data
@AllArgsConstructor
public class ParticipationAchievementObserver implements AchievementObserver {

    @Override
    public void achievementUpdate(String user, Achievement achievement) {
        if (PARTICIPATION.equals(achievement.getName())) {
            if (((Points) achievement).getScore() > 100) {
                AchievementStorageFactory.getInstance().addAchievement(user, new Badge(PART_OF_THE_COMMUNITY));
            }
        }
    }
}
