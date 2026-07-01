package com.daniellopez.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniellopez.backend.dto.LogEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
public class LogStreamController {

private final Sinks.Many<LogEvent> logSink;

public LogStreamController(Sinks.Many<LogEvent> logSink) {
    this.logSink = logSink;
}

@GetMapping(
        value = "/api/v1/logs/stream",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
)
public Flux<ServerSentEvent<LogEvent>> streamLogs() {

    return logSink.asFlux()
            .map(log ->
                    ServerSentEvent.builder(log)
                            .build()
            );
}

}
