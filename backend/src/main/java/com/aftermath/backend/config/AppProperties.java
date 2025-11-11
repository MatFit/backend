package com.aftermath.backend.config;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Auth auth = new Auth();

    @PostConstruct
    public void init() {
        System.out.println("AppProperties initialized!");
        System.out.println("Access token expiration: " + auth.getAccessTokenExpirationMsec());
    }

    public Auth getAuth(){
        return auth;
    }

    public static class Auth {
        private long accessTokenExpirationMsec;

        public long getAccessTokenExpirationMsec() {
            return accessTokenExpirationMsec;
        }

        public void setAccessTokenExpirationMsec(long accessTokenExpirationMsec) {
            this.accessTokenExpirationMsec = accessTokenExpirationMsec;
            System.out.println("Setting expiration: " + accessTokenExpirationMsec);
        }
    }
}
