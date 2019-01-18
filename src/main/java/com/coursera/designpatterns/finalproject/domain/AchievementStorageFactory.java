package com.coursera.designpatterns.finalproject.domain;

import com.coursera.designpatterns.finalproject.service.AchievementStorage;
import com.coursera.designpatterns.finalproject.service.InMemoryAchievementStorage;
import lombok.Data;

@Data
public class AchievementStorageFactory {

    private static AchievementStorage instance;

    public static AchievementStorage getInstance() {
        if(instance == null){
            instance = InMemoryAchievementStorage.INSTANCE;
        }
        return instance;
    }

    public static void setAchievementStorage(AchievementStorage achievementStorage){
        instance = achievementStorage;
    }
}
