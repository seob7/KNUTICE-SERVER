package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.DeviceTokenRepository;
import com.fx.knutNotice.domain.entity.DeviceToken;
import com.fx.knutNotice.dto.FcmDTO;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final String GENERAL_NEWS = "일반소식";
    private final String EVENT_NEWS = "행사안내";
    private final String ACADEMIC_NEWS = "학사공지사항";
    private final String SCHOLARSHIP_NEWS = "장학안내";

    private final DeviceTokenRepository deviceTokenRepository;
    private final int MAX_DEVICES_PER_MESSAGE = 500;

    public void sendToAllDevices(FcmDTO dto) throws FirebaseMessagingException {

        List<DeviceToken> allTokens = deviceTokenRepository.findAll();

        // 500개씩 나눠서 전송
        for (int i = 0; i < allTokens.size(); i += MAX_DEVICES_PER_MESSAGE) {
            List<DeviceToken> sublist = allTokens.subList(i,
                Math.min(i + MAX_DEVICES_PER_MESSAGE, allTokens.size()));
            List<String> registrationTokens = sublist.stream().map(DeviceToken::getToken)
                .collect(Collectors.toList());

            MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setNotification(
                    Notification.builder()
                        .setTitle(dto.getTitle())
                        .setBody(dto.getContent())
                        .build()
                )
                .setAndroidConfig(
                    AndroidConfig.builder()
                        .setNotification(
                            AndroidNotification.builder()
                                .setTitle(dto.getTitle())
                                .setBody(dto.getContent())
//                                .setClickAction("push_click")
                                .build()
                        )
                        .build()
                )
                .setApnsConfig(
                    ApnsConfig.builder()
                        .setAps(Aps.builder()
//                        .setCategory("push_click")
                            .build())
                        .build()
                )
//            .putData("제목", dto.getTitle())
//            .putData("내용", dto.getContent())
                .build();
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();
                for (int k = 0; k < responses.size(); k++) {
                    if (!responses.get(k).isSuccessful()) {
                        // The order of responses corresponds to the order of the registration tokens.
                        failedTokens.add(registrationTokens.get(k));
                    }
                }

                log.info("List of tokens that caused failures: {}", failedTokens);
            }
        }
    }

    //Front와 메시지 형식 상의 후 refactoring 진행
    public void fcmTrigger(List<String> updatedGeneralNewsTitle,
        List<String> updatedEventNewsTitle, List<String> updatedAcademicNewsTitle,
        List<String> updatedScholarshipNewsTitle) throws FirebaseMessagingException {

        Map<String, List<String>> updatedNewsMap = initializeFcmTrigger(
            updatedGeneralNewsTitle, updatedEventNewsTitle, updatedAcademicNewsTitle,
            updatedScholarshipNewsTitle);

        //sendFcmNotification 호출
        for (Map.Entry<String, List<String>> entry : updatedNewsMap.entrySet()) {
            List<String> titles = entry.getValue();

            // titles 목록이 비어있지 않은 경우에만 sendFcmNotification 호출
            if (!titles.isEmpty()) {
                this.sendFcmNotification(entry.getKey(), titles);
            }
        }

    }

    @NotNull
    private Map<String, List<String>> initializeFcmTrigger(List<String> updatedGeneralNewsTitle,
        List<String> updatedEventNewsTitle, List<String> updatedAcademicNewsTitle,
        List<String> updatedScholarshipNewsTitle) {
        Map<String, List<String>> updatedNewsMap = new ConcurrentHashMap<>();
        updatedNewsMap.put(GENERAL_NEWS, updatedGeneralNewsTitle);
        updatedNewsMap.put(EVENT_NEWS, updatedEventNewsTitle);
        updatedNewsMap.put(ACADEMIC_NEWS, updatedAcademicNewsTitle);
        updatedNewsMap.put(SCHOLARSHIP_NEWS, updatedScholarshipNewsTitle);
        return updatedNewsMap;
    }

    private void sendFcmNotification(String category, List<String> titles) throws FirebaseMessagingException {
        for (String title : titles) {
            FcmDTO fcmDTO = FcmDTO.builder()
                .title(category)
                .content(title)
                .build();
            this.sendToAllDevices(fcmDTO);
        }
    }

}
