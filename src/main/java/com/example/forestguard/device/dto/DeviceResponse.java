package com.example.forestguard.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceResponse {

    private String deviceId;
    private String apiKey;
    private double latitude;
    private double longitude;
    private boolean active;
}
