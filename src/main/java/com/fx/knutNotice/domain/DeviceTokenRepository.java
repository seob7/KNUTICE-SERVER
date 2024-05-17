package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.DeviceToken;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    List<DeviceToken> findByRegistrationDateBefore(LocalDate date);

}
