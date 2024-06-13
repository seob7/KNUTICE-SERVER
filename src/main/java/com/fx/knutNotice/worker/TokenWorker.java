package com.fx.knutNotice.worker;

import com.fx.knutNotice.domain.DeviceTokenRepository;
import com.fx.knutNotice.domain.entity.DeviceToken;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class TokenWorker {

    private final DeviceTokenRepository deviceTokenRepository;

    /**
     * `0 0 0` : 자정(00:00:00)
     * '* /1' : 한 달마다 실행
     * '*' 매년 실행
     * 최소 1달 ~ 2달 동안 DeviceToken 의 RegistrationDate 가 변경되지 않을 때 해당 토큰을 제거
     */
    @Scheduled(cron = "0 0 0 1 */1 *")
    public void tokenLifeCycle() {
        LocalDate twoMonthsAgo = LocalDate.now().minusMonths(1);
        List<DeviceToken> oldTokens = deviceTokenRepository.findByRegistrationDateBefore(twoMonthsAgo);
        deleteToken(oldTokens);
    }

    private void deleteToken(List<DeviceToken> oldTokens) {
        if (!oldTokens.isEmpty()) {
            deviceTokenRepository.deleteAll(oldTokens);
            log.info("Deleted {} tokens", oldTokens.size());
        }
    }
}
