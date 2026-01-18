package com.example.forestguard.device.entity;
import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID known by ESP32 (hardcoded in firmware)
    @Column(nullable = false, unique = true)
    private String deviceId;

    // Used for authentication (sent in header)
    @Column(nullable = false, unique = true)
    private String apiKey;

    // Hardcoded physical location (acceptable for project)
    private double latitude;
    private double longitude;

    private boolean active = true;
}
