package com.example.forestguard.alert.controller;

import com.example.forestguard.alert.dto.AlertResponse;
import com.example.forestguard.alert.entity.Alert;
import com.example.forestguard.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    // ✅ Active (unacknowledged) alerts
    @GetMapping
    public List<AlertResponse> getActiveAlerts() {
        return alertService.getActiveAlerts()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ✅ History (acknowledged alerts only)
    @GetMapping("/history")
    public List<AlertResponse> getHistoryAlerts() {
        return alertService.getHistoryAlerts()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    // ✅ Single alert details
    @GetMapping("/{id}")
    public AlertResponse getAlertById(@PathVariable Long id) {
        Alert alert = alertService.getById(id);
        return toResponse(alert);
    }


    // ✅ Officer acknowledges alert
    @PostMapping("/{id}/acknowledge")
    public void acknowledge(@PathVariable Long id) {
        alertService.acknowledge(id);
    }

    private AlertResponse toResponse(Alert a) {
        return new AlertResponse(
                a.getId(),
                a.getDevice().getDeviceId(),
                a.getType(),
                a.getTimestamp(),
                a.getDevice().getLatitude(),
                a.getDevice().getLongitude(),
                a.isAcknowledged()
        );
    }
}
