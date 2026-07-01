package com.daniellopez.backend.controller;

import com.daniellopez.backend.dto.AIIncidentReport;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
public class AIIncidentController {

private final Sinks.Many<AIIncidentReport> aiIncidentSink;

public AIIncidentController(
        Sinks.Many<AIIncidentReport> aiIncidentSink
) {
    this.aiIncidentSink = aiIncidentSink;
}

@GetMapping(
        value = "/api/v1/incidents/ai-stream",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
)
public Flux<AIIncidentReport> streamAIReports() {

    return aiIncidentSink.asFlux();
}


}
