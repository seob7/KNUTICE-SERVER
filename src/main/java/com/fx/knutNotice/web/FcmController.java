package com.fx.knutNotice.web;

import com.fx.knutNotice.dto.FcmDTO;
import com.fx.knutNotice.service.FcmService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody FcmDTO requestDTO) throws IOException {

        fcmService.sendByToken(requestDTO);

        return ResponseEntity.ok().build();
    }
}
