package com.programmerfriend.websockets.twitterwebsockets;

import com.programmerfriend.websockets.twitterwebsockets.twitter.TwitterListener;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Configuration
public class TwitterStreamConfig {

    @Autowired
    TwitterListener twitterListener;

    @PostConstruct
    public void setupTwitterStream() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(new String[]{"Elon Musk", "Space X"});
        tweetFilterQuery.language(new String[]{"en"});

        twitterStream.addListener(twitterListener);
        twitterStream.filter(tweetFilterQuery);
    }
}
