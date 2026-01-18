package com.example.forestguard.device.controller;

import com.example.forestguard.device.dto.DeviceRegisterRequest;
import com.example.forestguard.device.dto.DeviceResponse;
import com.example.forestguard.device.entity.Device;
import com.example.forestguard.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    // Manual registration (admin / initial setup)
    @PostMapping
    public DeviceResponse register(@RequestBody DeviceRegisterRequest request) {

        Device device = deviceService.register(request);

        return new DeviceResponse(
                device.getDeviceId(),
                device.getApiKey(),
                device.getLatitude(),
                device.getLongitude(),
                device.isActive()
        );
    }

    // List all devices (for dashboard)
    @GetMapping
    public List<DeviceResponse> getAll() {

        return deviceService.getAll()
                .stream()
                .map(d -> new DeviceResponse(
                        d.getDeviceId(),
                        d.getApiKey(),
                        d.getLatitude(),
                        d.getLongitude(),
                        d.isActive()
                ))
                .collect(Collectors.toList());
    }
}
