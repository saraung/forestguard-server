package com.example.forestguard.event.service;

import com.example.forestguard.device.entity.Device;
import com.example.forestguard.device.service.DeviceService;
import com.example.forestguard.event.dto.EventIngestRequest;
import com.example.forestguard.event.entity.Event;
import com.example.forestguard.event.repository.EventRepository;
import com.example.forestguard.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final DeviceService deviceService;
    private final AlertService alertService;

    public Event ingest(EventIngestRequest request, String apiKey) {

        // 1️⃣ Authenticate device using API key
        Device device = deviceService.getByApiKey(apiKey);
        Instant now = Instant.now();

        // 2️⃣ Store raw event (always)
        Event event = new Event();
        event.setDevice(device);
        event.setLabel(request.getLabel());
        event.setConfidence(request.getConfidence());
        event.setTimestamp(now);
        event.setAlertTriggered(request.isAlertTriggered());

        Event savedEvent = eventRepository.save(event);

        // 3️⃣ Escalate to alert ONLY if ESP32 already confirmed it
        if (request.isAlertTriggered()) {
            alertService.create(
                    device,
                    request.getLabel(),
                    now
            );
        }

        return savedEvent;
    }
}
