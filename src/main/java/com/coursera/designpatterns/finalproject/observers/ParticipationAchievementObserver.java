package com.coursera.designpatterns.finalproject.observers;

import com.coursera.designpatterns.finalproject.domain.Achievement;
import com.coursera.designpatterns.finalproject.domain.Badge;
import com.coursera.designpatterns.finalproject.domain.Points;
import com.coursera.designpatterns.finalproject.service.AchievementStorage;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.*;

@Data
@AllArgsConstructor
public class ParticipationAchievementObserver implements AchievementObserver {

    private AchievementStorage achievementStorage;

    @Override
    public void achievementUpdate(String user, Achievement achievement) {
        if (PARTICIPATION.equals(achievement.getName())) {
            if (((Points) achievement).getScore() > 100) {
                achievementStorage.addAchievement(user, new Badge(PART_OF_THE_COMMUNITY));
            }
        }
    }
}
