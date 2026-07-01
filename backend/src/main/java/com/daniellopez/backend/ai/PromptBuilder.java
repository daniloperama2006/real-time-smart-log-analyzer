package com.daniellopez.backend.ai;

import com.daniellopez.backend.dto.IncidentEvent;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

public String buildPrompt(IncidentEvent incident) {

    return """
            You are a senior Site Reliability Engineer and Backend Architect.

            Analyze the following production incident.

            INCIDENT TYPE:
            %s

            SEVERITY:
            %s

            DESCRIPTION:
            %s

            OCCURRENCES:
            %d

            Your response MUST be valid JSON only.

            Required JSON schema:

            {
              "severity": "LOW | MEDIUM | CRITICAL",
              "root_cause_analysis": "concise explanation",
              "suggested_fix_code": "production-ready code/config fix"
            }
            """
            .formatted(
                    incident.getIncidentType(),
                    incident.getSeverity(),
                    incident.getDescription(),
                    incident.getOccurrences()
            );
}

}
