package com.daniellopez.backend.engine;

import com.daniellopez.backend.dto.LogEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Component
public class LogGeneratorEngine {

private final Sinks.Many<LogEvent> logSink;

private final Random random = new Random();

private final Long intervalMs;

public LogGeneratorEngine(
        Sinks.Many<LogEvent> logSink,
        @Value("${stream.log-interval-ms}") Long intervalMs
) {
    this.logSink = logSink;
    this.intervalMs = intervalMs;
}

@PostConstruct
public void startGenerator() {

    System.out.println("[ENGINE] Starting log generator...");

    Flux.interval(Duration.ofMillis(intervalMs))
            .map(this::generateLog)
            .subscribe(log -> {

                logSink.tryEmitNext(log);

                System.out.println(
                        "[STREAM] " +
                                log.getScenario() +
                                " | " +
                                log.getStatusCode() +
                                " | " +
                                log.getMessage()
                );
            });
}

private LogEvent generateLog(Long sequence) {

    int cycle = (int) (sequence % 40);

    if (cycle < 10) {
        return normalTraffic();
    }

    if (cycle < 20) {
        return bruteForceAttack();
    }

    if (cycle < 30) {
        return databaseFailure();
    }

    return memoryLeak();
}

private LogEvent normalTraffic() {

    return new LogEvent(
            Instant.now(),
            "payment-service",
            200,
            120L + random.nextLong(50),
            "Request completed successfully",
            "NORMAL_TRAFFIC"
    );
}

private LogEvent bruteForceAttack() {

    return new LogEvent(
            Instant.now(),
            "auth-service",
            401,
            80L,
            "Unauthorized access attempt",
            "BRUTE_FORCE_ATTACK"
    );
}

private LogEvent databaseFailure() {

    return new LogEvent(
            Instant.now(),
            "database-service",
            500,
            3000L,
            "Database timeout connection",
            "DATABASE_FAILURE"
    );
}

private LogEvent memoryLeak() {

    return new LogEvent(
            Instant.now(),
            "analytics-service",
            200,
            5000L,
            "High memory consumption detected",
            "MEMORY_LEAK"
    );
}

}
