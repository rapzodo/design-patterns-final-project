package com.coursera.designpatterns.finalproject.service;

import com.coursera.designpatterns.finalproject.domain.Achievement;
import com.coursera.designpatterns.finalproject.observers.AchievementObserver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum InMemoryAchievementStorage implements AchievementStorage {

    INSTANCE;
    private Map<String, List<Achievement>> userAchievementsMap = new HashMap<>();
    private List<AchievementObserver> observers = new ArrayList<>();

    @Override
    public void addAchievement(String user, Achievement achievement) {
        if(userAchievementsMap.containsKey(user)) {
            Achievement merge = mergeAchievements(user, achievement);
            observers.forEach(observer -> observer.achievementUpdate(user,merge));
        }else{
            userAchievementsMap.put(user,Stream.of(achievement).collect(Collectors.toList()));
            observers.forEach(observer -> observer.achievementUpdate(user,achievement));
        }
    }

    private Achievement mergeAchievements(String user, Achievement achievement) {
        List<Achievement> achievements = getAchievements(user);
        Optional<Achievement> first = findPreviousAchievement(achievement.getName(), achievements);
        if(first.isPresent()){
            return updateAchievement(achievement, achievements, first);
        }else{
            achievements.add(achievement);
            return achievement;
        }
    }

    private Achievement updateAchievement(Achievement achievement, List<Achievement> achievements, Optional<Achievement> first) {
        Achievement previousPoints = first.get();
        Achievement updatedAchievement = achievement.merge(previousPoints);
        achievements.remove(previousPoints);
        achievements.add(updatedAchievement);
        return updatedAchievement;
    }

    private Optional<Achievement> findPreviousAchievement(String achievementName, List<Achievement> achievements) {
        return achievements.stream()
                    .filter(a -> a.getName().equals(achievementName))
                    .findFirst();
    }

    @Override
    public void addObserver(AchievementObserver observer){
        observers.add(observer);
    }

    @Override
    public List<AchievementObserver> getObservers() {
        return observers;
    }

    @Override
    public List<Achievement> getAchievements(String user) {
        return userAchievementsMap.get(user);
    }
}
