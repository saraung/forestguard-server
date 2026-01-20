package com.example.forestguard.alert.service;

import com.example.forestguard.alert.entity.Alert;
import com.example.forestguard.alert.repository.AlertRepository;
import com.example.forestguard.alert.controller.AlertStreamController;
import com.example.forestguard.device.entity.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    // 🔥 ADD THIS
    private final AlertStreamController alertStreamController;

    // Called internally when event.alertTriggered == true
    public void create(Device device, String type, Instant timestamp) {

        Alert alert = new Alert();
        alert.setDevice(device);
        alert.setType(type);
        alert.setTimestamp(timestamp);
        alert.setAcknowledged(false);

        // 1️⃣ Save alert
        Alert saved = alertRepository.save(alert);

        // 2️⃣ Push to SSE clients
        alertStreamController.push(saved);
    }

    public List<Alert> getActiveAlerts() {
        return alertRepository.findByAcknowledgedFalseOrderByTimestampDesc();
    }

    public List<Alert> getHistoryAlerts() {
        return alertRepository.findByAcknowledgedTrueOrderByTimestampDesc();
    }

    public Alert getById(Long alertId) {
        return alertRepository.findById(alertId)
                .orElseThrow(() -> new IllegalStateException("Alert not found"));
    }

    public void acknowledge(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new IllegalStateException("Alert not found"));

        alert.setAcknowledged(true);
        alertRepository.save(alert);
    }
}
