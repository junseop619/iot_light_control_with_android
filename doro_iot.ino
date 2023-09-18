#include <SPI.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <Servo.h>

const char* ssid = "YOUR SSID";
const char* password = "YOUR PASSWORD";

WiFiServer server(80);

//servo
Servo servo;
int angle = 90; //servo mortor 초기 각도값

void setup() {
  
  //서보모터 연결 핀 설정 및 각도 초기화
  servo.attach(D7); 
  servo.write(angle);
  delay(1000);
 
  //WiFi 연결
  Serial.print("Connecting to ");
  Serial.print(ssid);
 
  WiFi.begin(ssid, password);
  while(WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP Address: ");
  Serial.println(WiFi.localIP());

  //Server 시작
  server.begin();
  Serial.println("Server started");
  Serial.print("Use this URL to connect: ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.print("/");
}

void loop() {
  //client 접속 확인
  WiFiClient client = server.available();
  if(!client){
    return;
  }

  //client가 보내는 데이터를 기다린다.
  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }
 
  //요청을 읽는다.
  String request = client.readStringUntil('\r');
  Serial.println(request);
  client.flush();

   //mid
  if(request.indexOf("/Switch") > 0){
    Serial.println("Turning motor on");
    client.print("Turning motor on");
    angle = 90;
    servo.write(angle);
    }
  

  //led on using mortor
  if(request.indexOf("/On") > 0 ){
    client.print("Turning motor on");
 
    for(int i=0; i<15; i++){
      angle = angle + 1;
      if(angle >= 180){
        angle = 180;
      }
      servo.write(angle);
      delay(10);
    }
    delay(230);
  }

  //led off using mortor
  if(request.indexOf("/Off") > 0){ 
    switchState = "off";
    client.print("Turning motor off");

    for(int i=0; i<15; i++){
      angle = angle -1;
      if(angle<=0){
        angle = 0;
      }
      servo.write(angle);
      delay(10);
    }
    delay(230);
  }

  while(client.available()) {
    client.read();
  }

  client.print("HTTP/1.1 200 OK");
  client.print("Content-Type: text/html\r\n\r\n");
  Serial.println("클라이언트 연결 해제");
  
  angle = 90;
  servo.write(angle);

  delay(50);
}
