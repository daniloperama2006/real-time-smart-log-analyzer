package com.daniellopez.backend.engine;

import com.daniellopez.backend.dto.IncidentEvent;
import com.daniellopez.backend.dto.LogEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class AnomalyDetectionEngine {

private final Sinks.Many<LogEvent> logSink;

private final Sinks.Many<IncidentEvent> incidentSink;

public AnomalyDetectionEngine(
        Sinks.Many<LogEvent> logSink,
        Sinks.Many<IncidentEvent> incidentSink
) {
    this.logSink = logSink;
    this.incidentSink = incidentSink;
}

@PostConstruct
public void startDetection() {

    Flux<List<LogEvent>> windowedLogs =
            logSink.asFlux()
                    .window(Duration.ofSeconds(10))
                    .flatMap(Flux::collectList);

    windowedLogs.subscribe(this::analyzeWindow);
}

private void analyzeWindow(List<LogEvent> logs) {

    long databaseErrors =
            logs.stream()
                    .filter(log -> log.getStatusCode() == 500)
                    .count();

    long bruteForceAttempts =
            logs.stream()
                    .filter(log -> log.getStatusCode() == 401)
                    .count();

    long memoryLeaks =
            logs.stream()
                    .filter(log -> log.getResponseTime() > 4000)
                    .count();

    if (databaseErrors >= 5) {

        publishIncident(
                "DATABASE_FAILURE",
                "CRITICAL",
                "High volume of database timeout errors detected",
                databaseErrors
        );
    }

    if (bruteForceAttempts >= 10) {

        publishIncident(
                "BRUTE_FORCE_ATTACK",
                "HIGH",
                "Possible brute force attack detected",
                bruteForceAttempts
        );
    }

    if (memoryLeaks >= 5) {

        publishIncident(
                "MEMORY_LEAK",
                "MEDIUM",
                "Abnormally high response times detected",
                memoryLeaks
        );
    }
}

private void publishIncident(
        String type,
        String severity,
        String description,
        long occurrences
) {

    IncidentEvent incident = new IncidentEvent(
            Instant.now(),
            type,
            severity,
            description,
            occurrences
    );

    incidentSink.tryEmitNext(incident);

    System.out.println(
            "[INCIDENT] " +
                    type +
                    " | " +
                    severity +
                    " | count=" +
                    occurrences
    );
}

}
