package com.programmerfriend.websockets.twitterwebsockets.twitter;

import com.programmerfriend.websockets.twitterwebsockets.TwitterStatusReceived;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

@Component
@Slf4j
public class TwitterListener implements StatusListener {

    private ApplicationEventPublisher applicationEventPublisher;

    public TwitterListener(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onException(Exception e) {
    }

    public void onStatus(Status status) {
        log.info("Received new Status: {}", status);
        TwitterStatusReceived twitterStatusReceived = new TwitterStatusReceived(this, status);
        applicationEventPublisher.publishEvent(twitterStatusReceived);
        log.info("Emitted event for Status with id {}", status.getId());
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
    }

    @Override
    public void onTrackLimitationNotice(int i) {
    }

    @Override
    public void onScrubGeo(long l, long l1) {
    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {
    }
}