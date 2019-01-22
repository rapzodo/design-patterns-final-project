package com.coursera.designpatterns.finalproject.service.proxy;

import com.coursera.designpatterns.finalproject.domain.Badge;
import com.coursera.designpatterns.finalproject.domain.Points;
import com.coursera.designpatterns.finalproject.observers.AchievementObserver;
import com.coursera.designpatterns.finalproject.service.AchievementStorage;
import com.coursera.designpatterns.finalproject.service.ForumService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.*;

@Data
@AllArgsConstructor
@Service
public class ProxyForumService implements ForumService {

    private ForumService forumService;
    private AchievementStorage achievementStorage;
    private List<AchievementObserver> observers;

    //        Deve adicionar 5 pontos do tipo "CREATION". Deve adicionar o bagde "I CAN TALK"
    @Override
    public void addTopic(String user, String topic) {
        forumService.addTopic(user, topic);
        Points creationPoints = new Points(CREATION, 5);
        achievementStorage.addAchievement(user, creationPoints);
        achievementStorage.addAchievement(user, new Badge(I_CAN_TALK));
        notify(user, creationPoints);
    }

    //        Deve adicionar 3 pontos do tipo "PARTICIPATION". Deve adicionar o badge "LET ME ADD"
    @Override
    public void addComment(String user, String topic, String comment) {
        forumService.addComment(user, topic, comment);
        Points participationPoints = new Points(PARTICIPATION, 3);
        achievementStorage.addAchievement(user, participationPoints);
        achievementStorage.addAchievement(user, new Badge(LET_ME_ADD));
        notify(user, participationPoints);
    }


    //        Deve adicionar 1 ponto do tipo "CREATION".
    @Override
    public void likeTopic(String user, String topic, String topicUser) {
        forumService.likeTopic(user, topic, topicUser);
        Points creationPoint = new Points(CREATION, 1);
        achievementStorage.addAchievement(user, creationPoint);
        notify(user, creationPoint);
    }

    //    Deve adicionar 1 ponto do tipo "PARTICIPATION".
    @Override
    public void likeComment(String user, String topic, String comment, String commentUser) {
        forumService.likeComment(user, topic, comment, commentUser);
        Points participationPoint = new Points(PARTICIPATION, 1);
        achievementStorage.addAchievement(user, participationPoint);
        notify(user, participationPoint);
    }

    private void notify(String user, Points participationPoints) {
        observers.forEach(observer -> observer.achievementUpdate(user, participationPoints));
    }
}
