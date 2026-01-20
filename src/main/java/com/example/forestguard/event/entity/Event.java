package com.example.forestguard.event.entity;

import com.example.forestguard.device.entity.Device;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which ESP32 sent this
    @ManyToOne(optional = false)
    private Device device;

    // e.g. chainsaw, elephant, gun_shot
    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private float confidence;

    @Column(nullable = false)
    private Instant timestamp;

    // Already decided by ESP32 firmware
    @Column(nullable = false)
    private boolean alertTriggered;
}
