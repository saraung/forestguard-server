package com.example.forestguard.alert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class AlertResponse {

    private Long id;
    private String deviceId;
    private String type;
    private Instant timestamp;
    private double latitude;
    private double longitude;
    private boolean acknowledged;
}
