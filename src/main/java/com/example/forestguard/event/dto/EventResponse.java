package com.example.forestguard.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class EventResponse {

    private String deviceId;
    private String label;
    private float confidence;
    private Instant timestamp;
    private boolean alertTriggered;
}
