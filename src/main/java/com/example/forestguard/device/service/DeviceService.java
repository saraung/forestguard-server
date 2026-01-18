package com.example.forestguard.device.service;

import com.example.forestguard.device.dto.DeviceRegisterRequest;
import com.example.forestguard.device.entity.Device;
import com.example.forestguard.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public Device register(DeviceRegisterRequest request) {

        // Prevent duplicate registration
        deviceRepository.findByDeviceId(request.getDeviceId())
                .ifPresent(d -> {
                    throw new IllegalStateException("Device already registered");
                });

        Device device = new Device();
        device.setDeviceId(request.getDeviceId());
        device.setLatitude(request.getLatitude());
        device.setLongitude(request.getLongitude());
        device.setApiKey(generateApiKey());
        device.setActive(true);

        return deviceRepository.save(device);
    }

    public Device getByApiKey(String apiKey) {
        return deviceRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new IllegalStateException("Invalid device API key"));
    }

    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    private String generateApiKey() {
        return "fg_" + UUID.randomUUID().toString().replace("-", "");
    }
}
