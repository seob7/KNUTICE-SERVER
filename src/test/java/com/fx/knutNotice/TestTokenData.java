package com.fx.knutNotice;

import com.fx.knutNotice.domain.DeviceTokenRepository;
import com.fx.knutNotice.domain.entity.DeviceToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestTokenData {

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    @Test
    void addTokenData() {
        for (int i = 0; i < 999; i++) {
            deviceTokenRepository.save(DeviceToken.builder()
                .token(String.valueOf(i) + ":test")
                .build());
        }

    }
}