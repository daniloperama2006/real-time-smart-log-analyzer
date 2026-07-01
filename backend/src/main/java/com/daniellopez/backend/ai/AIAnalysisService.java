
package com.daniellopez.backend.ai;

import com.daniellopez.backend.dto.AIIncidentReport;
import com.daniellopez.backend.dto.IncidentEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Sinks;

import java.time.Instant;

@Service
public class AIAnalysisService {

private final Sinks.Many<IncidentEvent> incidentSink;

private final Sinks.Many<AIIncidentReport> aiIncidentSink;

private final PromptBuilder promptBuilder;

private final ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory;

private final ObjectMapper objectMapper = new ObjectMapper();

private final WebClient webClient;

@Value("${ai.openai.api-key}")
private String apiKey;

@Value("${ai.openai.model}")
private String model;

public AIAnalysisService(
        Sinks.Many<IncidentEvent> incidentSink,
        Sinks.Many<AIIncidentReport> aiIncidentSink,
        PromptBuilder promptBuilder,
        ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory
) {
    this.incidentSink = incidentSink;
    this.aiIncidentSink = aiIncidentSink;
    this.promptBuilder = promptBuilder;
    this.circuitBreakerFactory = circuitBreakerFactory;

    this.webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .build();
}

@PostConstruct
public void startAIAnalysis() {

    incidentSink.asFlux()
            .flatMap(this::analyzeIncident)
            .subscribe(aiIncidentSink::tryEmitNext);
}

private reactor.core.publisher.Mono<AIIncidentReport> analyzeIncident(
        IncidentEvent incident
) {

    String prompt = promptBuilder.buildPrompt(incident);

    return webClient.post()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + apiKey)
            .bodyValue("""
                    {
                      "model": "%s",
                      "messages": [
                        {
                          "role": "user",
                          "content": "%s"
                        }
                      ]
                    }
                    """.formatted(model, prompt.replace("\"", "\\\"")))
            .retrieve()
            .bodyToMono(String.class)
            .map(this::parseResponse)
            .transform(it ->
                    circuitBreakerFactory
                            .create("openai")
                            .run(it)
            );
}

private AIIncidentReport parseResponse(String response) {

    try {

        JsonNode root = objectMapper.readTree(response);

        String content =
                root
                        .get("choices")
                        .get(0)
                        .get("message")
                        .get("content")
                        .asText();

        JsonNode parsed = objectMapper.readTree(content);

        return new AIIncidentReport(
                Instant.now(),
                parsed.get("severity").asText(),
                parsed.get("root_cause_analysis").asText(),
                parsed.get("suggested_fix_code").asText()
        );

    } catch (Exception ex) {

        return new AIIncidentReport(
                Instant.now(),
                "CRITICAL",
                "Failed to parse AI response",
                "N/A"
        );
    }
}


}
