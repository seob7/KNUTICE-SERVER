package com.fx.knutNotice.web;

import com.fx.knutNotice.common.FcmMessage;
import com.fx.knutNotice.dto.FcmDTO;
import com.fx.knutNotice.service.DeviceTokenService;
import com.fx.knutNotice.service.FcmService;
import com.fx.knutNotice.dto.DeviceTokenDTO;
import com.fx.knutNotice.web.form.ResultForm;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;
    private final DeviceTokenService deviceTokenService;

    @PostMapping("/sendAllMessage")
    public ResultForm pushMessage(@RequestBody FcmDTO requestDTO)
        throws FirebaseMessagingException {

        fcmService.sendToAllDevices(requestDTO);
        return ResultForm.success(FcmMessage.SUCCESS_MESSAGE_SENT_TO_ALL.getDescription());
    }

    @GetMapping("/getAllTokens")
    public ResultForm getAllTokens() {
        return ResultForm.success(FcmMessage.SUCCESS_ALL_TOKENS_RETRIEVED.getDescription(),
            deviceTokenService.getAllTokens());
    }

    @PostMapping("/sendDeviceToken")
    public ResultForm sendDeviceToken(@RequestBody DeviceTokenDTO deviceTokenDTO) {
        deviceTokenService.saveDeviceToken(deviceTokenDTO);
        return ResultForm.success(FcmMessage.SUCCESS_TOKEN_REGISTRATION.getDescription());
    }

}
