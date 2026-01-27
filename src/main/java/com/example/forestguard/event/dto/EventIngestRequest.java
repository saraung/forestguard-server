package com.example.forestguard.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EventIngestRequest {

    private String label;
    private float confidence;

    private boolean alertTriggered;
}
