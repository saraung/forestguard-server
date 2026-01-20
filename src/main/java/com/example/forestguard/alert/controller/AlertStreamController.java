package com.example.forestguard.alert.controller;

import com.example.forestguard.alert.dto.AlertResponse;
import com.example.forestguard.alert.entity.Alert;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin // IMPORTANT for mobile
public class AlertStreamController {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(
            value = "/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        // 🔑 force connection flush
        try {
            emitter.send(SseEmitter.event().comment("connected"));
        } catch (Exception ignored) {}

        return emitter;
    }

    public void push(Alert alert) {

        // 🔑 SEND DTO, NOT ENTITY
        AlertResponse dto = new AlertResponse(
                alert.getId(),
                alert.getDevice().getDeviceId(),
                alert.getType(),
                alert.getTimestamp(),
                alert.getDevice().getLatitude(),
                alert.getDevice().getLongitude(),
                alert.isAcknowledged()
        );

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("alert")
                                .data(dto)
                );
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }
}
