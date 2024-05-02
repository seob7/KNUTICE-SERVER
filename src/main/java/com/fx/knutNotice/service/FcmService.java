package com.fx.knutNotice.service;

import com.fx.knutNotice.dto.FcmDTO;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FcmService {

    public void sendByToken(FcmDTO dto) {
        String token = dto.getTargetToken();

        Message message = Message.builder()
            .setToken(token)
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
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM send-"+response);
        } catch (FirebaseMessagingException e) {
            log.info("FCM except-"+ e.getMessage());
        }

    }

}
