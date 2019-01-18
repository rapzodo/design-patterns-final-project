package com.coursera.designpatterns.finalproject.service.proxy;

import com.coursera.designpatterns.finalproject.domain.Achievement;
import com.coursera.designpatterns.finalproject.domain.AchievementStorageFactory;
import com.coursera.designpatterns.finalproject.domain.Badge;
import com.coursera.designpatterns.finalproject.domain.Points;
import com.coursera.designpatterns.finalproject.observers.AchievementObserver;
import com.coursera.designpatterns.finalproject.observers.CreationAchievementObserver;
import com.coursera.designpatterns.finalproject.observers.ParticipationAchievementObserver;
import com.coursera.designpatterns.finalproject.service.AchievementStorage;
import com.coursera.designpatterns.finalproject.service.ForumService;
import com.coursera.designpatterns.finalproject.service.InMemoryAchievementStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.coursera.designpatterns.finalproject.domain.AchievementsNames.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

public class ProxyForumServiceTest {

    private ForumService proxyForumService;
    private String user = "JohnDoe";
    private ForumService mockRemoteService;

    @Before
    public void setup() {
        mockRemoteService = Mockito.mock(ForumService.class);
        AchievementStorageFactory.setAchievementStorage(InMemoryAchievementStorage.INSTANCE);
        AchievementStorage achievementStorage = AchievementStorageFactory.getInstance();
        proxyForumService = new ProxyForumService(mockRemoteService, achievementStorage);
        achievementStorage.addObserver(new CreationAchievementObserver(achievementStorage));
        achievementStorage.addObserver(new ParticipationAchievementObserver(achievementStorage));
    }

    @Test
    public void shouldAdd_5_CreationPointsAndCreationBadgeToUserWhenAddTopic() {
        List<Achievement> expectedAchievements = Stream.of(new Points(CREATION, 5), new Badge(I_CAN_TALK))
                .collect(Collectors.toList());
        proxyForumService.addTopic(user, "topic1");
        List<Achievement> achievementsInMemory = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievementsInMemory)
                .containsExactlyInAnyOrderElementsOf(expectedAchievements);
    }

    @Test
    public void shouldAdd_3_ParticipationPointsAndLetMeAddBadgeToUserWhenAddComment() {
        List<Achievement> expectedAchievements = Stream.of(new Points(PARTICIPATION, 3), new Badge(LET_ME_ADD))
                .collect(Collectors.toList());
        proxyForumService.addComment(user, "topic1", "this is a comment");
        List<Achievement> achievementsInMemory = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievementsInMemory)
                .containsExactlyInAnyOrderElementsOf(expectedAchievements);
    }

    @Test
    public void shouldAdd_1_CreationPointToUserWhenLikeATopic() {
        List<Achievement> expectedAchievements = Stream.of(new Points(CREATION, 1))
                .collect(Collectors.toList());
        proxyForumService.likeTopic(user, "topic1", "user liked this topic");
        List<Achievement> achievementsInMemory = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievementsInMemory)
                .containsExactlyInAnyOrderElementsOf(expectedAchievements);
    }

    @Test
    public void shouldAdd_1_ParticipationPointToUserWhenLikeAComment() {
        List<Achievement> expectedAchievements = Stream.of(new Points(PARTICIPATION, 1))
                .collect(Collectors.toList());
        proxyForumService.likeComment(user, "topic1", "this is the user comment", "user commented this topic");
        List<Achievement> achievementsInMemory = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievementsInMemory)
                .containsExactlyInAnyOrderElementsOf(expectedAchievements);
    }

    @Test
    public void shouldAdd_10_CreationPointsAndOneICanTalkBadgeToUserWhenAddTopic() {
        List<Achievement> expectedAchievements = Stream.of(new Points(CREATION, 10), new Badge(I_CAN_TALK))
                .collect(Collectors.toList());
        proxyForumService.addTopic(user, "topic1");
        proxyForumService.addTopic(user, "topic2");
        List<Achievement> achievementsInMemory = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievementsInMemory).containsExactlyInAnyOrderElementsOf(expectedAchievements);
    }

    @Test
    public void shouldAdd_6_CreationAnd_4_ParticipationsPointsAndICanTalkAndLetMeAddBadgeToUserWhenAllServiceMethodsCalled() {
        List<Achievement> expectedAchievements = Stream.of(new Points(CREATION, 6), new Points(PARTICIPATION, 4),
                new Badge(LET_ME_ADD), new Badge(I_CAN_TALK))
                .collect(Collectors.toList());
        proxyForumService.addTopic(user, "topic1");
        proxyForumService.addComment(user, "topic1", "this is a comment");
        proxyForumService.likeTopic(user, "topic1", "user topic1");
        proxyForumService.likeComment(user, "topic1", "user topic", "user comment");
        List<Achievement> achievementsInMemory = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievementsInMemory).containsExactlyInAnyOrderElementsOf(expectedAchievements);
    }

    @Test
    public void shouldNotAddAnyAchievementesInCaseServiceThrowsAnException() {
        doThrow(new RuntimeException()).when(mockRemoteService).addTopic(anyString(),anyString());
        assertThat(AchievementStorageFactory.getInstance().getAchievements(user)).isEmpty();
    }

    @Test
    public void shouldReceiveInventorBadgeWhenExceeds_100_CreationPoints(){
        IntStream.rangeClosed(1,21).forEach(value -> proxyForumService.addTopic(user,"topic " + value));
        List<Achievement> achievements = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievements).contains(new Badge(INVENTOR));
    }

    @Test
    public void shouldReceivePartOfCommunityBadgeWhenExceeds_100_ParticipationPoints() {
        IntStream.rangeClosed(1, 40).forEach(value -> proxyForumService.addComment(user, "topic " + value,
                "this is the " + value+ "th comment"));
        List<Achievement> achievements = AchievementStorageFactory.getInstance().getAchievements(user);
        assertThat(achievements).contains(new Badge(PART_OF_THE_COMMUNITY));
    }

    @After
    public void cleanUp() {
        List<Achievement> achievements = AchievementStorageFactory.getInstance().getAchievements(user);
        if(achievements != null){
            achievements.clear();
        }
        List<AchievementObserver> oververs = AchievementStorageFactory.getInstance().getObservers();
        if(oververs != null){
            oververs.clear();
        }
    }
}