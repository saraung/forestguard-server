package com.example.forestguard.event.controller;

import com.example.forestguard.event.dto.EventIngestRequest;
import com.example.forestguard.event.dto.EventResponse;
import com.example.forestguard.event.entity.Event;
import com.example.forestguard.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public EventResponse ingest(
            @RequestHeader("X-DEVICE-KEY") String apiKey,
            @RequestBody EventIngestRequest request
    ) {

        Event event = eventService.ingest(request, apiKey);

        return new EventResponse(
                event.getDevice().getDeviceId(),
                event.getLabel(),
                event.getConfidence(),
                event.getTimestamp(),
                event.isAlertTriggered()
        );
    }
}
