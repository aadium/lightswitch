package com.iotdemo.lightswitch;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@RestController
@RequestMapping("/led")
public class LightswitchApplication {

    private final String broker = "tcp://broker.hivemq.com:1883";
    private final String clientId = "SpringBootClient";
    private MqttClient client;

    public static void main(String[] args) {
        SpringApplication.run(LightswitchApplication.class, args);
    }

    @PostConstruct
    public void init() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        client = new MqttClient(broker, clientId);
        client.connect(options);
    }

    @PostMapping("/on")
    public String turnOnLed() {
        System.out.println("Publishing ON message...");
        publishMessage("ON");
        return "LED turned ON";
    }
    
    @PostMapping("/off")
    public String turnOffLed() {
        System.out.println("Publishing OFF message...");
        publishMessage("OFF");
        return "LED turned OFF";
    }    

    private void publishMessage(String messageContent) {
        try {
            MqttMessage message = new MqttMessage(messageContent.getBytes());
            message.setQos(1);
            message.setRetained(true);  // Retain the latest message
            client.publish("esp32/led", message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
}