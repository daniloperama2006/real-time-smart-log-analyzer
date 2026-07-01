package com.daniellopez.backend.dto;

import java.time.Instant;

public class IncidentEvent {

private Instant timestamp;

private String incidentType;

private String severity;

private String description;

private Long occurrences;

public IncidentEvent(
        Instant timestamp,
        String incidentType,
        String severity,
        String description,
        Long occurrences
) {
    this.timestamp = timestamp;
    this.incidentType = incidentType;
    this.severity = severity;
    this.description = description;
    this.occurrences = occurrences;
}

public Instant getTimestamp() {
    return timestamp;
}

public String getIncidentType() {
    return incidentType;
}

public String getSeverity() {
    return severity;
}

public String getDescription() {
    return description;
}

public Long getOccurrences() {
    return occurrences;
}


}
