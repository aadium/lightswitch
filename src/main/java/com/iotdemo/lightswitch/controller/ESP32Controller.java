package com.iotdemo.lightswitch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class ESP32Controller {

    @PostMapping("/led")
    public ResponseEntity<String> controlLED(@RequestParam boolean status) {
        if (status) {
            // Logic to turn ON LED via HTTP/MQTT
            return ResponseEntity.ok("LED turned ON");
        } else {
            // Logic to turn OFF LED
            return ResponseEntity.ok("LED turned OFF");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        // You could query sensor data from ESP32 here
        return ResponseEntity.ok("ESP32 is online");
    }
}
