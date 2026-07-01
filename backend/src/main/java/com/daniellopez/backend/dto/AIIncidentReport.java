package com.daniellopez.backend.dto;

import java.time.Instant;

public class AIIncidentReport {

private Instant timestamp;

private String severity;

private String rootCauseAnalysis;

private String suggestedFixCode;

public AIIncidentReport(
        Instant timestamp,
        String severity,
        String rootCauseAnalysis,
        String suggestedFixCode
) {
    this.timestamp = timestamp;
    this.severity = severity;
    this.rootCauseAnalysis = rootCauseAnalysis;
    this.suggestedFixCode = suggestedFixCode;
}

public Instant getTimestamp() {
    return timestamp;
}

public String getSeverity() {
    return severity;
}

public String getRootCauseAnalysis() {
    return rootCauseAnalysis;
}

public String getSuggestedFixCode() {
    return suggestedFixCode;
}


}
