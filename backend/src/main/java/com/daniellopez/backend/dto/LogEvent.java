package com.daniellopez.backend.dto;

import java.time.Instant;

public class LogEvent {

private Instant timestamp;
private String serviceName;
private Integer statusCode;
private Long responseTime;
private String message;
private String scenario;

public LogEvent() {
}

public LogEvent(
        Instant timestamp,
        String serviceName,
        Integer statusCode,
        Long responseTime,
        String message,
        String scenario
) {
    this.timestamp = timestamp;
    this.serviceName = serviceName;
    this.statusCode = statusCode;
    this.responseTime = responseTime;
    this.message = message;
    this.scenario = scenario;
}

public Instant getTimestamp() {
    return timestamp;
}

public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
}

public String getServiceName() {
    return serviceName;
}

public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
}

public Integer getStatusCode() {
    return statusCode;
}

public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
}

public Long getResponseTime() {
    return responseTime;
}

public void setResponseTime(Long responseTime) {
    this.responseTime = responseTime;
}

public String getMessage() {
    return message;
}

public void setMessage(String message) {
    this.message = message;
}

public String getScenario() {
    return scenario;
}

public void setScenario(String scenario) {
    this.scenario = scenario;
}


}
 
