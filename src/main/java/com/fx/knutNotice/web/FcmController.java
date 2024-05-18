package com.fx.knutNotice.web;

import com.fx.knutNotice.common.FcmMessage;
import com.fx.knutNotice.dto.FcmDTO;
import com.fx.knutNotice.service.DeviceTokenService;
import com.fx.knutNotice.service.FcmService;
import com.fx.knutNotice.dto.DeviceTokenDTO;
import com.fx.knutNotice.web.form.ResultForm;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "FCM Controller", description = "토큰 저장 및 관리 API")
public class FcmController {

    private final FcmService fcmService;
    private final DeviceTokenService deviceTokenService;

//    @PostMapping("/sendAllMessage")
    public ResultForm pushMessage(@RequestBody FcmDTO requestDTO)
            throws FirebaseMessagingException {

        fcmService.sendToAllDevices(requestDTO);
        return ResultForm.success(FcmMessage.SUCCESS_MESSAGE_SENT_TO_ALL.getDescription());
    }

    @GetMapping("/getAllTokens")
    @Operation(summary = "모든 토큰 조회", description = "요청시 모든 토큰 조회 가능")
    @ApiResponse(responseCode = "200", description = "전체 토큰 조회 성공")
    public ResultForm getAllTokens() {
        return ResultForm.success(FcmMessage.SUCCESS_ALL_TOKENS_RETRIEVED.getDescription(),
                deviceTokenService.getAllTokens());
    }

    @PostMapping("/sendDeviceToken")
    @Operation(summary = "토큰 저장", description = "id는 기기 고유 식별번호, deviceToken은 FCM의 토큰 번호입니다.")
    @ApiResponse(responseCode = "200", description = "Device Token 저장 성공")
    public ResultForm sendDeviceToken(@RequestBody DeviceTokenDTO deviceTokenDTO) {
        deviceTokenService.saveDeviceToken(deviceTokenDTO);
        return ResultForm.success(FcmMessage.SUCCESS_TOKEN_REGISTRATION.getDescription());
    }

}