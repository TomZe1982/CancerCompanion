package de.tomze.backend.config;

import de.tomze.backend.youtube.YoutubeApi;
import de.tomze.backend.youtube.YoutubeClient;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeClientConfig {

    @Bean
    public YoutubeApi getYoutubeApi() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(YoutubeClient.class))
                .logLevel(Logger.Level.FULL)
                .target(YoutubeApi.class, "https://www.googleapis.com/youtube/v3/");

    }
}
