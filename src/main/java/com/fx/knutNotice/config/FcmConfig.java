package com.fx.knutNotice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FcmConfig {

    //https://velog.io/@haden/spring-fcm-server-%EC%84%A4%EC%A0%95#%ED%8C%8C%EC%9D%B4%EC%96%B4%EB%B2%A0%EC%9D%B4%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EC%A0%95
    @Value("${firebase.secret.key.path}")
    String fcmKeyPath;

    //Setup Firebase Admin SDK
    @PostConstruct
    public void getFcmCredential() {
        try {
            InputStream refreshToken = new ClassPathResource(fcmKeyPath).getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken)).build();

            FirebaseApp.initializeApp(options);
            log.info("FCM Setting Complete!!!");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
