package com.daniellopez.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.daniellopez.backend.dto.LogEvent;

import reactor.core.publisher.Sinks;

@Configuration
public class StreamConfig {

@Bean
public Sinks.Many<LogEvent> logSink() {

    return Sinks.many()
            .multicast()
            .onBackpressureBuffer();
}

}
