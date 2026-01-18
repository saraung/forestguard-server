package com.example.forestguard.device.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRegisterRequest {

    private String deviceId;

    // Hardcoded location entered once
    private double latitude;
    private double longitude;
}
