package com.daniellopez.backend.config;

import com.daniellopez.backend.dto.AIIncidentReport;
import com.daniellopez.backend.dto.IncidentEvent;
import com.daniellopez.backend.dto.LogEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class StreamConfig {

@Bean
public Sinks.Many<LogEvent> logSink() {

    return Sinks.many()
            .multicast()
            .onBackpressureBuffer();
}

@Bean
public Sinks.Many<IncidentEvent> incidentSink() {

    return Sinks.many()
            .multicast()
            .onBackpressureBuffer();
}

@Bean
public Sinks.Many<AIIncidentReport> aiIncidentSink() {

return Sinks.many()
        .multicast()
        .onBackpressureBuffer();

}

}
