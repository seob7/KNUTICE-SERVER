package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

}
