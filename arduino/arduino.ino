#include <WiFi.h>
#include <PubSubClient.h>

const char* ssid = "Aadi Galaxy A53 5G";  
const char* password = "aadiumrani";  
const char* mqttServer = "broker.hivemq.com";  
const int mqttPort = 1883;
const char* topic = "esp32/led";  

WiFiClient espClient;
PubSubClient client(espClient);

unsigned long lastReconnectAttempt = 0;
bool ledState = LOW;

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Received on topic: ");
  Serial.println(topic);

  String message;
  for (int i = 0; i < length; i++) {
    message += (char)payload[i];
  }

  Serial.print("Payload: ");
  Serial.println(message);

  if (message == "ON" && !ledState) {
    digitalWrite(15, HIGH);
    ledState = HIGH;
    Serial.println("LED turned ON");
  } else if (message == "OFF" && ledState) {
    digitalWrite(15, LOW);
    ledState = LOW;
    Serial.println("LED turned OFF");
  }
}

bool reconnectMQTT() {
  // Attempt to connect to MQTT broker
  if (client.connect("ESP32Client")) {
    Serial.println("Reconnected to MQTT!");
    client.subscribe(topic);
    return true;
  }
  return false;
}

void setup() {
  Serial.begin(115200);
  pinMode(15, OUTPUT);
  digitalWrite(15, LOW);  // Initialize LED to OFF

  // Connect to Wi-Fi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi!");

  // Set MQTT server and callback
  client.setServer(mqttServer, mqttPort);
  client.setCallback(callback);
}

void loop() {
  // Check Wi-Fi status
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("WiFi lost. Reconnecting...");
    WiFi.reconnect();
    delay(1000);  // Wait briefly for reconnection attempt
  }

  // Reconnect to MQTT if disconnected
  if (!client.connected()) {
    unsigned long now = millis();
    if (now - lastReconnectAttempt > 1000) {  // Attempt reconnect every 5 seconds
      lastReconnectAttempt = now;
      if (reconnectMQTT()) {
        lastReconnectAttempt = 0;  // Reset timer on successful connection
      }
    }
  } else {
    client.loop();  // Maintain MQTT connection
  }
}