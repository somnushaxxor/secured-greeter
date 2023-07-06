package ru.kolesnik.securedgreeter.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfigProperties {

    @Getter
    @Setter
    public static class Access {

        private String secretKey;
        private Duration duration;

    }

    @Getter
    @Setter
    public static class Refresh {

        private Duration duration;

    }

    private Access access;
    private Refresh refresh;

}
