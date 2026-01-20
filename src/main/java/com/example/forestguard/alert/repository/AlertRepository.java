package com.example.forestguard.alert.repository;

import com.example.forestguard.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByAcknowledgedFalseOrderByTimestampDesc();
    List<Alert> findByAcknowledgedTrueOrderByTimestampDesc();

}
