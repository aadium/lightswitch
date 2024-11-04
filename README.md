# Lightswitch

This is an IOT application with which we can turn an LED on and off using MQTT. The Internet of Things (IoT) refers to a network of physical devices, vehicles, appliances, and other physical objects that are embedded with sensors, software, and network connectivity, allowing them to collect and share data. MQTT is a standards-based messaging protocol, or set of rules, used for machine-to-machine communication. Smart sensors, wearables, and other Internet of Things (IoT) devices typically have to transmit and receive data over a resource-constrained network with limited bandwidth. These IoT devices use MQTT for data transmission, as it is easy to implement and can communicate IoT data efficiently.

I have used Spring Boot to build a web server to send instructions to toggle the light state. I used an ESP32 board, and attached an LED to it. By writing code to subscribe to the MQTT server, I could fetch the message published by the web server, and could accordingly toggle the LED.
