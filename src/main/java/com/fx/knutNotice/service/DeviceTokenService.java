package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.DeviceTokenRepository;
import com.fx.knutNotice.domain.entity.DeviceToken;
import com.fx.knutNotice.dto.DeviceTokenDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    public List<DeviceToken> getAllTokens() {
        return deviceTokenRepository.findAll();
    }

    public void saveDeviceToken(DeviceTokenDTO deviceTokenDTO) {
        deviceTokenRepository.save(DeviceToken.builder()
            .token(deviceTokenDTO.getDeviceToken())
            .registrationDate(LocalDateTime.now()) //토큰 관리 권장사항 -> 타임스탬프와 함께 앱 서버에 저장.
            .build());
    }
}
