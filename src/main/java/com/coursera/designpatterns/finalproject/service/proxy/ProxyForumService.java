package com.coursera.designpatterns.finalproject.service.proxy;

import com.coursera.designpatterns.finalproject.domain.Badge;
import com.coursera.designpatterns.finalproject.domain.Points;
import com.coursera.designpatterns.finalproject.service.AchievementStorage;
import com.coursera.designpatterns.finalproject.service.ForumService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.*;

@Data
@AllArgsConstructor
@Service
public class ProxyForumService implements ForumService {

    private ForumService forumService;
    private AchievementStorage achievementStorage;

//        Deve adicionar 5 pontos do tipo "CREATION". Deve adicionar o bagde "I CAN TALK"
    @Override
    public void addTopic(String user, String topic) {
        forumService.addTopic(user, topic);
        achievementStorage.addAchievement(user,new Points(CREATION,5));
        achievementStorage.addAchievement(user,new Badge(I_CAN_TALK));
    }

//        Deve adicionar 3 pontos do tipo "PARTICIPATION". Deve adicionar o badge "LET ME ADD"
    @Override
    public void addComment(String user, String topic, String comment) {
        forumService.addComment(user,topic,comment);
        achievementStorage.addAchievement(user,new Points(PARTICIPATION,3));
        achievementStorage.addAchievement(user,new Badge(LET_ME_ADD));
    }

//        Deve adicionar 1 ponto do tipo "CREATION".
    @Override
    public void likeTopic(String user, String topic, String topicUser) {
        forumService.likeTopic(user,topic,topicUser);
        achievementStorage.addAchievement(user,new Points(CREATION,1));
    }

//    Deve adicionar 1 ponto do tipo "PARTICIPATION".
    @Override
    public void likeComment(String user, String topic, String comment, String commentUser) {
        forumService.likeComment(user,topic,comment,commentUser);
        achievementStorage.addAchievement(user, new Points(PARTICIPATION,1));
    }
}
