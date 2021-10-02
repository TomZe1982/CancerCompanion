package de.tomze.backend;

import de.tomze.backend.config.JpaConfig;
import de.tomze.backend.service.UserService;
import de.tomze.backend.youtube.YoutubeApi;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;

import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.mockito.Mockito.mock;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"de.tomze.backend"})
@Import({JpaConfig.class})
@TestConfiguration
public class SpringTestContextConfiguration {

    public static final String MOCKED_SERVICES_PROFILE = "mockedUserService";

    @Primary
    @Bean(name = "dataSource", destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Primary
    @Bean
    @Profile(MOCKED_SERVICES_PROFILE)
    public UserService userService() {
        return mock(UserService.class);
    }

    @Primary
    @Bean(name = "youtubeAPIMock")
    @AliasFor("youtubeApi")
    public YoutubeApi getGithubAPI() {
        return mock(YoutubeApi.class);
    }

}
