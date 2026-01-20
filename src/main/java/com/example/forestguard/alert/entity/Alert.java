package com.example.forestguard.alert.entity;

import com.example.forestguard.device.entity.Device;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which device raised this alert
    @ManyToOne(optional = false)
    private Device device;

    // chainsaw, elephant, fire, gun_shot
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Instant timestamp;

    // Officer can acknowledge alert
    private boolean acknowledged = false;
}
